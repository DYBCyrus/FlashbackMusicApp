package com.example.team9.flashbackmusic_team9;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by cyrusdeng on 09/02/2018.
 */

public class PlayList implements ListIterator<Track> {

    private ArrayList<Track> playingTracks;
    private ListIterator<Track> iter;
    private boolean isLooping = false;

    public void setLooping(boolean isLooping) {this.isLooping = isLooping;}

//    public PlayList(ArrayList<Track> tracks, int index, boolean looping) {
//        playingTracks = tracks;
//        iter = playingTracks.listIterator(index);
//        isLooping = looping;
//    }
    public PlayList(ArrayList<Track> tracks, boolean looping) {
        playingTracks = tracks;
        iter = playingTracks.listIterator();
        isLooping = looping;
    }

    @Override
    public boolean hasNext() {
        if (!iter.hasNext() && !isLooping) {
            return false;
        } else if (!iter.hasNext() && isLooping) {
            iter = playingTracks.listIterator();
        }

        Track tr = iter.next();
        while (!tr.isPlayable()){
            if (iter.hasNext()) {
                tr = iter.next();
            } else {
                return false;
            }
        }
        iter.previous();
        return true;
    }

    @Override
    public Track next() {
        return iter.next();
    }

    @Override
    public boolean hasPrevious() {
        if (!iter.hasPrevious()) {
            return false;
        }

        Track tr = iter.previous();
        if (iter.hasPrevious()) {
            tr = iter.previous();
        }
        while (!tr.isPlayable()){
            if (iter.hasPrevious()) {
                tr = iter.previous();
            } else {
                return false;
            }
        }
        iter.next();
        return true;
    }

    @Override
    public Track previous() {
        Track tr = iter.previous();
        iter.next();
        return tr;
    }

    @Override
    public int nextIndex() {
        return iter.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iter.previousIndex();
    }

    @Override
    public void remove() {
        playingTracks.clear();
    }

    @Override
    public void set(Track track) {}

    @Override
    public void add(Track track) {}

}
