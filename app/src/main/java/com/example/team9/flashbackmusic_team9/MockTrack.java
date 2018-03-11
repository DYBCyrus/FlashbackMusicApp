package com.example.team9.flashbackmusic_team9;

import android.location.Location;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by cyrusdeng on 18/02/2018.
 */

public class MockTrack implements Serializable, Comparable<MockTrack>, ITrack {
    private int year = -1;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private double longitude = 9999;
    private double latitude;
    private Track.FavoriteStatus status;

    private Location location;
    private LocalDateTime dateTime;

    private String userEmail;
    private String userName;

    private String URL;

    private String title;
    private String album;

    private Track localTrack;
    public MockTrack(){}
    public MockTrack(Track.FavoriteStatus status) {
        this.status = status;
    }

    public MockTrack(Track t) {
        this.status = t.getStatus();
        this.URL = t.getUrl();
        setLocation(t.getLocation());
        setDate(t.getDate());
    }

    public MockTrack(Track t, User user) {
        this.URL = t.getUrl();
        setUser(user);
        this.album = t.getAlbum().getName();
        this.title = t.getName();
        setLocation(t.getLocation());
        setDate(t.getDate());
    }
    public MockTrack(Location loc, LocalDateTime time, User lastUser, String Url) {
        setLocation(loc);
        setDate(time);
        setUser(lastUser);
        URL = Url;
    }
    public void setUser(User user) {
        this.userName = user.getName();
        this.userEmail = user.getEmail();
    }
    public String getUserName() { return userName; }
    public String getUserEmail() {
        return userEmail;
    }
    public void setURL(String URL) { this.URL = URL; }
    public String getURL() { return URL; }

    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public int getSecond() {
        return second;
    }

    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public Location getLocation() {return location;}
    public LocalDateTime getDateTime() {return dateTime;}

    public void setTrack(Track t) {
        localTrack = t;
    }
    public Track.FavoriteStatus getStatus() {return status;}
    public String getName() {
        if (localTrack != null) {
            return localTrack.getName();
        }
        return title;
    }
    public String getAlbum() {
        if (localTrack != null) {
            return localTrack.getAlbum().getName();
        }
        return album;
    }
    public void setDate(LocalDateTime date) {
        if (location != null) {
            this.year = date.getYear();
            this.month = date.getMonthValue();
            this.day = date.getDayOfMonth();
            this.hour = date.getHour();
            this.minute = date.getMinute();
            this.second = date.getSecond();
        }
        this.dateTime = date;
    }
    public void setLocation(Location location) {
        if (location != null) {
            this.longitude = location.getLongitude();
            this.latitude = location.getLatitude();
        }
        this.location = location;
    }
    public void setStatus(Track.FavoriteStatus status) {this.status = status;}

    @Override
    public int compareTo(@NonNull MockTrack MockTrack) {
        int thisScore = 0;
        int trackScore = 0;
        int thisTieScore = 0;
        int trackTieScore = 0;

        // location compare
        if (this.getLocation().distanceTo(MainActivity.getmLocation()) < 305) {
            thisScore++;
            thisTieScore += 3;
        }
        if (MockTrack.getLocation().distanceTo(MainActivity.getmLocation()) < 305) {
            trackScore++;
            trackTieScore += 3;
        }

        // time compare
        if (this.getDateTime().isAfter(LocalDateTime.now().minusWeeks(1))) {
            thisScore++;
            thisTieScore += 2;
        }
        if (MockTrack.getDateTime().isAfter(LocalDateTime.now().minusWeeks(1))) {
            trackScore++;
            trackTieScore += 2;
        }

        // google+ friends compare
        if (MainActivity.getUser().isFriend(userEmail)) {
            thisScore++;
            thisTieScore++;
        }
        if (MainActivity.getUser().isFriend(MockTrack.getUserEmail())) {
            trackScore++;
            trackTieScore++;
        }

        // score compare
        if (thisScore != trackScore) {
            return trackScore - thisScore;
        } else if (thisTieScore != trackTieScore) {
            return trackTieScore - thisTieScore;
        }

        if (this.getDateTime().isAfter(MockTrack.getDateTime())) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isPlayable() {
        if (localTrack == null) {
            return false;
        }
        return localTrack.isPlayable();
    }

    @Override
    public boolean hasDownloaded() {
        return localTrack!=null;
    }

    @Override
    public Track getTrack() {
        return localTrack;
    }
}
