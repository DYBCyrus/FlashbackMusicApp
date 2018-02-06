package com.example.team9.flashbackmusic_team9;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AllAlbumsActivity extends AppCompatActivity {

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
            }
        });

        String[] albums = {"album1", "album2", "album3"};
        ListAdapter albumsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albums);
        ListView albumView = (ListView) findViewById(R.id.album_list);
        albumView.setAdapter(albumsAdapter);

        albumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchActivity();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void launchActivity() {
        Intent intent = new Intent(this, AlbumTracksActivity.class);
        intent.putExtra("all_tracks", new String[]{"track1", "track2"});
        startActivity(intent);
    }

}
