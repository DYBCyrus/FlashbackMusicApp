package com.example.team9.flashbackmusic_team9;

import android.location.Location;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by cyrusdeng on 18/02/2018.
 */

public class MockTrack implements Serializable {
    private int year = -1;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private double longitude = 9999;
    private double latitude;
    private Track.FavoriteStatus status;

    public MockTrack(Track.FavoriteStatus status) {
        this.status = status;
    }

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

    public Track.FavoriteStatus getStatus() {return status;}

    public void setDate(LocalDateTime date) {
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
        this.hour = date.getHour();
        this.minute = date.getMinute();
        this.second = date.getSecond();
    }
    public void setLocation(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }
    public void setStatus(Track.FavoriteStatus status) {this.status = status;}
}
