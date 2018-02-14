package com.example.team9.flashbackmusic_team9;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayingActivity extends AppCompatActivity implements Updateable{

    private TextView title;
    private TextView artist;
    private TextView album;
    private TextView location;
    private TextView time;
    private ImageButton fav;

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
                Updateables.popItem();
                Updateables.popItem();
            }
        });

        fav = findViewById(R.id.likeButton);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        location = findViewById(R.id.location);
        time = findViewById(R.id.time);

        title.setText(Player.getCurrentTrack().getName());
        artist.setText(Player.getCurrentTrack().getArtist());
        album.setText(Player.getCurrentTrack().getAlbum().getName());
        display();

        // Checking track status before launching activity for like_dislike button image
        checkStatus();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            changeStatus();
            }
        });

        PlayerToolBar playerToolBar = new PlayerToolBar(new Button(this),
                (ImageButton)findViewById(R.id.prevButton),
                (ImageButton)findViewById(R.id.play_pause_button),
                (ImageButton)findViewById(R.id.nextButton), this);
        Updateables.addUpdateable(this);
    }

    public void checkStatus() {
        if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.DISLIKE ){
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x,
                    null));
        }
        else if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.LIKE ){
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark,
                    null));
        }
        else{
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus,
                    null));
        }
    }

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

    public void changeStatus() {
        if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.DISLIKE ){
            Player.getCurrentTrack().setStatus(Track.FavoriteStatus.NEUTRAL);
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus,
                    null));
        }
        else if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.LIKE ){
            Player.getCurrentTrack().setStatus(Track.FavoriteStatus.DISLIKE);
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x,
                    null));
        }
        else{
            Player.getCurrentTrack().setStatus(Track.FavoriteStatus.LIKE);
            fav.setBackground(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.check_mark, null));
        }
    }

    public void update() {
        Track track = Player.getCurrentTrack();
        title.setText(track.getName());
        artist.setText(track.getArtist());
        album.setText(track.getAlbum().getName());
        if (track.getLocation() != null || track.getDate() != null) {
            location.setText(track.getLocation().toString());
            time.setText(track.getDate().toString());
        }
        checkStatus();
    }

}

