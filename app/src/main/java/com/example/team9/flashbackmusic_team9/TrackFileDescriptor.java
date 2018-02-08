package com.example.team9.flashbackmusic_team9;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;

import java.io.Serializable;

/**
 * Created by Kent on 2/6/2018.
 */

public class TrackFileDescriptor extends AssetFileDescriptor implements Serializable {
    public TrackFileDescriptor(ParcelFileDescriptor fd, long startOffset, long length) {
        super(fd, startOffset, length);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TrackFileDescriptor(ParcelFileDescriptor fd, long startOffset, long length, Bundle bundle) {
        super(fd, startOffset, length, bundle);

    }
}
