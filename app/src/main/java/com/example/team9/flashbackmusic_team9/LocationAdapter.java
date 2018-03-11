package com.example.team9.flashbackmusic_team9;

import android.location.Location;

/**
 * Created by Chutong on 3/8/18.
 */

public class LocationAdapter implements ILocation
{
    Location location;

    public LocationAdapter(Location loc)
    {
        location = loc;
    }
    public double getLatitude()
    {
        return location. getLatitude();
    }
    public double getLongitude()
    {
        return location.getLongitude();
    }

    @Override
    public void setLatitude(double latitude) {
        location.setLatitude(latitude);
    }

    @Override
    public void setLongitude(double longitude)
    {
        location.setLongitude(longitude);
    }

    @Override
    public float distanceTo(ILocation loc) {
        Location location1 = new Location("");
        location1.setLongitude(this.getLongitude());
        location1.setLatitude(this.getLatitude());
        Location location2 = new Location("");
        location2.setLongitude(loc.getLongitude());
        location2.setLatitude(loc.getLatitude());
        return location1.distanceTo(location2);
    }
}
