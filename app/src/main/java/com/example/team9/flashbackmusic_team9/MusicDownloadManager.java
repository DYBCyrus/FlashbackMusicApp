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

import java.io.File;

/**
 * Created by Kent on 3/4/2018.
 */

public class MusicDownloadManager {
    private static Context context;
    private static DownloadManager downloadManager;
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
                        if (downloadFileLocalUri != null) {
                            System.out.println(downloadFileLocalUri);
                            File mFile = new File(downloadFileLocalUri);
                            DataBase.addLocalTrack(mFile.getAbsolutePath().substring(6));
                        }
                        String downloadFileTitle = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                        Toast toast = Toast.makeText(context, downloadFileTitle, Toast.LENGTH_SHORT);
                        toast.show();
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

}
