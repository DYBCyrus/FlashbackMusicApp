package com.example.team9.flashbackmusic_team9;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
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
    private String mAddressOutput;

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

            }
        });

        fav = findViewById(R.id.likeButton);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        location = findViewById(R.id.location);
        time = findViewById(R.id.time);

        update();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track tr = Player.getCurrentTrack();
                if (tr != null) {
                    if (tr.getStatus() == Track.FavoriteStatus.NEUTRAL) {
                        tr.setStatus(Track.FavoriteStatus.LIKE);
//                    fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark,
//                            null));
                    } else if (tr.getStatus() == Track.FavoriteStatus.LIKE) {
                        tr.setStatus(Track.FavoriteStatus.DISLIKE);
//                    fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x,
//                            null));
                        Player.playNext();
                    } else {
                        tr.setStatus(Track.FavoriteStatus.NEUTRAL);
//                    fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus,
//                            null));
                    }
                }
            }
        });

//        Player.getCurrentTrack().addListeningFavoriteStatusButton(fav);

        PlayerToolBar playerToolBar = new PlayerToolBar(new Button(this),
                (ImageButton)findViewById(R.id.prevButton),
                (ImageButton)findViewById(R.id.play_pause_button),
                (ImageButton)findViewById(R.id.nextButton), this);
        Updateables.addUpdateable(this);
    }

    protected void startIntentService() {
        AddressResultReceiver mResultReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra("receiver", mResultReceiver);
        if (Player.getCurrentTrack().getLocation() != null) {
            intent.putExtra("location", Player.getCurrentTrack().getLocation());
            startService(intent);
        }
    }

//    public void checkStatus() {
//        if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.DISLIKE ){
//            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x,
//                    null));
//        }
//        else if( Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.LIKE ){
//            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark,
//                    null));
//        }
//        else{
//            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus,
//                    null));
//        }
//    }

//    public void display() {
//        TextView location = (TextView)findViewById(R.id.location);
//        TextView time = (TextView)findViewById(R.id.time);
//        Track currentTrack = Player.getCurrentTrack();
//        if (currentTrack.getLocation() != null) {
//            location.setText(currentTrack.getLocation().toString());
//        } else {
//            location.setText("No playing history");
//        }
//        if (currentTrack.getDate() != null) {
//            time.setText(currentTrack.getDate().toString());
//        } else {
//            time.setText("No playing history");
//        }
//    }

    public void update() {
        Track track = Player.getCurrentTrack();
        if (track == null) {
            finish();
            return;
        }
        title.setText(track.getName());
        artist.setText(track.getArtist());
        album.setText(track.getAlbum().getName());
        if (track.getLocation() != null) {
            startIntentService();
        } else {
            location.setText("No playing history");
        }
        if (track.getDate() != null) {
            time.setText(track.getDate().toString());
        } else {
            time.setText("No playing history");
        }
        System.out.println(track.getStatus());
        switch (track.getStatus()) {
            case LIKE:
                fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark,
                        null));
                break;
            case NEUTRAL:
                fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus,
                        null));
                break;
            case DISLIKE:
                fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x,
                        null));
        }
    }
    @Override
    public void finish() {
        Updateables.popItem();
        Updateables.popItem();
        super.finish();
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString("result");
            location.setText(mAddressOutput);
        }

    }

}

