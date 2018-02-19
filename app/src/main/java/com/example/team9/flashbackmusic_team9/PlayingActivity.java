package com.example.team9.flashbackmusic_team9;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.os.ResultReceiver;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.ArrayList;

public class PlayingActivity extends AppCompatActivity implements Updateable{

    private TextView title;
    private TextView artist;
    private TextView album;
    private TextView location;
    private TextView time;
    private String mAddressOutput;
    private ImageButton fav;
    private SeekBar seekbar;
    public Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();

            }
        });

        findViewById(R.id.viewPlaylist).setVisibility(View.INVISIBLE);
        fav = findViewById(R.id.likeButton);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        location = findViewById(R.id.location);
        time = findViewById(R.id.time);

        final ImageButton fav = findViewById(R.id.likeButton);
        seekbar = findViewById(R.id.seekBar);

        TextView title = findViewById(R.id.title);
        TextView artist = findViewById(R.id.artist);
        TextView album = findViewById(R.id.album);

        title.setText(Player.getCurrentTrack().getName());
        artist.setText(Player.getCurrentTrack().getArtist());
        album.setText(Player.getCurrentTrack().getAlbum().getName());

        seekbar.setMax(Player.getPlayer().getDuration());
        seekbar.setProgress(Player.getPlayer().getCurrentPosition());

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

        final Handler h = new Handler();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(Player.getPlayer().isPlaying()){
                    System.out.println("!!!!!");
                    seekbar.setMax(Player.getPlayer().getDuration());
                    seekbar.setProgress(Player.getPlayer().getCurrentPosition());
                    h.postDelayed(this, 500);
                }
            }
        };

        h.removeCallbacks(r);
        h.postDelayed(r, 500);

        // Checking track status before launching activity for like_dislike button image
        if (Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.DISLIKE) {
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.x, null));
        } else if (Player.getCurrentTrack().getStatus() == Track.FavoriteStatus.LIKE) {
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark, null));
        } else {
            fav.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.plus, null));
        }
        update();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track tr = Player.getCurrentTrack();
                if (tr != null) {
                    if (tr.getStatus() == Track.FavoriteStatus.NEUTRAL) {
                        tr.setStatus(Track.FavoriteStatus.LIKE);
                    } else if (tr.getStatus() == Track.FavoriteStatus.LIKE) {
                        tr.setStatus(Track.FavoriteStatus.DISLIKE);
                        Player.playNext();
                    } else {
                        tr.setStatus(Track.FavoriteStatus.NEUTRAL);
                    }
                }
            }
        });

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
        seekbar.setProgress(Player.getPlayer().getCurrentPosition());
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

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ArrayList<MockTrack> mockTracks = new ArrayList<>();
        for (Track each : DataBase.getAllTracks()) {
            mockTracks.add(each.getMockTrack());
        }
        try {
            editor.putString("tracks", ObjectSerializer.serialize(mockTracks));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString("lastActivity", "normal");
        editor.apply();
    }

}

