package tests;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.support.test.rule.ActivityTestRule;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.Track;
import com.example.team9.flashbackmusic_team9.Album;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDate;

import java.time.LocalDateTime;

public class JUnitTestTrack {

    private Track track;
    private String name;
    private String artist;
    private AssetFileDescriptor afd;
    private Album album;
    private LocalDateTime date;
    private Location location;
    private Track.FavoriteStatus status;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp()
    {
        name = "name";
        artist = "artist";
        afd = null;
        album = new Album("name");
        status = Track.FavoriteStatus.NEUTRAL;
        track = new Track(name, artist, album,"");
    }


    @Test
    public void testSetStatus() {
        Assert.assertEquals(Track.FavoriteStatus.NEUTRAL, track.getStatus());

        track.setStatus(Track.FavoriteStatus.DISLIKE);
        Assert.assertEquals(Track.FavoriteStatus.DISLIKE, track.getStatus());

        track.setStatus(Track.FavoriteStatus.LIKE);
        Assert.assertEquals(Track.FavoriteStatus.LIKE, track.getStatus());
    }

    @Test
    public void testGetStatus(){
        Assert.assertEquals(Track.FavoriteStatus.NEUTRAL, track.getStatus());

        track.setStatus(Track.FavoriteStatus.DISLIKE);
        Assert.assertEquals(Track.FavoriteStatus.DISLIKE, track.getStatus());

        track.setStatus(Track.FavoriteStatus.LIKE);
        Assert.assertEquals(Track.FavoriteStatus.LIKE, track.getStatus());
    }

    @Test
    public void testIsPlayable(){

        Assert.assertTrue(track.isPlayable());

        track.setStatus(Track.FavoriteStatus.DISLIKE);
        Assert.assertFalse(track.isPlayable());

        track.setStatus(Track.FavoriteStatus.LIKE);
        Assert.assertTrue(track.isPlayable());
    }

    @Test
    public void testGetName(){
        Assert.assertEquals("name", track.getName());
        Assert.assertNotEquals("NAME", track.getName());
    }

    @Test
    public void testSetName(){
        track.setName("trackName");
        Assert.assertEquals("trackName", track.getName());
        Assert.assertNotEquals("name", track.getName());
    }

    @Test
    public void testGetArtist(){
        Assert.assertEquals("artist", track.getArtist());
        track.setArtist("ARTIST");
        Assert.assertEquals("ARTIST", track.getArtist());
        Assert.assertNotEquals("artist", track.getArtist());
    }

    @Test
    public void testSetArtist(){
        track.setArtist("ARTIST");
        Assert.assertEquals("ARTIST", track.getArtist());
        Assert.assertNotEquals("artist", track.getArtist());
    }

    @Test
    public void testGetAlbum(){
        Assert.assertEquals(album, track.getAlbum());
        Album album1 = new Album("album");
        track.setAlbum(album1);
        Assert.assertEquals(album1, track.getAlbum());
        Assert.assertNotEquals(album, track.getAlbum());
    }

    @Test
    public void testSetAlbum(){
        Album album1 = new Album("album");
        track.setAlbum(album1);
        Assert.assertEquals(album1, track.getAlbum());
        Assert.assertNotEquals(album, track.getAlbum());
    }

    @Test
    public void testGetDate(){
        LocalDateTime date = LocalDateTime.MAX;
        LocalDateTime date1 = LocalDateTime.MIN;
        track.setDate(date);
        Assert.assertEquals(date, track.getDate());
        Assert.assertNotEquals(date1, track.getDate());

        track.setDate(date1);
        Assert.assertEquals(date1, track.getDate());
        Assert.assertNotEquals(date, track.getDate());
    }

    @Test
    public void testSetDate(){
        LocalDateTime date = LocalDateTime.MAX;
        LocalDateTime date1 = LocalDateTime.MIN;
        track.setDate(date);
        Assert.assertEquals(date, track.getDate());
        Assert.assertNotEquals(date1, track.getDate());

        track.setDate(date1);
        Assert.assertEquals(date1, track.getDate());
        Assert.assertNotEquals(date, track.getDate());
    }

