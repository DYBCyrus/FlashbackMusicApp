package com.example.team9.flashbackmusic_team9;

import java.util.Comparator;

/**
 * Created by cyrusdeng on 13/03/2018.
 */

public class ArtistComparator implements Comparator<Track> {
    public ArtistComparator(){}
    @Override
    public int compare(Track track, Track t1) {
        return track.getArtist().compareTo(t1.getArtist());
    }
}
