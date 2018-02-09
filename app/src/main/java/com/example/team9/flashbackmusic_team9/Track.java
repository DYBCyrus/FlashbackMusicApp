package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;

import java.io.Serializable;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Track implements Serializable
{
    private String name;
    private AssetFileDescriptor afd;
    private Album album;
    private int hour;
    private int date;
    private int month;

    public Track(String name, Album album, AssetFileDescriptor afd) {
        this.name = name;
        this.album = album;
        this.afd = afd;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        name = n;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album al) {
        album = al;
    }
    public AssetFileDescriptor getDescriptor(){
        return afd;
    }
    public int getHour()
    {
        return hour;
    }

    public void setHour(int h)
    {
        hour = h;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int d)
    {
        date = d;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int m) {
        month = m;
    }
}
