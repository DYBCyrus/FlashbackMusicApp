package com.example.team9.flashbackmusic_team9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.location.FusedLocationProviderClient;
import java.io.IOException;
import java.util.ArrayList;

public class FlashBackActivity extends PlayingActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back.setText("NORMAL");

        Button viewPlayListButton = findViewById(R.id.viewPlaylist);
        viewPlayListButton.setVisibility(View.VISIBLE);
        viewPlayListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchViewPlaylistActivity();
            }
        });
    }

    public void launchViewPlaylistActivity() {
        Intent intent = new Intent(this, ViewPlaylistActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        ArrayList<MockTrack> mockTracks = new ArrayList<>();
        for (Track each : DataBase.getAllTracks()) {
            mockTracks.add(each.getMockTrack());
        }
        try {
            editor.putString("tracks", ObjectSerializer.serialize(mockTracks));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString("lastActivity", "flash");
        editor.apply();
    }

    public void setMode() {
        SharedPreferences prefs = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "normal");
        editor.apply();
    }

    @Override
    public void finish() {
        super.finish();
        setMode();
        Player.reset();
    }

}
