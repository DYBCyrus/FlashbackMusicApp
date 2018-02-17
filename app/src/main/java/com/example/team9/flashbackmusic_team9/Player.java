package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Kent on 2/6/2018.
 */

public class Player {
    private static MediaPlayer player;
    private static Track currentTrack;
    private static PlayList currentPlayList;

    public static void start(Track track) {
        player.reset();
//        if (track.getStatus() == Track.FavoriteStatus.DISLIKE) {
//            playNext();
//            return;
//        }
        try {
            AssetFileDescriptor afd = track.getDescriptor();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            currentTrack = track;
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();
    }

    public static boolean playPrevious() {
        player.reset();
        if (currentPlayList != null && currentPlayList.hasPrevious()) {
            start(currentPlayList.previous());
            return true;
        }
        currentTrack = null;
        Updateables.updateAll();
        return false;
    }
    public static boolean playNext() {

        player.reset();
        if (currentPlayList != null && currentPlayList.hasNext()) {

            start(currentPlayList.next());
            return true;
        }
        currentTrack = null;
        Updateables.updateAll();
        return false;
    }
    public static void playPlayList(PlayList playList) {
        currentPlayList = playList;
        playNext();
    }
    public static boolean isPlaying() {
        return player.isPlaying();
    }

    public static void pause() {
        player.pause();
        Updateables.updateAll();
    }
    public static void resume() {
        player.start();
        Updateables.updateAll();
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

    public static void setPlayer(MediaPlayer playerToSet) {
        player = playerToSet;
    }
    public static void setCurrentTrackLocation(Location loc) {
        currentTrack.setLocation(loc);
    }
    public static void setCurrentTrackTime(LocalDateTime dateTime) {currentTrack.setDate(dateTime);}
    public static void reset() {
        player.reset();
        currentTrack = null;
        currentPlayList = null;
        Updateables.updateAll();
    }

}
