package com.example.team9.flashbackmusic_team9;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Album implements Serializable
{
    private ArrayList<Track> tracks;
    private String name;

    public Album(String name) {
        this.name = name;
        tracks = new ArrayList<Track>();
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }
    public String getName() { return name; }
    public ArrayList<Track> getTracks() { return tracks; }
}
