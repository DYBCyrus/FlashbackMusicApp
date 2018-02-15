package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Kent on 2/14/2018.
 */

public class FavoriteStatusButton extends ImageButton {
    private Track bindedTrack;
    public FavoriteStatusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void bindTrack(Track track) {
        bindedTrack = track;
        updateImage();
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bindedTrack.getStatus() == Track.FavoriteStatus.NEUTRAL) {
                    bindedTrack.setStatus(Track.FavoriteStatus.LIKE);
                }
                else if (bindedTrack.getStatus() == Track.FavoriteStatus.LIKE) {
                    bindedTrack.setStatus(Track.FavoriteStatus.DISLIKE);
                }
                else {
                    bindedTrack.setStatus(Track.FavoriteStatus.NEUTRAL);
                }
            }
        });
    }
    public void updateImage() {
        if (bindedTrack.getStatus() == Track.FavoriteStatus.NEUTRAL) {
            super.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.plus,
                null));
        }
        else if (bindedTrack.getStatus() == Track.FavoriteStatus.LIKE) {
            super.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.check_mark,
                    null));
        }
        else {
            super.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.x,
                    null));
        }
    }
}
