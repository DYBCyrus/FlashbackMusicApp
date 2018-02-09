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
    private Button songName;
    private ImageButton pausePlay;
    private ImageButton nextButton;
    private ImageButton previousButton;

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

        songName = (Button) findViewById(R.id.songName);
        pausePlay = (ImageButton) findViewById(R.id.pauseplay);
        nextButton = (ImageButton) findViewById(R.id.next);
        previousButton = (ImageButton) findViewById(R.id.previous);

        pausePlay.setOnClickListener(new MyClickListener(this));

    }

    private class MyClickListener implements View.OnClickListener {

        private int mBackgroundIndex = 0;
        private final TypedArray mBackgrounds;

        private MyClickListener(Context context) {
            mBackgrounds = context.getResources().obtainTypedArray(R.array.backgrounds);
        }

        // TODO: Update this function for pause and play functionality
        @Override
        public void onClick(View v) {
            // myBackgroundIndex == 0 means pause for pausePlay button
            // myBackgroundIndex == 1 means play for pausePlay button
            mBackgroundIndex++;
            if (mBackgroundIndex >= mBackgrounds.length()) {
                mBackgroundIndex = 0;
            }
            v.setBackgroundResource(mBackgrounds.getResourceId(mBackgroundIndex, 0));

            songName.setText(tracks.toString());

        }

        @Override
        protected void finalize() throws Throwable {
            mBackgrounds.recycle();
            super.finalize();
        }
    }

}
