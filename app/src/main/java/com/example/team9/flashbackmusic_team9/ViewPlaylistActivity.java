package com.example.team9.flashbackmusic_team9;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewPlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Track> trackList = new ArrayList<>();
        for (Track each:Player.getPlayList().getPlayingTracks()) {
            if(each.getStatus()!= Track.FavoriteStatus.DISLIKE) {
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

}
