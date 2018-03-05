package com.example.team9.flashbackmusic_team9;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LocationManager locationManager;
    private static Location mLocation;
    private SharedPreferences prefs;
    private FusedLocationProviderClient mFusedLocationClient;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:78013321666:android:3709eeeca5bbd4b0")
                .setDatabaseUrl("https://cse-110-team-project-team-9.firebaseio.com/")
                .build();

        database = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(this, options, "secondary"));
        myRef = database.getReferenceFromUrl("https://cse-110-team-project-team-9.firebaseio.com/");

        // request getting location
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                100);

        // mode history
        prefs = getSharedPreferences("mode", MODE_PRIVATE);
        String mode = prefs.getString("lastActivity", "");

        if (mode.compareTo("flash") == 0) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mLocation = location;
                                launchModeActivity();
                            }
                        }
                    });
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            setLocationManager();
        }
        // load music files
        DataBase.loadFile(this);

        ArrayList<MockTrack> currentTasks = new ArrayList<>();
        try {
            currentTasks = (ArrayList<MockTrack>) ObjectSerializer.deserialize(
                    prefs.getString("tracks", ObjectSerializer.serialize(new ArrayList<MockTrack>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Track> allTracks = DataBase.getAllTracks();
        if (currentTasks.size() == allTracks.size()) {
            for (int i = 0; i < allTracks.size(); i++) {
                MockTrack current = currentTasks.get(i);
                allTracks.get(i).setStatus(current.getStatus());
                if (current.getYear() != -1) {
                    allTracks.get(i).setDate(LocalDateTime.of(current.getYear(), current.getMonth(),
                            current.getDay(), current.getHour(), current.getMinute(),
                            current.getSecond()));
                }
                if (current.getLongitude() != 9999) {
                    allTracks.get(i).setLocation(current.getLongitude(), current.getLatitude());
                    LOGGER.info(current.getLongitude()+", "+ current.getLatitude());

                }
            }
        }

        // set static player
        playerAction();

        // all UI things
        PlayerToolBar playerToolBar = new PlayerToolBar((Button)findViewById(R.id.trackName_button),
                (ImageButton)findViewById(R.id.previous_button),
                (ImageButton)findViewById(R.id.play_button),
                (ImageButton)findViewById(R.id.next_button), this);
        ListAdapter tracksAdapter = new TrackListAdapter(this,
                R.layout.list_item, DataBase.getAllTracks());
        ListView trackView = (ListView) findViewById(R.id.track_list);
        trackView.setAdapter(tracksAdapter);
        trackView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapterView.getAdapter().getItem(i);
                Player.clearPlayList();
                if (track.getStatus() != Track.FavoriteStatus.DISLIKE) {
                    Player.start(track);
                    launchPlayingActivity();
                }
            }
        });


        Button showAlbums = (Button) findViewById(R.id.all_albums);
        showAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAllAlbumsActivity();
            }
        });
        Button modeChange = (Button)findViewById(R.id.mode);
        modeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchModeActivity();
            }
        });
    }

    /**
     * Switch to playing view when starting to play a track
     */
    public void launchPlayingActivity() {
        Intent intent = new Intent(this, PlayingActivity.class);
        startActivity(intent);
    }

    /**
     * Switch to all albums view
     */
    public void launchAllAlbumsActivity() {
        Intent intent = new Intent(this, AllAlbumsActivity.class);
        startActivity(intent);
    }

    /**
     * Switch to flashback mode view
     */
    public void launchModeActivity() {
        ArrayList<Track> list = new ArrayList<>();
        for (Track each:DataBase.getAllTracks()) {
            if (each.hasPlayHistory()) {
                list.add(each);
            }
        }
        Collections.sort(list);

        PlayList flashbackList = new PlayList(list, true);
        if (flashbackList.hasNext()) {
            Intent intent = new Intent(this, FlashBackActivity.class);
            Player.playPlayList(flashbackList);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("No track available for flashback");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Got it",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    /**
     * Initialize and set static player, include onPrepare and onCompletion
     * While onCompletion, set current location and time
     */
    public void playerAction() {
        MediaPlayer player = new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                Updateables.updateAll();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Player.setCurrentTrackLocation(mLocation);
                Player.setCurrentTrackTime(TrackTime.now());

                Track currentTrack = Player.getCurrentTrack();
                String titleAndAuthor = Player.getCurrentTrack().getName() + "@" +
                        Player.getCurrentTrack().getArtist();
                DatabaseReference rf = myRef.child(titleAndAuthor);
                rf.setValue(currentTrack.getMockTrack());

                if (!Player.playNext()) {
                    Updateables.updateAll();
                }
            }
        });
        Player.setPlayer(player);
    }

    /**
     * Set current location manager
     */
    public void setLocationManager() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocation = location;
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s){}
        };

        String locationProvider = LocationManager.GPS_PROVIDER;
        while (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(locationProvider, 0, 0,
                locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    setLocationManager();
                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public static Location getmLocation() {return mLocation;}

    /**
     * Record the mode when exit the app
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ArrayList<MockTrack> mockTracks = new ArrayList<>();
        for (Track each : DataBase.getAllTracks()) {
            mockTracks.add(each.getMockTrack());
        }
        try {
            editor.putString("tracks", ObjectSerializer.serialize(mockTracks));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString("lastActivity", "normal");
        editor.apply();
    }

    /**
     * setter for current location
     * @param loc
     */
    public static void setmLocation(Location loc)
    {
        mLocation = loc;
    }
}
