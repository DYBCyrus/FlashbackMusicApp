package com.example.team9.flashbackmusic_team9;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyrusdeng on 05/02/2018.
 */

public class AlbumListAdapter extends ArrayAdapter<Album> {

    public AlbumListAdapter(Activity activity, int position, ArrayList<Album> albums) {
        super(activity, position, albums);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.album_item, parent, false);
        }

        Album album = getItem(position);
        ((TextView)convertView.findViewById(R.id.track_name)).setText(album.getName());

        return convertView;
    }
}
