package com.example.team9.flashbackmusic_team9;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    public static void upload(String titleAndAuthor, Track currentTrack) {
        DatabaseReference rf = myRef.child(titleAndAuthor);
        rf.setValue(currentTrack.getMockTrack());
    }

    public static void pullDown() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Query query = myRef.orderByKey();
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            System.out.println(dataSnapshot1.getKey());
                            for (DataSnapshot data : dataSnapshot1.getChildren()) {
                                System.out.println(data.getValue());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
