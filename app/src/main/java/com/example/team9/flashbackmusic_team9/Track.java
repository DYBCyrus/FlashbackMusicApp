package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Track
{
    private String name;
    private String artist;
    private AssetFileDescriptor afd;
    private Album album;
    private Date date;
    private Location location;
    private FavoriteStatus status;

    public Track(String name, String artist, Album album, AssetFileDescriptor afd) {
        this.name = name;
        this.album = album;
        this.afd = afd;
        this.artist = artist;
        this.status = FavoriteStatus.NEUTRAL;
    }

    public enum FavoriteStatus {
        LIKE,DISLIKE,NEUTRAL;
    }

    public void setStatus(FavoriteStatus status) {this.status = status;}

    public FavoriteStatus getStatus() {return status;}

    public boolean isPlayable() {return status != FavoriteStatus.DISLIKE;}

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        name = n;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String n)
    {
        artist = n;
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date d)
    {
        date = d;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
    }
}
