package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.Track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class JUnitTestAlbum {

    private Album album;
    private Track track;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setUp()
    {
        album = new Album("albumName");
        track = new Track("name","artist",album,null);
    }

    @Test
    public void testGetName()
    {
        Assert.assertEquals("albumName",album.getName());
        Assert.assertNotEquals("ABC",album.getName());
    }

    @Test
    public void testAddTrack()
    {
        album.addTrack(track);
        Assert.assertTrue(album.getTracks().contains(track));
        Assert.assertFalse(!album.getTracks().contains(track));
    }

    @Test
    public void testGetTrack()
    {
        Assert.assertEquals(new ArrayList<Track>(),album.getTracks());
        album.addTrack(track);
        Assert.assertEquals(track,album.getTracks().get(0));
    }
}
