package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

/**
 * Created by Kent on 2/6/2018.
 */

public class Player {
    private static MediaPlayer player = new MediaPlayer();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void start(Track track) {
        player.reset();
        try {
            AssetFileDescriptor afd = track.getDescriptor();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

    }
    public static void pause() {
        player.pause();
    }
}
