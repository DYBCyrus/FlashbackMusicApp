package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Kent on 2/10/2018.
 */

public class PlayerToolBar implements Updateable{
    private Button trackNameButton;
    private ImageButton previous;
    private ImageButton play;
    private ImageButton next;
    private Context context;


    public PlayerToolBar(Button b1, ImageButton b2, ImageButton b3, ImageButton b4, Context c) {
        trackNameButton = b1;
        previous = b2;
        play = b3;
        next = b4;
        context = c;

        update();
        setListener();
        Updateables.addUpdateable(this);
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
                Player.playPrevious();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.isPlaying()) {
                    Player.pause();
                }
                else if (Player.getCurrentTrack()!=null) {
                    Player.resume();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.playNext();
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
}
