package com.example.team9.flashbackmusic_team9;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class AlbumTracksActivity extends AppCompatActivity {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_tracks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button playAll = findViewById(R.id.playAll);
        Button backToAlbums = findViewById(R.id.back);

        int index = getIntent().getExtras().getInt("index");
        album = DataBase.getAlbum(index);

        TextView albumName = (TextView)findViewById(R.id.album_name);
        albumName.setText(album.getName());

        PlayerToolBar playerToolBar = new PlayerToolBar((Button)findViewById(R.id.trackName_button),
                (ImageButton)findViewById(R.id.previous_button), (ImageButton)findViewById(R.id.play_button),
                (ImageButton)findViewById(R.id.next_button), this);

        ListAdapter trackOfAlbumAdapter = new TrackListAdapter(this, R.layout.list_item, album.getTracks());
        ListView trackOfAlbum = findViewById(R.id.album_track_list);
        trackOfAlbum.setAdapter(trackOfAlbumAdapter);
        trackOfAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapterView.getAdapter().getItem(i);
                Player.clearPlayList();
                if (track.getStatus() != Track.FavoriteStatus.DISLIKE) {
                    Player.start(track);
                    launchActivity();
                }
            }
        });


        // Clicking play all songs button
        playAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayList playList = new PlayList(album.getTracks(), false);
                if (playList.hasNext()) {
                    Player.playPlayList(playList);
                    launchActivity();
                }
            }
        });

        backToAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button modeChange = (Button)findViewById(R.id.flash_mode);
        modeChange.setVisibility(View.INVISIBLE);
    }

    public void launchActivity() {
        Intent intent = new Intent(this, PlayingActivity.class);
        startActivity(intent);
    }

    @Override
    public void finish() {
        Updateables.popItem();
        for (Track each:album.getTracks()) {
            each.popListeningFavoriteStatusButton(this);
        }
        super.finish();
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
