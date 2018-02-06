package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chutong on 2/5/18.
 */

public class LoadFile {
    public static void loadFile(MainActivity main, ArrayList<Track> tracks, ArrayList<Album> albums)
    {
        AssetManager manager = main.getAssets();
        try {
            for (String al : manager.list("")) {
                boolean notAlbum = false;
                Album alb = new Album(al);
                for (String track : manager.list(al)) {
                    if (track.substring(track.length()-4, track.length()).compareTo(".mp3")==0) {
                        FileDescriptor fd = manager.openFd(al+"/"+track).getFileDescriptor();
                        String trackName = track.substring(0, track.length()-3);
                        Track trackTobeAdd = new Track(trackName, fd);
                        tracks.add(trackTobeAdd);
                        alb.addSong(trackTobeAdd);
                    } else {
                        notAlbum = true;
                        break;
                    }
                }
                if (!notAlbum) {
                    albums.add(alb);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
