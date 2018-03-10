package com.example.team9.flashbackmusic_team9;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LocationManager locationManager;
    private static Location mLocation;
    private SharedPreferences prefs;
    private FusedLocationProviderClient mFusedLocationClient;
    private User currentUser;

    // firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Google Sign In and People API
    private String authCode;
    private GoogleSignInClient aGoogleSignInClient;
    private HttpTransport httpTransport = new NetHttpTransport();
    private JacksonFactory jsonFactory = new JacksonFactory();
    private String clientId = "78013321666-9j6ancu1s1tr08tjqfe1kb9rtl0qeqhi.apps.googleusercontent.com";
    private String clientSecret = "omDZYzPwK1TVBQKcl-CA6VD_";
    private Scope aScope = new Scope("https://www.googleapis.com/auth/contacts.readonly");

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // firebase configuration
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:78013321666:android:3709eeeca5bbd4b0")
                .setDatabaseUrl("https://cse-110-team-project-team-9.firebaseio.com/")
                .build();
        database = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(this, options, "secondary"));
        myRef = database.getReferenceFromUrl("https://cse-110-team-project-team-9.firebaseio.com/");

        // Google Sign In configuration
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode("78013321666-9j6ancu1s1tr08tjqfe1kb9rtl0qeqhi.apps.googleusercontent.com")
                .requestScopes(aScope)
                .requestEmail()
                .build();
        aGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // request getting location
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                100);
        while (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
        }

        // mode history
        prefs = getSharedPreferences("mode", MODE_PRIVATE);
        String mode = prefs.getString("lastActivity", "");
        if (mode.compareTo("flash") == 0) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        setLocationManager();
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

        MusicDownloadManager.setup(this);
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
        Button newDownload = (Button) findViewById(R.id.downloadButton);
        newDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewDownload();
            }
        });

        Button signin = (Button)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSignInActivity();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 2) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(!GoogleSignIn.hasPermissions(account, aScope)) {
                    System.out.println("not permitted");
                    GoogleSignIn.requestPermissions(this, requestCode, account, aScope);
                }

                authCode = account.getServerAuthCode();
                currentUser = new User(account.getEmail());
                // Signed in successfully, show authenticated UI.
                new GetFriendsTaskRunner().execute();
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("error!!!!!!!", "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }



    private void launchSignInActivity() {
        Intent signInIntent = aGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);
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

    //  /storage/emulated/0/Download/wtf
    public void launchNewDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Type Your URL");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);
        final Context c = this;
        // Set up the buttons
        builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = input.getText().toString();
                MusicDownloadManager.startDownloadTask(url);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
    @SuppressLint("MissingPermission")
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
        locationManager.requestLocationUpdates(locationProvider, 0, 0,
                locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length >= 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                }
                return;
            }
        }
    }

    /**
     * Get current location
     * @return current location
     */
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

    /**
     * set up people api and get a list of google contacts
     * reference: https://developers.google.com/people/v1/getting-started
     * https://developers.google.com/people/v1/read-people
     * https://developers.google.com/identity/sign-in/android/start-integrating
     * @throws IOException
     */


    /**
     * AsyncTask for getting google contact list
     * People API: https://developers.google.com/people/v1/read-people
     */
    private class GetFriendsTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                // Step 2: Exchange -->
                GoogleTokenResponse tokenResponse =
                        new GoogleAuthorizationCodeTokenRequest(
                                httpTransport, jsonFactory, clientId, clientSecret, authCode, "")
                                .execute();
                // End of Step 2 <--

                GoogleCredential credential = new GoogleCredential().setAccessToken(tokenResponse.getAccessToken());

                PeopleService peopleService =
                        new PeopleService.Builder(httpTransport, jsonFactory, credential).build();

                ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                        .setPersonFields("emailAddresses")
                        .execute();
                List<Person> connections = response.getConnections();
                System.out.println(connections.size());
                for (Person person : connections) {
                    for (EmailAddress email : person.getEmailAddresses()) {
                        currentUser.addFriend(email.getValue());
//                        System.out.println(email.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
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
}
