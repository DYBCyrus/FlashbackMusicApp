package com.example.team9.flashbackmusic_team9;

import android.content.Context;
import android.location.Location;
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

        DatabaseReference ref = myRef.child(currentTrack.getURL().replace("/", "").replace(".", ""));
        ref.child("name").setValue(currentTrack.getName());
        ref.child("album").setValue(currentTrack.getAlbum());
        ref.child("url").setValue(currentTrack.getURL());
        DatabaseReference historyRef = ref.child("playingHistory").push();
        historyRef.child("userName").setValue(currentTrack.getUserName());
        historyRef.child("userEmail").setValue(currentTrack.getUserEmail());
        historyRef.child("latitude").setValue(currentTrack.getLatitude());
        historyRef.child("longitude").setValue(currentTrack.getLongitude());
        historyRef.child("year").setValue(currentTrack.getYear());
        historyRef.child("month").setValue(currentTrack.getMonth());
        historyRef.child("day").setValue(currentTrack.getDay());
        historyRef.child("hour").setValue(currentTrack.getHour());
        historyRef.child("minute").setValue(currentTrack.getMinute());
        historyRef.child("second").setValue(currentTrack.getSecond());
    }

    public static void pullDown(final MainActivity activity) {
        Query query = myRef.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MockTrack> toDownload = new ArrayList<>();

                for (DataSnapshot trackData : dataSnapshot.getChildren()) {
                    System.out.println(trackData.getKey());
                    ArrayList<MockTrack> toSort = new ArrayList<>();
                    for (DataSnapshot history : trackData.child("playingHistory").getChildren()) {
                        ILocation loc = new LocationAdapter(new Location(""));
                        loc.setLatitude((double) history.child("latitude").getValue());
                        loc.setLongitude((double) history.child("longitude").getValue());

                        LocalDateTime time = LocalDateTime.of(
                                ((Long) history.child("year").getValue()).intValue(),
                                ((Long) history.child("month").getValue()).intValue(),
                                ((Long) history.child("day").getValue()).intValue(),
                                ((Long) history.child("hour").getValue()).intValue(),
                                ((Long) history.child("minute").getValue()).intValue(),
                                ((Long) history.child("second").getValue()).intValue()
                        );
                        User user = new User((String) history.child("userEmail").getValue(),
                                (String) history.child("userName").getValue());
                        String url = (String) trackData.child("url").getValue();
                        String title = (String) trackData.child("name").getValue();
                        if (!user.getEmail().equals(MainActivity.getUser().getEmail())) {
                            toSort.add(new MockTrack(title, loc, time, user, url));
                        }
                    }
                    Collections.sort(toSort);
                    if (!toSort.isEmpty()) {
                        toDownload.add(toSort.get(0));
                    }
                }
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


    public static void pullDownUpdatedUser(String url, PlayingActivity activity) {
        DatabaseReference dref = myRef.child(url.replace("/", "").replace(".", ""));
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("playingHistory").hasChildren()) {
                    activity.displayHistory(null, null, null);
                }
                else {
                    ArrayList<MockTrack> toSort = new ArrayList<>();
                    for (DataSnapshot history : dataSnapshot.child("playingHistory").getChildren()) {
                        ILocation loc = new LocationAdapter(new Location(""));
                        loc.setLatitude((double) history.child("latitude").getValue());
                        loc.setLongitude((double) history.child("longitude").getValue());

                        LocalDateTime time = LocalDateTime.of(
                                ((Long) history.child("year").getValue()).intValue(),
                                ((Long) history.child("month").getValue()).intValue(),
                                ((Long) history.child("day").getValue()).intValue(),
                                ((Long) history.child("hour").getValue()).intValue(),
                                ((Long) history.child("minute").getValue()).intValue(),
                                ((Long) history.child("second").getValue()).intValue()
                        );
                        User user = new User((String) history.child("userEmail").getValue(),
                                (String) history.child("userName").getValue());
                        String url = (String) dataSnapshot.child("url").getValue();
                        String title = (String) dataSnapshot.child("name").getValue();

                        toSort.add(new MockTrack(title, loc, time, user, url));
                    }
                    MockTrack mostRecentTrack = toSort.get(0);
                    for (int i = 1; i < toSort.size(); i++) {
                        MockTrack temp = toSort.get(i);
                        if (temp.getDateTime().isAfter(mostRecentTrack.getDateTime()) && temp.getDateTime().isBefore(MockTrackTime.now())) {
                            mostRecentTrack = temp;
                        }
                    }
                    String name = mostRecentTrack.getUserName();

                    Location loc = new Location("");
                    loc.setLatitude(mostRecentTrack.getLatitude());
                    loc.setLongitude(mostRecentTrack.getLongitude());
                    LocalDateTime date= mostRecentTrack.getDateTime();
                    activity.displayHistory(name, loc, date);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
