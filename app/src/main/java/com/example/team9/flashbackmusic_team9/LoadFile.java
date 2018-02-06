package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.IOException;

/**
 * Created by Chutong on 2/5/18.
 */

public class LoadFile {

    private String[] album;
    private String[][] song;

    public LoadFile(MainActivity main)
    {

        AssetManager manager = main.getAssets();
        try {
            album = manager.list("");

            song = new String[album.length][];

            int i = 0;
            while (i < album.length)
            {

                song[i] = manager.list(album[i]);
                boolean s = false;
                for(String each:song[i] )
                {
                    if (each.substring(each.length()-4, each.length()).compareTo(".mp3")==0)
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
                        else if( j >= i )
                        {
                            alb[j] = album[j+1];
                            so[j] = song[j+1];
                        }
                    }
                    album = alb;
                    song = so;
                }
                else
                {
                    i++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getAlbum() {
        return album;
    }

    public String[][] getSong() {
        return song;
    }
}
