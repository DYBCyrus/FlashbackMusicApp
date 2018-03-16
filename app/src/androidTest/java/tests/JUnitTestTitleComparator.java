package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.TitleComparator;
import com.example.team9.flashbackmusic_team9.Track;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chutong on 3/14/18.
 */

public class JUnitTestTitleComparator
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    TitleComparator titCom;

    @Before
    public void setUp()
    {
        titCom = new TitleComparator();
    }

    @Test
    public void TestCompare()
    {
        Album album = new Album("Album 1");
        Album album1 = new Album("Album 2");
        Track t1 = new Track("TonySong","Tony", album, "");
        Track t2 = new Track("kentSong","kent", album1, "");
        Assert.assertTrue(titCom.compare(t1, t2) < 0);
    }
}
