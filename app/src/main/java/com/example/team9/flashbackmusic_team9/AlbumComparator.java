package com.example.team9.flashbackmusic_team9;

import java.util.Comparator;

/**
 * Created by cyrusdeng on 13/03/2018.
 */

public class AlbumComparator implements Comparator<Track> {
    public AlbumComparator(){}
    @Override
    public int compare(Track track, Track t1) {
        if(track.getAlbum().getName().compareTo(t1.getAlbum().getName()) == 0) {
            return track.getName().compareTo(t1.getName());
        } else {
            return track.getAlbum().getName().compareTo(t1.getAlbum().getName());
        }
    }
}
