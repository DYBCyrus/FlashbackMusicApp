package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyrusdeng on 17/02/2018.
 */

public class SimpleTrackListAdapter extends ArrayAdapter<Track> {
    public SimpleTrackListAdapter(Context context, int resourceId, ArrayList<Track> tracks) {
        super(context, resourceId, tracks);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_item, parent, false);
        }

        Track track = getItem(position);
        ((TextView)convertView.findViewById(R.id.track_names)).setText(track.getName());
        return convertView;
    }
}
