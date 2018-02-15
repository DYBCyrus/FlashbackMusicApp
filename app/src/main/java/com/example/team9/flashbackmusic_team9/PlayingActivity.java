package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class PlayingActivity extends AppCompatActivity {


    private SeekBar seekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        final ImageButton fav = findViewById(R.id.likeButton);
        final ImageButton play_pause = findViewById(R.id.play_pause_button);
        seekbar = findViewById(R.id.seekBar);

        TextView title = findViewById(R.id.title);
        TextView artist = findViewById(R.id.artist);
        TextView album = findViewById(R.id.album);
        TextView location = findViewById(R.id.location);
        TextView time = findViewById(R.id.time);

        title.setText(Player.getCurrentTrack().getName());
        artist.setText(Player.getCurrentTrack().getArtist());
        album.setText(Player.getCurrentTrack().getAlbum().getName());
        display();


        seekbar.setMax(Player.getPlayer().getDuration());
        seekbar.setProgress(Player.getPlayer().getCurrentPosition());


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int currentPosition  = 0;
//                int total = Player.getPlayer().getDuration();
//                seekbar.setMax(total);
//                while (Player.getPlayer() != null && currentPosition < total ){
//                    try{
//                        Thread.sleep(1000);
//                        currentPosition = Player.getPlayer().getCurrentPosition();
//                    }
//                    catch (InterruptedException e){
//                        return;
//                    }
//                    seekbar.setProgress(currentPosition);
//                }
//            }
//        });

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                int currentPosition  = 0;
//                int total = Player.getPlayer().getDuration();
//                seekbar.setMax(total);
//                while (Player.getPlayer() != null && currentPosition < total ){
//                    try{
//                        Thread.sleep(1000);
//                        currentPosition = Player.getPlayer().getCurrentPosition();
//                    }
//                    catch (InterruptedException e){
//                        return;
//                    }
//                    seekbar.setProgress(currentPosition);
//                }
//            }
//        };

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            boolean userTouch;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if( Player.getPlayer().isPlaying() && userTouch ){
                    Player.getPlayer().seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                userTouch = true;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userTouch = false;
            }
        });

        // Checking track status before launching activity for like_dislike button image
        if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.DISLIKE ){
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x, null));
        }
        else if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.LIKE ){
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark, null));
        }
        else{
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus, null));
        }


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.DISLIKE ){
                    Player.getCurrentTrack().setStatus(Track.FavoriteStatus.NEUTRAL);
                    fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus, null));
                }
                else if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.LIKE ){
                    Player.getCurrentTrack().setStatus(Track.FavoriteStatus.DISLIKE);
                    fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x, null));
                }
                else{
                    Player.getCurrentTrack().setStatus(Track.FavoriteStatus.LIKE);
                    fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark, null));
                }
            }
        });

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( Player.isPlaying() ){
                    Player.pause();
                    play_pause.setImageResource(R.drawable.play);
                }
                else{
                    Player.resume();
                    play_pause.setImageResource(R.drawable.pause);
                }
            }
        });
    }



//    public void run(){
//        int currentPosition  = 0;
//        int total = Player.getPlayer().getDuration();
//        seekbar.setMax(total);
//        while (Player.getPlayer() != null && currentPosition < total ){
//            try{
//                Thread.sleep(1000);
//                currentPosition = Player.getPlayer().getCurrentPosition();
//            }
//            catch (InterruptedException e){
//                return;
//            }
//            seekbar.setProgress(currentPosition);
//        }
//    }

    public void display() {
        TextView location = (TextView)findViewById(R.id.location);
        TextView time = (TextView)findViewById(R.id.time);
        Track currentTrack = Player.getCurrentTrack();
        if (currentTrack.getLocation() != null) {
            location.setText(currentTrack.getLocation().toString());
        } else {
            location.setText("No playing history");
        }
        if (currentTrack.getDate() != null) {
            time.setText(currentTrack.getDate().toString());
        } else {
            time.setText("No playing history");
        }
    }

}

