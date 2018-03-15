package com.example.team9.flashbackmusic_team9;

/**
 * Created by yikuanxia on 3/10/18.
 */

public interface ITrack extends Comparable<ITrack> {
    boolean isPlayable();
    boolean hasDownloaded();
    String getName();
    Track getTrack();
}
