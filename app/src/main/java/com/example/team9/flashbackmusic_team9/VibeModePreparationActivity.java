package com.example.team9.flashbackmusic_team9;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class VibeModePreparationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe_mode_preparation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView indicator = findViewById(R.id.preparationIndicator);
        indicator.setText("Starting Vibe Mode Preparation \n Fetching Data From Firebase");



    }

    public void processDownload(ArrayList<MockTrack> toDownload) {
        if (toDownload.isEmpty()) {

        }
        boolean processToVibeMode = false;
        for (MockTrack each : toDownload) {
            if (DataBase.contain(each)) {
                processToVibeMode = true;
            }
            else {
                MusicDownloadManager.startDownloadTask(each.getURL());
            }
        }
        if (processToVibeMode) {
        }

    }

}
