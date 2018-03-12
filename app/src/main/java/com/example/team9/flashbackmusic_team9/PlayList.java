package com.example.team9.flashbackmusic_team9;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by cyrusdeng on 09/02/2018.
 */

public class PlayList implements ListIterator<ITrack> {

    private ArrayList<? extends ITrack> playingTracks;
    private int index;
//    private ListIterator<Track> iter;
    private boolean isLooping = false;

    public void setLooping(boolean isLooping) {this.isLooping = isLooping;}
    public ArrayList<? extends ITrack> getPlayingTracks() {return playingTracks;}

    public PlayList(ArrayList<? extends ITrack> tracks, boolean looping) {
        playingTracks = tracks;
//        iter = playingTracks.listIterator();
        index = -1;
        isLooping = looping;
    }

//    @Override
//    public boolean hasNext() {
//
//        if (!iter.hasNext() && !isLooping) {
//            return false;
//
//        } else if (!iter.hasNext() && isLooping) {
//
//            iter = playingTracks.listIterator();
//            if (!iter.hasNext()) {return false;}
//        }
//
//        Track tr = iter.next();
//        while (!tr.isPlayable()){
//            if (iter.hasNext()) {
//                tr = iter.next();
//            } else {
//
//                return false;
//            }
//        }
//        iter.previous();
//        return true;
//    }
//
//    @Override
//    public Track next() {
//        Track tr = iter.next();
//        return tr;
//    }
//
//    @Override
//    public boolean hasPrevious() {
//        if (!iter.hasPrevious()) {
//            return false;
//        }
//
//        Track tr = iter.previous();
//        if (iter.hasPrevious()) {
//            tr = iter.previous();
//        }
//        else {
//            return false;
//        }
//        while (!tr.isPlayable()){
//            if (iter.hasPrevious()) {
//                tr = iter.previous();
//            } else {
//                return false;
//            }
//        }
//        iter.next();
//        return true;
//    }
//
//    @Override
//    public Track previous() {
//        Track tr = iter.previous();
//        iter.next();
//        return tr;
//    }
//
//    @Override
//    public int nextIndex() {
//        return iter.nextIndex();
//    }
//
//    @Override
//    public int previousIndex() {
//        return iter.previousIndex();
//    }

    @Override
    public boolean hasNext() {
        int cur = index;
        if (index+1 >= playingTracks.size()) {
            if(isLooping) {
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
