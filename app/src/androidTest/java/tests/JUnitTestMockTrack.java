package tests;

import android.location.Location;
import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.MockLocation;
import com.example.team9.flashbackmusic_team9.MockTrack;
import com.example.team9.flashbackmusic_team9.MockTrackTime;
import com.example.team9.flashbackmusic_team9.Track;
import com.example.team9.flashbackmusic_team9.User;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by Chutong on 3/8/18.
 */

public class JUnitTestMockTrack
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testCompareTo()
    {
        Album album1 = new Album("album1");
        mainActivity.getActivity().setCurrentUser(new User("", ""));
        MockTrack track1 = new MockTrack(new Track("Track1","Tony", album1, null), new User("",""));
        MockTrack track2 = new MockTrack(new Track("Track2","Tony", album1, null), new User("",""));

        track1.setStatus(Track.FavoriteStatus.LIKE);
        track2.setStatus(Track.FavoriteStatus.LIKE);
        LocalDateTime dateTime = LocalDateTime.of(2018, 3, 3,12, 5);
        MockTrackTime.useFixedClockAt(dateTime);

        // same time, different location, same day, same like
        MockLocation location = new MockLocation();
        dateTime = LocalDateTime.of(2018, 3, 3,10, 5);
        location.setLatitude(116.4074);
        location.setLongitude(39.9042);

        Location loc = new Location("");
        loc.setLatitude(116.4074);
        loc.setLongitude(39.9042);
        MainActivity.setmLocation(loc);


        track1.setLocation(location);
        track1.setDate(dateTime);


        MockLocation location1 = new MockLocation();
        location1.setLatitude(97.4074);
        location1.setLongitude(37.9042);
        track2.setLocation(location1);
        track2.setDate(dateTime);


        Assert.assertTrue(track1.compareTo(track2) < 0);

        // different time, same location, same day, same like

        LocalDateTime dateTime2 = LocalDateTime.of(2018, 3, 1,3, 5);
        location.setLatitude(113.4074);
        location.setLongitude(39.9042);

        track1.setLocation(location);
        track1.setDate(dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2018, 2, 3,7, 5);

        track2.setLocation(location);
        track2.setDate(dateTime3);

        Assert.assertTrue(track1.compareTo(track2) < 0);


        // same time, same location, different day in same range, same like
        dateTime2 = LocalDateTime.of(2018, 2, 27,3, 5);

        track1.setLocation(location);
        track1.setDate(dateTime2);

        dateTime3 = LocalDateTime.of(2018, 2, 28,3, 5);

        track2.setLocation(location);
        track2.setDate(dateTime3);

         Assert.assertTrue(track1.compareTo(track2) > 0);

        // same time, same location, same day, different like

        track1.setLocation(location);
        track1.setDate(dateTime);
        track1.setStatus(Track.FavoriteStatus.NEUTRAL);

        track2.setLocation(location);
        track2.setDate(dateTime);
        track2.setStatus(Track.FavoriteStatus.LIKE);



        Assert.assertTrue(track1.compareTo(track2) > 0);

        // score same status same
        dateTime2 = LocalDateTime.of(2018, 3, 3,6, 5);
        dateTime3 = LocalDateTime.of(2018, 3, 3,7, 5);


        track1.setLocation(location);
        track1.setDate(dateTime2);
        track1.setStatus(Track.FavoriteStatus.LIKE);

        track2.setLocation(location);
        track2.setDate(dateTime3);
        track2.setStatus(Track.FavoriteStatus.LIKE);

        Assert.assertTrue(track1.compareTo(track2) > 0);



    }
}
