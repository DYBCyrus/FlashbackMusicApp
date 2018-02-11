package com.example.team9.flashbackmusic_team9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Stack;

/**
 * Created by Kent on 2/10/2018.
 */

public class PlayerToolBar {
    private Button trackNameButton;
    private ImageButton previous;
    private ImageButton play;
    private ImageButton next;
    private Context context;

    private static Stack<PlayerToolBar> toolBars = new Stack<>();


    public PlayerToolBar(Button b1, ImageButton b2, ImageButton b3, ImageButton b4, Context c) {
        trackNameButton = b1;
        previous = b2;
        play = b3;
        next = b4;
        context = c;

        update();
        setListener();
        addListeningToolbar(this);
    }
    private void setListener() {
        trackNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track tr = Player.getCurrentTrack();
                if (tr != null) {
                    Intent intent = new Intent(context, PlayingActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.playPrevious()) {
                    play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause, null));
                }
                else {
                    play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.play, null));

                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.isPlaying()) {
                    Player.pause();
                    play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.play, null));

                }
                else if (Player.getCurrentTrack()!=null) {
                    Player.resume();
                    play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause, null));

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.playNext()) {
                    play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause, null));

                }
                else {
                    play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.play, null));
                }
            }
        });
    }
    public void update() {
        if (Player.isPlaying()) {
            play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.pause, null));
        }
        else {
            play.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.play, null));
        }
        if (Player.getCurrentTrack() != null) {
            trackNameButton.setText(Player.getCurrentTrack().getName());
        }
        else {
            trackNameButton.setText("Select a Track to Play");
        }
    }
    public static void addListeningToolbar(PlayerToolBar toolBar) {
        toolBars.add(toolBar);
    }
    public static void updateToolbar() {
        for(PlayerToolBar each : toolBars) {
            each.update();
        }
    }
    public static void popToolbar() {
        toolBars.pop();
    }

}
