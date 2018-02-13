package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlayingActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        TextView title = (TextView)findViewById(R.id.title);
        TextView artist = (TextView)findViewById(R.id.artist);
        TextView album = (TextView)findViewById(R.id.album);
        TextView location = (TextView)findViewById(R.id.location);
        TextView time = (TextView)findViewById(R.id.time);

        title.setText(Player.getCurrentTrack().getName());
        artist.setText(Player.getCurrentTrack().getArtist());
        album.setText(Player.getCurrentTrack().getAlbum().getName());

        final ImageButton fav = (ImageButton) findViewById(R.id.likeButton);

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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}

