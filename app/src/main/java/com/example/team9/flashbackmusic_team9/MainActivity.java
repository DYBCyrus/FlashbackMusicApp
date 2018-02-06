package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> SongNames;

    private MediaPlayer mediaPlayer;
    private static final int MEDIA_RES_ID = R.raw.deaddovedonoteat;

    String path = Environment.getExternalStorageState().toString();

    public void loadMedia(int resourceId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        AssetFileDescriptor assetFileDescriptor = this.getResources().openRawResourceFd(resourceId);
        try {
            mediaPlayer.setDataSource(assetFileDescriptor);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String[] album;
        String[][] song;
        AssetManager manager = getAssets();
        try {
            album = manager.list("");

            song = new String[album.length][];

            for (int i = 0; i < album.length; i++)
            {
                song[i] = manager.list(album[i]);
                boolean s = false;
                for(String each:song[i] )
                {
                    if (each.substring(each.length()-4, each.length()-1).compareTo(".mp3")==0)
                    {
                        s = true;
                    }
                }
                if(!s)
                {
                    String alb[] = new String[album.length-1];
                    String so[][] = new String[album.length-1][];
                    for(int j = 0; j < album.length - 1; j++)
                    {
                        if( j < i )
                        {
                            alb[j] = album[j];
                            so[j] = song[j];
                        }
                        if( j >= i )
                        {
                            alb[j] = album[j+1];
                            so[j] = song[j+1];
                        }
                    }
                    album = alb;
                    song = so;
                }
            }

            for(int i = 0; i < song.length; i++ )
            {
                System.out.println("###################");
                for(int j = 0; j < song[i].length; j++)
                {
                    System.out.println(song[i][j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
