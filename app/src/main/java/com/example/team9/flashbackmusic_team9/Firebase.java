package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.logging.Logger;

/**
 * Created by cyrusdeng on 10/03/2018.
 */

public class Firebase {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


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
        LOGGER.info("URL in FireBase: "+ currentTrack.getURL().replace("/", "").replace(".", ""));
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
                    loc.setLongitude((double)trackData.child("longitude").getValue());

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
                    String title = (String)trackData.child("name").getValue();
                    MockTrack newTrack = new MockTrack(title, loc, time, user, url);
                    toDownload.add(newTrack);
                }
                Collections.sort(toDownload);
                for (MockTrack each : toDownload) {
                    System.out.println(each.getURL());
                    LOGGER.info("pullDown URL" + each.getURL());
                }
                activity.processDownload(toDownload);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static String name;
    private static TextView toUpdate;


    public static void pullDownUpdatedUser(String url, TextView t) {
        toUpdate = t;
        DatabaseReference dref = myRef.child(url.replace("/", "").replace(".", ""));
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = (String)dataSnapshot.child("userName").getValue();
                if (name == null) {
                    name = "None";
                }
                System.out.println(name);

                if (name.equals(MainActivity.getUser().getName())) {
                    SpannableString spanString = new SpannableString("you");
                    spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
                    toUpdate.setText(spanString);
                } else {
                    toUpdate.setText(name);
                }
                LOGGER.info("Name in FireBase" + name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static String getName() {return name;}
}
