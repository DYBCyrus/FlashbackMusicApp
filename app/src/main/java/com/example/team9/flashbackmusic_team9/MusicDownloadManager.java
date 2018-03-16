package com.example.team9.flashbackmusic_team9;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.URLUtil;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.logging.Logger;

/**
 * Created by Kent on 3/4/2018.
 */

public class MusicDownloadManager {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PlayList viewList;
    private static Context context;
    private static DownloadManager downloadManager;
    private static MockTrack currentDownload;
    private static ListIterator<MockTrack> toDownload;
    private static boolean hasDownloadedOne = false;
    private static DownloadManager.Request currentRequest = null;
    public static void setup(Context c) {
        context = c;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        BroadcastReceiver onComplete=new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
                Cursor c = downloadManager.query(q);

                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        // process download
                        currentRequest = null;
                        String downloadFileLocalUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        String downloadUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
                        if (downloadFileLocalUri != null) {
                            System.out.println(downloadFileLocalUri);
                            File mFile = new File(downloadFileLocalUri);
                            Track loadedTrack = DataBase.addDownloadedTrack(mFile.getAbsolutePath().substring(6), downloadUri);
                            if (currentDownload != null) {
                                currentDownload.setTrack(loadedTrack);
                                loadedTrack.setDataFromMockTrack(currentDownload);

                                reorderPlayingList();

                                if (!hasDownloadedOne) {
                                    hasDownloadedOne = true;
                                    if (context instanceof MainActivity) {
                                        LOGGER.info("ccccccccccc");
                                        ((MainActivity) context).launchModeActivity();
                                        LOGGER.info("Download mode launch successfully");
                                    }
                                }
                            }
                        }
                        String downloadFileTitle = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                        Toast toast = Toast.makeText(context, downloadFileTitle, Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
                while (toDownload != null && toDownload.hasNext()) {
                    currentDownload = toDownload.next();
                    if (!currentDownload.hasDownloaded()) {
                        startDownloadTask(currentDownload.getURL());
                        break;
                    }
                }
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    public static void startDownloadTask(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        currentRequest = request;
        request.setMimeType("audio/MP3");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, null, null).replace("-","_"));

        LOGGER.info("start download");
        // get download service and enqueue file
        downloadManager.enqueue(request);
    }

    public static void resumeDownload() {
        if (currentRequest != null) {
            downloadManager.enqueue(currentRequest);
        }
    }
    public static void abortAll() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus (DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PENDING|DownloadManager.STATUS_RUNNING);
        Cursor c = downloadManager.query(query);
        while(c.moveToNext()) {
            downloadManager.remove(c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
        }
        LOGGER.info("remove all");
    }
    public static boolean downloadAll(ArrayList<MockTrack> a, boolean hasDownloadedTrack) {
        hasDownloadedOne = hasDownloadedTrack;
        toDownload = a.listIterator();
        while (toDownload != null && toDownload.hasNext()) {
            currentDownload = toDownload.next();
            if (!currentDownload.hasDownloaded()) {
                startDownloadTask(currentDownload.getURL());
                LOGGER.info("Not downloading");
                return true;
            }
        }
        if (!hasDownloadedTrack) {
            Toast toast = Toast.makeText(context, "No Vibe Mode Track Available", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

    public static void registerPlayingOrderList(PlayList viewList1) {
        viewList = viewList1;
    }

    public static void reorderPlayingList() {
        // have downloaded
        ArrayList<MockTrack> t = (ArrayList<MockTrack>)viewList.getPlayingTracks();
        Track currentPlaying = Player.getCurrentTrack();
        int i;
        for (i = 0; i < t.size(); i++) {
            if (t.get(i).getTrack().equals(currentPlaying)) {
                break;
            }
        }
        ArrayList<MockTrack> suborder = new ArrayList<>(t.subList(((i+1)>t.size() ? i : (i+1)),t.size()));
        suborder.add(currentDownload);
        Collections.sort(suborder);

        if (i+1 < t.size()) {
            t.subList(i+1,t.size()).clear();
        }
        for (MockTrack track : suborder) {
            t.add(track);
        }
        viewList.setPlayingTracks(t);
    }
}
