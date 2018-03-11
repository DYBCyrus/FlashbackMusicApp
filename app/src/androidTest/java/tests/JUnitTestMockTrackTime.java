package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.MockTrackTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDateTime;

public class JUnitTestMockTrackTime {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testTrackTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        MockTrackTime.useFixedClockAt(localDateTime);
        Assert.assertEquals(localDateTime, MockTrackTime.now());

        LocalDateTime localDateTime1 = LocalDateTime.MAX;
        LocalDateTime localDateTime2 = LocalDateTime.MIN;
        Assert.assertNotEquals(localDateTime1, MockTrackTime.now());
        Assert.assertNotEquals(localDateTime2, MockTrackTime.now());
    }
}
