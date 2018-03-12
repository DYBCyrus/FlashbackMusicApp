package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.MockTrack;
import com.example.team9.flashbackmusic_team9.MockTrackTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class JUnitTestMockTrackTime {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testTrackTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC-08:00")).truncatedTo(ChronoUnit.SECONDS);
        MockTrackTime.useFixedClockAt(localDateTime);
        System.out.println(localDateTime);
        System.out.println(MockTrackTime.now());
        Assert.assertEquals(localDateTime, MockTrackTime.now());

        LocalDateTime localDateTime1 = LocalDateTime.MAX.truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime localDateTime2 = LocalDateTime.MIN.truncatedTo(ChronoUnit.SECONDS);
        Assert.assertNotEquals(localDateTime1, MockTrackTime.now());
        Assert.assertNotEquals(localDateTime2, MockTrackTime.now());
    }
}
