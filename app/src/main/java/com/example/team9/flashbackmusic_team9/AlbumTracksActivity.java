package com.example.team9.flashbackmusic_team9;

import android.content.Intent;
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
import android.widget.TextView;

public class AlbumTracksActivity extends AppCompatActivity {

    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_tracks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button playAll = findViewById(R.id.playAll);
        Button backToAlbums = findViewById(R.id.back);

        int index = getIntent().getExtras().getInt("index");
        album = DataBase.getAlbum(index);

        TextView albumName = (TextView)findViewById(R.id.album_name);
        albumName.setText(album.getName());

        PlayerToolBar playerToolBar = new PlayerToolBar((Button)findViewById(R.id.trackName_button),
                (ImageButton)findViewById(R.id.previous_button), (ImageButton)findViewById(R.id.play_button),
                (ImageButton)findViewById(R.id.next_button), this);

        ListAdapter trackOfAlbumAdapter = new TrackListAdapter(this, android.R.layout.simple_list_item_1, album.getTracks());
        ListView trackOfAlbum = findViewById(R.id.album_track_list);
        trackOfAlbum.setAdapter(trackOfAlbumAdapter);
        trackOfAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapterView.getAdapter().getItem(i);
                Player.clearPlayList();
                if (track.getStatus() != Track.FavoriteStatus.DISLIKE) {
                    Player.start(track);
                    launchActivity();
                }
            }
        });

        // Clicking play all songs button
        playAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayList playList = new PlayList(album.getTracks(), false);
                if (playList.hasNext()) {
                    Player.playPlayList(playList);
                    launchActivity();
                }
            }
        });

        backToAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void launchActivity() {
        Intent intent = new Intent(this, PlayingActivity.class);
        startActivity(intent);
    }

    @Override
    public void finish() {
        Updateables.popItem();
        for (Track each:album.getTracks()) {
            each.popListeningFavoriteStatusButton();
        }
        super.finish();
    }

}
