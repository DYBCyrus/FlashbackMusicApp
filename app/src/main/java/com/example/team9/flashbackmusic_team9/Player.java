package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Kent on 2/6/2018.
 */

public class Player {
    private static MediaPlayer player = new MediaPlayer();
    private static Track currentTrack;

    public static void setPlayer(MediaPlayer playerToSet) {
        player = playerToSet;
    }
    public static void setCurrentTrackLocation(Location loc) {
        currentTrack.setLocation(loc);
    }
    public static void setCurrentTrackTime(Date date) {currentTrack.setDate(date);}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void start(Track track) {
        player.reset();
        try {
            AssetFileDescriptor afd = track.getDescriptor();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            currentTrack = track;
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentTrack = track;
        player.prepareAsync();
    }

    public static MediaPlayer getPlayer(){ return player; }
    public static Track getCurrentTrack(){ return currentTrack; }

    public static void pause() {
        player.pause();
    }
}
