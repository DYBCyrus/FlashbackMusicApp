package com.example.team9.flashbackmusic_team9;

import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

/**
 * Created by Kent on 2/6/2018.
 */

public class Player {
    private MediaPlayer player;
    public Player() {
        player = new MediaPlayer();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start(Track track) {

        try {
            player.setDataSource(track.getFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();

    }
    public void pause() {
        player.pause();
    }
}
