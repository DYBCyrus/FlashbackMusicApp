package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.AlbumComparator;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.Track;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chutong on 3/14/18.
 */

public class JUnitTestAlbumComparator
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    AlbumComparator albCom;

    @Before
    public void setUp()
    {
        albCom = new AlbumComparator();
    }

    @Test
    public void TestCompare()
    {
        Album album = new Album("Album 1");
        Album album1 = new Album("Album 2");
        Track t1 = new Track("","Tony", album, "");
        Track t2 = new Track("","kent", album1, "");
        Assert.assertTrue(albCom.compare(t1, t2) < 0);

        t1 = new Track("TonySong","Tony", album, "");
        t2 = new Track("kentSong","kent", album, "");
        Assert.assertTrue(albCom.compare(t1, t2) < 0);
    }
}
