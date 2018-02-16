package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.TrackTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDateTime;

public class JUnitTestTrackTime {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testTrackTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        TrackTime.useFixedClockAt(localDateTime);
        Assert.assertEquals(localDateTime, TrackTime.now());

        LocalDateTime localDateTime1 = LocalDateTime.MAX;
        LocalDateTime localDateTime2 = LocalDateTime.MIN;
        Assert.assertNotEquals(localDateTime1,TrackTime.now());
        Assert.assertNotEquals(localDateTime2,TrackTime.now());
    }
}
