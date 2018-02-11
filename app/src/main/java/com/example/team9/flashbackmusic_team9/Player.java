package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Kent on 2/6/2018.
 */

public class Player {
    private static MediaPlayer player;
    private static Track currentTrack;
    private static PlayList currentPlayList;

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

    public static boolean playPrevious() {
        player.reset();
        if (currentPlayList != null && currentPlayList.hasPrevious()) {
            start(currentPlayList.previous());
            return true;
        }
        return false;
    }
    public static boolean playNext() {
        player.reset();
        if (currentPlayList != null && currentPlayList.hasNext()) {
            start(currentPlayList.next());
            return true;
        }
        return false;
    }
    public static void playPlaylList(PlayList playList) {
        currentPlayList = playList;
        start(currentPlayList.next());
    }
    public static boolean isPlaying() {
        return player.isPlaying();
    }

    public static void pause() {
        player.pause();
        PlayerToolBar.updateToolbar();
    }
    public static void resume() {
        player.start();
    }
    public static MediaPlayer getPlayer(){
        return player;
    }
    public static Track getCurrentTrack(){
        return currentTrack;
    }

    public static void clearPlayList() {
        currentPlayList = null;
    }
    public static PlayList getPlayList() {
        return currentPlayList;
    }
    public static void setPlayList(PlayList playList1) {
        currentPlayList = playList1;
    }

}
