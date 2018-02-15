package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import java.time.LocalDateTime;
import java.util.Stack;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Track
{
    private String name;
    private String artist;
    private AssetFileDescriptor afd;
    private Album album;
    private LocalDateTime date;
    private Location location;
    private FavoriteStatus status;
    private Stack<FavoriteStatusButton> fsButtons = new Stack<>();

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
    public void setStatus(FavoriteStatus status) {
        this.status = status;
        updateListeningFavoriteStatusButton();
        Updateables.updateAll();
    }

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

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime d)
    {
        date = d;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public void addListeningFavoriteStatusButton(FavoriteStatusButton button) {
        button.bindTrack(this);
        fsButtons.add(button);
    }
    public void popListeningFavoriteStatusButton() {
        fsButtons.pop();
    }
    public void updateListeningFavoriteStatusButton() {
        for (FavoriteStatusButton each : fsButtons) {
            each.updateImage();
        }
    }
}
