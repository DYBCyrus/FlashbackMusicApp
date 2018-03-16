package com.example.team9.flashbackmusic_team9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yikuanxia on 3/15/18.
 */

public class NetworkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if(info.getType() == ConnectivityManager.TYPE_WIFI)
                System.out.println("connected");

            if(info.getType() == ConnectivityManager.TYPE_MOBILE)
                System.out.println("connected");
        }
        System.out.println("not connected");
    }
}
