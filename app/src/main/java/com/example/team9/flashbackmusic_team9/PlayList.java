package com.example.team9.flashbackmusic_team9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 * Created by cyrusdeng on 09/02/2018.
 */

public class PlayList implements ListIterator<ITrack> {

    private ArrayList<? extends ITrack> playingTracks;
    private ArrayList<? extends ITrack> viewTracks;
    private int index;
    private boolean isLooping = false;

    public void setLooping(boolean isLooping) {this.isLooping = isLooping;}
    public ArrayList<? extends ITrack> getPlayingTracks() {return playingTracks;}
    public void setPlayingTracks(ArrayList<? extends ITrack> tracks) {this.playingTracks = tracks;}
    public void setViewTracks(ArrayList<? extends ITrack> viewTracks) {
        this.viewTracks = viewTracks;
    }
    public ArrayList<? extends ITrack> getViewTracks() {return this.viewTracks;}

    public PlayList(ArrayList<? extends ITrack> tracks, boolean looping) {
        playingTracks = tracks;
        viewTracks = tracks;
        index = -1;
        isLooping = looping;
    }

    @Override
    public boolean hasNext() {
        int cur = index;
        if (index+1 >= playingTracks.size()) {
            if(isLooping) {
                Collections.sort(playingTracks);
                index = -1;
            } else {
                index = playingTracks.size();
                return false;
            }
        }
        while (index+1 < playingTracks.size() && !playingTracks.get(index+1).isPlayable()) {
            index++;
            if (isLooping) {
                if (index==cur) {
                    return false;
                }
                if (index+1 >= playingTracks.size()) {
                    Collections.sort(playingTracks);
                    index = -1;
                }
            }
        }
        if (index+1 >= playingTracks.size()) {
            return false;
        }
        return true;
    }

    @Override
    public Track next() {

        return playingTracks.get(++index).getTrack();
    }

    @Override
    public boolean hasPrevious() {
        int cur = index;
        if (index <= 0) {
            if(isLooping) {
                index = playingTracks.size();
            } else {
                index = -1;
                return false;
            }        }
        while (index-1 >= 0 && !playingTracks.get(index-1).isPlayable()) {
            index--;
            if (isLooping) {
                if (index-1 < 0) {
                    index = playingTracks.size();
                }
                if (index==cur) {
                    return false;
                }
            }
        }
        if (index-1 < 0) {
            index--;
            return false;
        }
        return true;    }

    @Override
    public Track previous() {
        return playingTracks.get(--index).getTrack();
    }

    @Override
    public int nextIndex() {
        return index+1;
    }

    @Override
    public int previousIndex() {
        return index-1;
    }

    @Override
    public void remove() {
        if (playingTracks != null) {
            playingTracks.clear();
        }
    }

    @Override
    public void set(ITrack track) {}

    @Override
    public void add(ITrack track) {}

}
