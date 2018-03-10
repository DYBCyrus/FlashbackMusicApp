package com.example.team9.flashbackmusic_team9;

import android.location.Location;

/**
 * Created by Chutong on 3/8/18.
 */

public interface ILocation
{
    double getLatitude();
    double getLongitude();

    float distanceTo(ILocation location);
}
