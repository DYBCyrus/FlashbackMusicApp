package com.example.team9.flashbackmusic_team9;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AllAlbumsActivity extends AppCompatActivity {

    private ArrayList<Album> album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_albums);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button showTracks = (Button) findViewById(R.id.all_tracks);
        showTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PlayerToolBar.popToolbar();
            }
        });

        album = DataBase.getAllAlbums();

        PlayerToolBar playerToolBar = new PlayerToolBar((Button)findViewById(R.id.trackName_button),
                (ImageButton)findViewById(R.id.previous_button), (ImageButton)findViewById(R.id.play_button),
                (ImageButton)findViewById(R.id.next_button), this);

        ListAdapter albumsAdapter = new AlbumListAdapter(this, android.R.layout.simple_list_item_1, album);
        ListView albumView = (ListView) findViewById(R.id.album_list);
        albumView.setAdapter(albumsAdapter);

        albumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchActivity(i);
            }
        });

    }

    public void launchActivity(int index) {
        Intent intent = new Intent(this, AlbumTracksActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

}
