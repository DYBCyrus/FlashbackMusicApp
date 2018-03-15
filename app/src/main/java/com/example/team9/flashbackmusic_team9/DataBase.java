package com.example.team9.flashbackmusic_team9;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kent on 2/7/2018.
 */

public class DataBase {
    private static TrackListAdapter mainTrackListView;

    private static ArrayList<Track> shareTracks = new ArrayList<>();
    private static ArrayList<Track> allTracks = new ArrayList<>();
    private static ArrayList<Album> allAlbums = new ArrayList<>();

    public static ArrayList<Track> getAllTracks() {
        return allTracks;
    }

    public static ArrayList<Track> getShareTracks() {
        return shareTracks;
    }

    public static ArrayList<Album> getAllAlbums() {
        return allAlbums;
    }

    public static Track getTrack(int index) {
        return allTracks.get(index);
    }

    public static Album getAlbum(int index) {
        return allAlbums.get(index);
    }
    /* don't use this method unless it's necessary */
    public static void setAllTracks(ArrayList<Track> tracks) {
        allTracks = tracks;
    }
    /* don't use this method unless it's necessary */
    public static void setAllAlbums(ArrayList<Album> albums) {
        allAlbums = albums;
    }

    public static void setMainTrackListView(TrackListAdapter trackListView) {
        mainTrackListView = trackListView;
    }
    public static Track addDownloadedTrack(String path, String URL) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        System.out.println(path);
        retriever.setDataSource(path);
        String albumName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String trackName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artistName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if (albumName == null) {
            albumName = "Unknown";
        }
        if (trackName == null) {
            trackName = "Unknown";
        }
        if (artistName == null) {
            artistName = "Unknown";
        }
        Album al = null;
        for (Album each : allAlbums) {
            if (each.getName().equals(albumName)) {
                al = each;
                break;
            }
        }
        if ( al == null) {
            al = new Album(albumName);
            allAlbums.add(al);
        }
        Track newTrack = new Track(trackName, artistName, al, path);
        newTrack.setURL(URL);
        al.addTrack(newTrack);
        allTracks.add(newTrack);
        shareTracks.add(newTrack);
        System.out.println(allAlbums.size());
        System.out.println(allAlbums);
        mainTrackListView.notifyDataSetChanged();
        return newTrack;
    }
    public static void loadFile(MainActivity main)
    {
        File downloadDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] musicFiles = new File[]{};
             if (downloadDir.listFiles() != null) {
                 musicFiles=downloadDir.listFiles();
             }


        HashMap<String, Album> map = new HashMap<>();
        for (File each : musicFiles) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            String path = each.getAbsolutePath();
            System.out.println(path);

            retriever.setDataSource(path);
            String albumName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String trackName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artistName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if (albumName == null) {
                albumName = "Unknown";
            }
            if (trackName == null) {
                trackName = "Unknown";
            }
            if (artistName == null) {
                artistName = "Unknown";
            }
            if (map.get(albumName) == null) {
                Album newAlbum = new Album(albumName);
                allAlbums.add(newAlbum);
                map.put(albumName, newAlbum);
            }
            Album al = map.get(albumName);
            Track newTrack = new Track(trackName, artistName, al, path);
            al.addTrack(newTrack);
            allTracks.add(newTrack);
            shareTracks.add(newTrack);
        }

    }

    public static boolean contain(MockTrack m) {
        for (Track each : allTracks) {
            if (m.getURL().equals(each.getUrl())) {
                System.out.println(each.getUrl());
                m.setTrack(each);
                return true;
            }
        }
        return false;
    }
}