    @Test
    public void testGetLocation(){
        Location location = new Location("location");
        Location location1 = new Location("location1");

        track.setLocation(location);
        Assert.assertEquals(location, track.getLocation());
        Assert.assertNotEquals(location1, track.getLocation());

        track.setLocation(location1);
        Assert.assertEquals(location1, track.getLocation());
        Assert.assertNotEquals(location,track.getLocation());
    }

    @Test
    public void testSetLocation(){

        Location location = new Location("location");
        Location location1 = new Location("location1");

        track.setLocation(location);
        Assert.assertEquals(location, track.getLocation());
        Assert.assertNotEquals(location1, track.getLocation());

        track.setLocation(location1);
        Assert.assertEquals(location1, track.getLocation());
        Assert.assertNotEquals(location,track.getLocation());

    }

    @Test
    public void testCompareTo()
    {
        // same time, different location, same day, same like
        Location location = new Location("");
        LocalDateTime dateTime = LocalDateTime.of(2018, 3, 3,10, 5);
        location.setLatitude(116.4074);
        location.setLongitude(39.9042);
        MainActivity.setmLocation(location);

        Album album1 = new Album("album1");
        Track track1 = new Track("Track1","Tony", album1, null);
        track1.setLocation(location);
        track1.setDate(dateTime);

        Track track2 = new Track("Track2","Tony", album1, null);

        Location location1 = new Location("");
        location1.setLatitude(97.4074);
        location1.setLongitude(37.9042);
        track2.setLocation(location1);
        track2.setDate(dateTime);

        track1.setMockHour(10);
        track1.setMockDate(3);
        Assert.assertTrue(track1.compareTo(track2) < 0);

        // different time, same location, same day, same like

        LocalDateTime dateTime2 = LocalDateTime.of(2018, 3, 3,3, 5);
        location.setLatitude(116.4074);
        location.setLongitude(39.9042);
        MainActivity.setmLocation(location);

        track1.setLocation(location);
        track1.setDate(dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2018, 3, 3,7, 5);

        track2.setLocation(location);
        track2.setDate(dateTime3);

        track1.setMockHour(7);
        track1.setMockDate(3);
        Assert.assertTrue(track1.compareTo(track2) > 0);

        // same time, same location, different day, same like
        dateTime2 = LocalDateTime.of(2018, 3, 5,3, 5);
        location.setLatitude(116.4074);
        location.setLongitude(39.9042);
        MainActivity.setmLocation(location);

        track1.setLocation(location);
        track1.setDate(dateTime2);

        dateTime3 = LocalDateTime.of(2018, 3, 3,3, 5);

        track2.setLocation(location);
        track2.setDate(dateTime3);

        track1.setMockHour(7);
        track1.setMockDate(6);
        Assert.assertTrue(track1.compareTo(track2) > 0);

        // same time, same location, same day, different like
        location.setLatitude(116.4074);
        location.setLongitude(39.9042);
        MainActivity.setmLocation(location);

        track1.setLocation(location);
        track1.setDate(dateTime);
        track1.setStatus(Track.FavoriteStatus.NEUTRAL);

        track2.setLocation(location);
        track2.setDate(dateTime);
        track2.setStatus(Track.FavoriteStatus.LIKE);

        track1.setMockHour(7);
        track1.setMockDate(6);

        Assert.assertTrue(track1.compareTo(track2) > 0);

        // score same status same
        dateTime2 = LocalDateTime.of(2018, 3, 3,6, 5);
        dateTime3 = LocalDateTime.of(2018, 3, 3,7, 5);
        MainActivity.setmLocation(location);


        track1.setLocation(location);
        track1.setDate(dateTime2);
        track1.setStatus(Track.FavoriteStatus.LIKE);

        track2.setLocation(location);
        track2.setDate(dateTime3);
        track2.setStatus(Track.FavoriteStatus.LIKE);

        track1.setMockHour(7);
        track1.setMockDate(6);

        Assert.assertTrue(track1.compareTo(track2) > 0);

        // location time both null
        MainActivity.setmLocation(location);


        track1 = new Track("Track1","Tony", album1, null);

        track2 = new Track("Track1","Tony", album1, null);

        track1.setMockHour(7);
        track1.setMockDate(6);

        Assert.assertTrue(track1.compareTo(track2) == 0);
    }
}
