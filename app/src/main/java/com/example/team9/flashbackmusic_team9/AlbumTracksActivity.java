package com.example.team9.flashbackmusic_team9;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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


public class AlbumTracksActivity extends AppCompatActivity {

    private  Button displaySong;
    private MediaPlayer mediaPlayer;
//    private Player player;
    private String currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_tracks);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        final ImageButton pausePlay;
        ImageButton nextButton;
        final ImageButton previousButton;
        Album album;
        Track track;

        int index = getIntent().getExtras().getInt("index");
        album = DataBase.getAlbum(index);

        displaySong = findViewById(R.id.songName);
        pausePlay = findViewById(R.id.pauseplay);
        nextButton = findViewById(R.id.next);
        previousButton = findViewById(R.id.previous);

        track = Player.getCurrentTrack();
        currentSong = track.getName();

        ListAdapter trackOfAlbumAdapter = new TrackListAdapter(this, android.R.layout.simple_list_item_1, album.getTracks());
        ListView trackOfAlbum = findViewById(R.id.album_track_list);
        trackOfAlbum.setAdapter(trackOfAlbumAdapter);
        trackOfAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapterView.getAdapter().getItem(i);
                currentSong = track.getName();
                displaySong.setText(currentSong);
                launchActivity(track);

                Drawable d = getResources().getDrawable(R.drawable.pause);
                pausePlay.setBackground(d);
            }
        });

        Button backAlbum = findViewById(R.id.back);
        backAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        displaySong.setText(currentSong);

        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable d = pausePlay.getBackground();
                Drawable backGround = getResources().getDrawable(R.drawable.play);
                mediaPlayer = Player.getPlayer();

                if( d.getConstantState().equals(backGround.getConstantState()) ){
                    pausePlay.setBackground(getResources().getDrawable(R.drawable.pause));
                    mediaPlayer.start();
                }
                else{
                    pausePlay.setBackground(getResources().getDrawable(R.drawable.play));
                    mediaPlayer.pause();
                }

            }
        });

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void launchActivity(Track track) {
        Player.start(track);
    }

}
