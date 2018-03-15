package com.example.team9.flashbackmusic_team9;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class ViewPlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<ITrack> trackList = new ArrayList<>();
        for ( ITrack each:Player.getPlayList().getViewTracks()) {
            if(each.isPlayable() || !each.hasDownloaded()) {
                trackList.add(each);
            }
        }

        ListAdapter tracksAdapter = new SimpleTrackListAdapter(this,
                android.R.layout.simple_list_item_1, trackList);
        ListView trackView = (ListView) findViewById(R.id.simple_track_list);
        trackView.setAdapter(tracksAdapter);

        Button back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        editor.putString("lastActivity", "flash");
        editor.apply();
    }

}
