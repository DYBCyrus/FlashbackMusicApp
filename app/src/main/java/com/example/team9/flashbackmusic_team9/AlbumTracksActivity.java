package com.example.team9.flashbackmusic_team9;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.res.ResourcesCompat;
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

import java.util.ArrayList;


public class AlbumTracksActivity extends AppCompatActivity {

    private  Button displaySong;
    private MediaPlayer mediaPlayer;
    private String currentSong;
    private PlayList playList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_tracks);

        mediaPlayer = Player.getPlayer();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button playAll = findViewById(R.id.playAll);
        Button backAlbum = findViewById(R.id.back);
        final ImageButton pausePlay;
        ImageButton nextButton;
        final ImageButton previousButton;
        final Album album;
        final Track track;


        int index = getIntent().getExtras().getInt("index");
        album = DataBase.getAlbum(index);


        displaySong = findViewById(R.id.songName);
        pausePlay = findViewById(R.id.pauseplay);
        nextButton = findViewById(R.id.next);
        previousButton = findViewById(R.id.previous);


        track = Player.getCurrentTrack();

        if( track == null ){
            currentSong = "";
        }
        else{
            currentSong = track.getName();
        }

        if( currentSong.equals("") ){
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.play, null);
            pausePlay.setBackground(d);
        }

        ListAdapter trackOfAlbumAdapter = new TrackListAdapter(this, android.R.layout.simple_list_item_1, album.getTracks());
        final ListView trackOfAlbum = findViewById(R.id.album_track_list);
        trackOfAlbum.setAdapter(trackOfAlbumAdapter);
        trackOfAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapterView.getAdapter().getItem(i);
                ArrayList<Track> temp = new ArrayList<Track>();
                temp.add(track);
                setPlaylist(temp, 0);
                Player.setPlayList(getPlayList());
                currentSong = track.getName();
                displaySong.setText(currentSong);
                launchActivity(track);

                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.pause, null);
                pausePlay.setBackground(d);
            }
        });

        // Clicking play all songs button
        playAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlaylist(album.getTracks(), 0);
                Player.setPlayList(getPlayList());
                if (Player.getPlayList().hasNext()) {
                    Track track = Player.getPlayList().next();
                    Player.start(track);
                    currentSong = track.getName();
                    displaySong.setText(currentSong);
                }
                if (Player.getPlayer().isPlaying()) {
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.play, null));
                } else {
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pause, null));
                }
            }
        });

        Player.getPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (Player.getPlayList().hasNext()) {
                    Track track = Player.getPlayList().next();
                    Player.start(track);
                    currentSong = track.getName();
                    displaySong.setText(currentSong);
                }
            }
        });


        displaySong.setText(currentSong);

        // Clicking the pause/play song button
        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable d = pausePlay.getBackground();
                Drawable backGround = ResourcesCompat.getDrawable(getResources(), R.drawable.play, null);

                if( d.getConstantState().equals(backGround.getConstantState()) &&
                        !currentSong.equals("") ){
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pause, null));
                    mediaPlayer.start();
                }
                else{
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.play, null));
                    mediaPlayer.pause();
                }

            }
        });


        // Clicking the next song button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trackNumber++;

//                if( trackNumber == trackOfAlbum.getAdapter().getCount() ){
//                    trackNumber = trackOfAlbum.getAdapter().getCount() - 1;
//                }
                if (Player.getPlayList().hasNext()) {
//                    Track nextTrack = (Track) trackOfAlbum.getItemAtPosition(trackNumber);
                    Track nextTrack = Player.getPlayList().next();
                    currentSong = nextTrack.getName();
                    displaySong.setText(currentSong);
                    launchActivity(nextTrack);
                } else {
                    Player.getPlayer().reset();
                }
                if (Player.getPlayer().isPlaying()) {
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.play, null));
                } else {
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pause, null));
                }
            }
        });


        // Clicking the previous song button
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trackNumber--;

//                if( trackNumber < 0 ){
//                    trackNumber = 0;
//                }
                Track nextTrack;
                if (Player.getPlayList().hasPrevious()) {

//                    Track nextTrack = (Track) trackOfAlbum.getItemAtPosition(trackNumber);
                    nextTrack = Player.getPlayList().previous();
                } else {
                    nextTrack = Player.getCurrentTrack();
                }
                currentSong = nextTrack.getName();
                displaySong.setText(currentSong);
                launchActivity(nextTrack);
                if (Player.getPlayer().isPlaying()) {
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.play, null));
                } else {
                    pausePlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pause, null));
                }
            }
        });

        // Clicking the back button
        backAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void launchActivity(Track track) {
        Player.start(track);
    }

    public void setPlaylist(ArrayList<Track> tracks, int index) {
        playList = new PlayList(tracks, index);
    }

    public PlayList getPlayList() {return playList;}

}
