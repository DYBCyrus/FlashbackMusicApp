package com.example.team9.flashbackmusic_team9;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Album implements Serializable
{
    private ArrayList<Track> songs;
    private String name;

    public Album(String name) {
        this.name = name;
        songs = new ArrayList<Track>();
    }

    public void addSong(Track song)
    {
        songs.add(song);
    }
    public void removeSong(String song)
    {
        songs.remove(song);
    }
    public String getName() { return name; }
    public ArrayList<Track> getSongs() { return songs; }
}
