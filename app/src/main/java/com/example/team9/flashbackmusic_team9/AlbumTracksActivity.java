package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlbumTracksActivity extends AppCompatActivity {

    private ArrayList<Track> tracks;

    private ImageButton pausePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_tracks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        tracks = (ArrayList<Track>) bundle.getSerializable("all_tracks");

        ListAdapter trackOfAlbumAdapter = new TrackListAdapter(this, android.R.layout.simple_list_item_1, tracks);
        ListView trackOfAlbum = (ListView) findViewById(R.id.album_track_list);
        trackOfAlbum.setAdapter(trackOfAlbumAdapter);

        Button backAlbum = (Button) findViewById(R.id.back);
        backAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        pausePlay = (ImageButton) findViewById(R.id.pauseplay);
        pausePlay.setOnClickListener(new MyClickListener(this));

    }

    private static class MyClickListener implements View.OnClickListener {

        private int mBackgroundIndex = 0;
        private final TypedArray mBackgrounds;

        public MyClickListener(Context context) {
            mBackgrounds = context.getResources().obtainTypedArray(R.array.backgrounds);
        }

        @Override
        public void onClick(View v) {
            // myBackgroundIndex == 0 means pause for pausePlay button
            // myBackgroundIndex == 1 means play for pausePlay button
            mBackgroundIndex++;
            if (mBackgroundIndex >= mBackgrounds.length()) {
                mBackgroundIndex = 0;
            }
            v.setBackgroundResource(mBackgrounds.getResourceId(mBackgroundIndex, 0));
        }

        @Override
        protected void finalize() throws Throwable {
            mBackgrounds.recycle();
            super.finalize();
        }
    }

}
