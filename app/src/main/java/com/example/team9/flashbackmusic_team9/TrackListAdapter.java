package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyrusdeng on 05/02/2018.
 */

public class TrackListAdapter extends ArrayAdapter<Track> {

    public TrackListAdapter(Context context, int resourceId, ArrayList<Track> tracks) {
        super(context, resourceId, tracks);
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Track track = getItem(position);
        ((TextView)convertView.findViewById(R.id.track_names)).setText(track.getName());
        ImageButton fav = convertView.findViewById(R.id.change_status);
        if( track.getStatus() == Track.FavoriteStatus.DISLIKE ){
            fav.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.x,
                    null));
        }
        else if( track.getStatus() == Track.FavoriteStatus.LIKE ){
            fav.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.check_mark,
                    null));
        }
        else{
            fav.setBackground(ResourcesCompat.getDrawable(getContext().getResources(),
                    R.drawable.plus, null));
        }

        fav.setTag(track);
        fav.setTag(R.id.change_status, fav);
        return convertView;
    }
}
