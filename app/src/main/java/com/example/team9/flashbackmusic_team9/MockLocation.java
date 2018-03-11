package com.example.team9.flashbackmusic_team9;

import android.location.Location;

/**
 * Created by Chutong on 3/8/18.
 */

public class MockLocation implements ILocation
{
    private double latitude, longitude;

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLatitude(double lat)
    {
        latitude = lat;
    }

    public void setLongitude(double lon)
    {
        longitude = lon;
    }

    public float distanceTo(ILocation loc)
    {
        Location location1 = new Location("");
        location1.setLongitude(this.getLongitude());
        location1.setLatitude(this.getLatitude());
        Location location2 = new Location("");
        location2.setLongitude(loc.getLongitude());
        location2.setLatitude(loc.getLatitude());
        return location1.distanceTo(location2);
    }

}

