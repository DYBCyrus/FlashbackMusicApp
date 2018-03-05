package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.support.annotation.NonNull;
import java.time.LocalDateTime;
import java.util.Stack;

/**
 * Created by Chutong Yang on 2/5/2018.
 */

public class Track implements Comparable<Track>
{
    private MockTrack mockTrack;

    private String name;
    private String artist;
    private AssetFileDescriptor afd;
    private Album album;
    private LocalDateTime date;
    private Location location;
    private FavoriteStatus status;
    private Stack<FavoriteStatusButton> fsButtons = new Stack<>();
    private int mockHour = -1;
    private int mockDate = -1;

    public enum FavoriteStatus {
        LIKE,DISLIKE,NEUTRAL
    }

    public Track(String name, String artist, Album album, AssetFileDescriptor afd) {
        this.name = name;
        this.album = album;
        this.afd = afd;
        this.artist = artist;
        this.status = FavoriteStatus.NEUTRAL;
        mockTrack = new MockTrack(getStatus());
    }
    public MockTrack getMockTrack() {return mockTrack;}

    public void setStatus(FavoriteStatus status) {
        this.status = status;
        mockTrack.setStatus(status);
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
        mockTrack.setDate(d);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
        mockTrack.setLocation(loc);
    }

    public void setLocation(double longitude, double latitude) {
        location = new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        mockTrack.setLocation(location);
    }

    public void addListeningFavoriteStatusButton(FavoriteStatusButton button) {
        button.bindTrack(this);
        if (!fsButtons.empty() && fsButtons.peek().getContext() == button.getContext()) {
            fsButtons.pop();
        }
        fsButtons.push(button);
    }
    public void popListeningFavoriteStatusButton(Context context) {
        if (!fsButtons.empty() && fsButtons.peek().getContext() == context) {
            fsButtons.pop();
        }
    }
    public void updateListeningFavoriteStatusButton() {
        for (FavoriteStatusButton each : fsButtons) {
            each.updateImage();
        }
    }
    public boolean hasPlayHistory() {
        return date != null && status != FavoriteStatus.DISLIKE;
    }
    @Override
    public int compareTo(@NonNull Track track) {
        int thisScore = 0;
        int trackScore = 0;

        if (this.getLocation() != null &&
                this.getLocation().distanceTo(MainActivity.getmLocation()) < 305) {
            thisScore++;
        }
        if (track.getLocation() != null &&
                track.getLocation().distanceTo(MainActivity.getmLocation()) < 305) {
            trackScore++;
        }

        if (this.getDate() == null && track.getDate() == null) {
            return 0;
        }

        int first = 5;
        int second = 11;
        int third = 17;


        int currentHour = LocalDateTime.now().getHour();
        int currentDay = LocalDateTime.now().getDayOfWeek().getValue();

        if(mockHour != -1) {
            currentHour = mockHour;
        }

        if(mockDate != -1) {
            currentDay = mockDate;
        }

        int thisHour = -1;
        int trackHour = -1;
        int thisDay = -1;
        int trackDay = -1;
        if (this.getDate() != null) {
            thisHour = this.getDate().getHour();
            thisDay = this.getDate().getDayOfWeek().getValue();
        }
        if (track.getDate() != null) {
            trackHour = track.getDate().getHour();
            trackDay = track.getDate().getDayOfWeek().getValue();
        }

        if (thisDay == currentDay) {
            thisScore++;
        }
        if (trackDay == currentDay) {
            trackScore++;
        }

        if (first < currentHour && currentHour < second) {
            if (thisHour != -1 && first < thisHour && thisHour < second) {
                thisScore++;
            }
            if (trackHour != -1 && first < trackHour && trackHour < second) {
                trackScore++;
            }
        } else if (second < currentHour && currentHour < third) {
            if (thisHour != -1 &&second < thisHour && thisHour < third) {
                thisScore++;
            }
            if (trackHour != -1 && second < trackHour && trackHour < third) {
                trackScore++;
            }
        } else {
            if (thisHour != -1 && first > thisHour || thisHour > third) {
                thisScore++;
            }
            if (trackHour != -1 && first > trackHour || trackHour > third) {
                trackScore++;
            }
        }
        if (thisScore != trackScore) {
            return trackScore - thisScore;
        }
        if (this.getStatus() == FavoriteStatus.LIKE && track.getStatus() != FavoriteStatus.LIKE) {
            return -1;
        } else if (this.getStatus() != FavoriteStatus.LIKE && track.getStatus() == FavoriteStatus.LIKE) {
            return 1;
        }
        if (this.getDate() == null) {return 1;}
        if (track.getDate() == null) {return -1;}
        if (this.getDate().isAfter(track.getDate())) {
            return -1;
        } else {
            return 1;
        }
    }

    public void setMockHour(int hour)
    {
        mockHour = hour;
    }

    public void setMockDate(int date)
    {
        mockDate = date;
    }

}
