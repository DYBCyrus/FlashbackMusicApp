package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.location.Location;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by cyrusdeng on 10/03/2018.
 */

public class Firebase {
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;

    public static void configFirebase(Context context) {
        // firebase configuration
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:78013321666:android:3709eeeca5bbd4b0")
                .setDatabaseUrl("https://cse-110-team-project-team-9.firebaseio.com/")
                .build();
        database = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(context, options, "secondary"));
        myRef = database.getReferenceFromUrl("https://cse-110-team-project-team-9.firebaseio.com/");
    }

    public static void upload(MockTrack currentTrack) {

        myRef.child(currentTrack.getURL().replace("/", "").replace(".", "")).setValue(currentTrack);
    }

    public static void pullDown(final MainActivity activity) {
        Query query = myRef.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MockTrack> toDownload = new ArrayList<>();

                for (DataSnapshot trackData : dataSnapshot.getChildren()) {
                    System.out.println(trackData.getKey());
                    ILocation loc = new LocationAdapter(new Location(""));
                    loc.setLatitude((double)trackData.child("latitude").getValue());
                    loc.setLatitude((double)trackData.child("longitude").getValue());

                    LocalDateTime time = LocalDateTime.of(
                            ((Long)trackData.child("year").getValue()).intValue(),
                            ((Long)trackData.child("month").getValue()).intValue(),
                            ((Long)trackData.child("day").getValue()).intValue(),
                            ((Long)trackData.child("hour").getValue()).intValue(),
                            ((Long)trackData.child("minute").getValue()).intValue(),
                            ((Long)trackData.child("second").getValue()).intValue()
                    );
                    User user = new User((String)trackData.child("user").child("email").getValue(),
                            (String)trackData.child("user").child("name").getValue());
                    String url = (String)trackData.child("url").getValue();
                    MockTrack newTrack = new MockTrack(loc, time, user, url);
                    toDownload.add(newTrack);
                }
                Collections.sort(toDownload);
                for (MockTrack each : toDownload) {
                    System.out.println(each.getURL());
                }
                activity.processDownload(toDownload);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
