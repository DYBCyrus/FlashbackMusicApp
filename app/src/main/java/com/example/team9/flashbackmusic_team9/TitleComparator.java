package com.example.team9.flashbackmusic_team9;

import java.util.Comparator;

/**
 * Created by cyrusdeng on 14/03/2018.
 */

public class TitleComparator implements Comparator<Track> {
    public TitleComparator() {}
    @Override
    public int compare(Track track, Track t1) {
        return track.getName().compareTo(t1.getName());
    }
}
