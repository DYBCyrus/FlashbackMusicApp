package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.ArtistComparator;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.Track;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chutong on 3/14/18.
 */

public class JUnitTestArtistComparator
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    ArtistComparator artCom;

    @Before
    public void setUp()
    {
        artCom = new ArtistComparator();
    }

    @Test
    public void TestCompare()
    {
        Album album = new Album("Album 1");
        Track t1 = new Track("","Tony", album, "");
        Track t2 = new Track("","kent", album, "");
        Assert.assertTrue(artCom.compare(t1, t2) < 0);
    }
}
