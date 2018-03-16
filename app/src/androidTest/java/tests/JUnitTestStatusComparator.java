package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.StatusComparator;
import com.example.team9.flashbackmusic_team9.Track;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chutong on 3/14/18.
 */

public class JUnitTestStatusComparator
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    StatusComparator staCom;

    @Before
    public void setUp()
    {
        staCom = new StatusComparator();
    }

    @Test
    public void TestCompare()
    {
        Album album = new Album("Album 1");
        Album album1 = new Album("Album 2");
        Track t1 = new Track("TonySong","Tony", album, "");
        Track t2 = new Track("kentSong","kent", album1, "");
        t1.setStatus(Track.FavoriteStatus.LIKE);
        t2.setStatus(Track.FavoriteStatus.LIKE);
        Assert.assertTrue(staCom.compare(t1, t2) < 0);

        t1.setStatus(Track.FavoriteStatus.LIKE);
        t2.setStatus(Track.FavoriteStatus.NEUTRAL);
        Assert.assertTrue(staCom.compare(t1, t2) < 0);

        t1.setStatus(Track.FavoriteStatus.NEUTRAL);
        t2.setStatus(Track.FavoriteStatus.DISLIKE);
        Assert.assertTrue(staCom.compare(t1, t2) < 0);

        t1.setStatus(Track.FavoriteStatus.DISLIKE);
        t2.setStatus(Track.FavoriteStatus.LIKE);
        Assert.assertTrue(staCom.compare(t1, t2) > 0);
    }
}
