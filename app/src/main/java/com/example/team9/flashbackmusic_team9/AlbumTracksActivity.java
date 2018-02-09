package com.example.team9.flashbackmusic_team9;

import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


public class AlbumTracksActivity extends AppCompatActivity {

    private  Button displaySong;;
    private MediaPlayer mediaPlayer;
    private Player player;
    private String currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_tracks);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ImageButton pausePlay;
        ImageButton nextButton;
        ImageButton previousButton;
        Album album;
        Track track;

        int index = getIntent().getExtras().getInt("index");
        album = DataBase.getAlbum(index);

        displaySong = (Button) findViewById(R.id.songName);
        pausePlay = (ImageButton) findViewById(R.id.pauseplay);
        nextButton = (ImageButton) findViewById(R.id.next);
        previousButton = (ImageButton) findViewById(R.id.previous);

        track = player.getCurrentTrack();
        currentSong = track.getName();

        ListAdapter trackOfAlbumAdapter = new TrackListAdapter(this, android.R.layout.simple_list_item_1, album.getTracks());
        ListView trackOfAlbum = (ListView) findViewById(R.id.album_track_list);
        trackOfAlbum.setAdapter(trackOfAlbumAdapter);
        trackOfAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapterView.getAdapter().getItem(i);
                launchActivity(track);
                currentSong = track.getName();
                displaySong.setText(currentSong);

            }
        });
        Button backAlbum = (Button) findViewById(R.id.back);
        backAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        displaySong.setText(currentSong);
        pausePlay.setOnClickListener(new MyClickListener(this));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySong.setText("Next Button Clicked!");
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySong.setText("Prev Button Clicked!");
            }
        });
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
            mediaPlayer = player.getPlayer();

            if( mBackgroundIndex == 0 ){
                mBackgroundIndex = 1;
                v.setBackgroundResource(mBackgrounds.getResourceId(mBackgroundIndex, 0));
                mediaPlayer.pause();
            }
            else{
                mBackgroundIndex = 0;
                v.setBackgroundResource(mBackgrounds.getResourceId(mBackgroundIndex, 0));
                mediaPlayer.start();
            }

        }

        @Override
        protected void finalize() throws Throwable {
            mBackgrounds.recycle();
            super.finalize();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void launchActivity(Track track) {
        Player.start(track);
    }

}
