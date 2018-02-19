package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kent on 2/7/2018.
 */

public class DataBase {
    private static ArrayList<Track> allTracks = new ArrayList<>();
    private static ArrayList<Album> allAlbums = new ArrayList<>();

    public static ArrayList<Track> getAllTracks() {
        return allTracks;
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

    public static void loadFile(MainActivity main)
    {
        Field[] fields = R.raw.class.getFields();
        try {
            HashMap<String, Album> map = new HashMap<>();
            for (Field each : fields) {
                System.out.println(each.toString());
                int id = each.getInt(each);
                AssetFileDescriptor afd = main.getResources().openRawResourceFd(id);
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                String albumName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                String trackName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artistName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

                if (map.get(albumName) == null) {
                    Album newAlbum = new Album(albumName);
                    allAlbums.add(newAlbum);
                    map.put(albumName, newAlbum);
                }
                Album al = map.get(albumName);
                Track newTrack = new Track(trackName, artistName, al, afd);
                al.addTrack(newTrack);
                allTracks.add(newTrack);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
