package com.example.team9.flashbackmusic_team9;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.webkit.URLUtil;
import android.widget.Toast;

import org.mortbay.jetty.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Kent on 3/4/2018.
 */

public class MusicDownloadManager {
    private static Context context;
    private static DownloadManager downloadManager;
    private static MockTrack currentDownload;
    private static ListIterator<MockTrack> toDownload;
    private static boolean hasDownloadedOne = false;
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
                        String downloadFileLocalUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        String downloadUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
                        if (downloadFileLocalUri != null) {
                            System.out.println(downloadFileLocalUri);
                            File mFile = new File(downloadFileLocalUri);
                            Track loadedTrack = DataBase.addDownloadedTrack(mFile.getAbsolutePath().substring(6), downloadUri);
                            if (currentDownload != null) {
                                currentDownload.setTrack(loadedTrack);
                                loadedTrack.setDataFromMockTrack(currentDownload);
                                if (!hasDownloadedOne) {
                                    hasDownloadedOne = true;
                                    if (context instanceof MainActivity) {
                                        System.out.println("ccccccccccc");

                                        ((MainActivity) context).launchModeActivity();
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
        request.setMimeType("audio/MP3");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, null, null));

        // get download service and enqueue file
        downloadManager.enqueue(request);
    }

    public static void abortAll() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus (DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PENDING|DownloadManager.STATUS_RUNNING);
        Cursor c = downloadManager.query(query);
        while(c.moveToNext()) {
            downloadManager.remove(c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
        }
    }
    public static boolean downloadAll(ArrayList<MockTrack> a, boolean hasDownloadedTrack) {
        hasDownloadedOne = hasDownloadedTrack;
        toDownload = a.listIterator();
        while (toDownload != null && toDownload.hasNext()) {
            currentDownload = toDownload.next();
            if (!currentDownload.hasDownloaded()) {
                startDownloadTask(currentDownload.getURL());
                System.out.println("bbbbbbbbbb");

                return true;
            }
        }
        if (!hasDownloadedTrack) {
            Toast toast = Toast.makeText(context, "No Vibe Mode Track Available", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }
}
