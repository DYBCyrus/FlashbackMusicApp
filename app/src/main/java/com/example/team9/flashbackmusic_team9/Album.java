package com.example.team9.flashbackmusic_team9;

import java.util.ArrayList;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Album
{
    ArrayList<String> songs = new ArrayList<>();

    public void addSong(String song)
    {
        songs.add(song);
    }
    public void removeSong(String song)
    {
        songs.remove(song);
    }
}
