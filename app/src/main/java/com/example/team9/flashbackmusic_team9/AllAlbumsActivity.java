package com.example.team9.flashbackmusic_team9;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AllAlbumsActivity extends AppCompatActivity {

    private ArrayList<Album> album;
    private Button showTracks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_albums);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showTracks = (Button) findViewById(R.id.all_tracks);
        showTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Updateables.popItem();
                showTracks.setEnabled(false);
            }
        });

        album = DataBase.getAllAlbums();

        PlayerToolBar playerToolBar = new PlayerToolBar((Button)findViewById(R.id.trackName_button),
                (ImageButton)findViewById(R.id.previous_button), (ImageButton)findViewById(R.id.play_button),
                (ImageButton)findViewById(R.id.next_button), this);

        ListAdapter albumsAdapter = new AlbumListAdapter(this, R.layout.album_item, album);
        ListView albumView = (ListView) findViewById(R.id.album_list);
        albumView.setAdapter(albumsAdapter);

        albumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchActivity(i);
            }
        });

        Button mode = (Button)findViewById(R.id.flash);
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchModeActivity();
            }
        });

    }

    /**
     * Switch to flashback mode view
     */
    public void launchModeActivity() {
        ArrayList<Track> list = new ArrayList<>();
        for (Track each:DataBase.getAllTracks()) {
            if (each.hasPlayHistory()) {
                list.add(each);
            }
        }
        Collections.sort(list);

        PlayList flashbackList = new PlayList(list, true);
        if (flashbackList.hasNext()) {
            Intent intent = new Intent(this, FlashBackActivity.class);
            Player.playPlayList(flashbackList);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("No track available for flashback");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Got it",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void launchActivity(int index) {
        Intent intent = new Intent(this, AlbumTracksActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ArrayList<Track> shareTracks = DataBase.getShareTracks();
        int i = 0;
        for (Track t : DataBase.getAllTracks()) {
            for (i = 0; i < shareTracks.size(); i++) {
                if (t.getName().equals(shareTracks.get(i).getName()) &&
                        t.getArtist().equals(shareTracks.get(i).getArtist())) {
                    break;
                }
            }
            shareTracks.set(i, t);
        }

        SharedPreferences prefs = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ArrayList<MockTrack> mockTracks = new ArrayList<>();
        for (Track each : shareTracks) {
            mockTracks.add(each.getMockTrack());
        }
        try {
            editor.putString("tracks", ObjectSerializer.serialize(mockTracks));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString("lastActivity", "normal");
        editor.apply();
    }

}
