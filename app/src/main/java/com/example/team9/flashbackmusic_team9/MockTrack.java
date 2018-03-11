package com.example.team9.flashbackmusic_team9;

import android.location.Location;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by cyrusdeng on 18/02/2018.
 */

public class MockTrack implements Serializable, Comparable<MockTrack> {
    private int year = -1;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private double longitude = 9999;
    private double latitude;
    private Track.FavoriteStatus status;

    private ILocation location;
    private LocalDateTime dateTime;

    private String user;
    private String URL;

    public MockTrack(Track.FavoriteStatus status) {
        this.status = status;
    }

    public void setUser(String user) { this.user = user; }
    public String getUser() { return user; }
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
    public ILocation getLocation() {return location;}
    public LocalDateTime getDateTime() {return dateTime;}

    public Track.FavoriteStatus getStatus() {return status;}

    public void setDate(LocalDateTime date) {
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
        this.hour = date.getHour();
        this.minute = date.getMinute();
        this.second = date.getSecond();
        this.dateTime = date;
    }
    public void setLocation(ILocation location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
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
        if (this.getLocation().distanceTo(new LocationAdapter(MainActivity.getmLocation())) < 305) {
            thisScore++;
            thisTieScore += 3;
        }
        if (MockTrack.getLocation().distanceTo(new LocationAdapter(MainActivity.getmLocation())) < 305) {
            trackScore++;
            trackTieScore += 3;
        }

        // time compare
        if (this.getDateTime().isAfter(MockTrackTime.now().minusWeeks(1))) {
            thisScore++;
            thisTieScore += 2;
        }
        if (MockTrack.getDateTime().isAfter(MockTrackTime.now().minusWeeks(1))) {
            trackScore++;
            trackTieScore += 2;
        }

        // google+ friends compare


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
}
