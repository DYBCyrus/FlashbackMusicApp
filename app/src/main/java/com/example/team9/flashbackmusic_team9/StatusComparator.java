package com.example.team9.flashbackmusic_team9;

import java.util.Comparator;

/**
 * Created by cyrusdeng on 13/03/2018.
 */

public class StatusComparator implements Comparator<Track> {
    public StatusComparator() {}
    @Override
    public int compare(Track track, Track t1) {
        if (track.getStatus() == t1.getStatus()) {
            return track.getName().compareTo(t1.getName());
        } else if (track.getStatus() == Track.FavoriteStatus.LIKE) {
            return -1;
        } else if (track.getStatus() == Track.FavoriteStatus.NEUTRAL) {
            if (t1.getStatus() == Track.FavoriteStatus.LIKE)
                return 1;
            else
                return -1;
        } else {
            return 1;
        }
    }
}
