package tests;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.DataBase;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.PlayList;
import com.example.team9.flashbackmusic_team9.Player;
import com.example.team9.flashbackmusic_team9.Track;
import com.example.team9.flashbackmusic_team9.Updateables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Chutong on 2/16/18.
 */

public class JUnitTestPlayer
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    PlayList pl;

    @Before
    public void setUp()
    {
        DataBase.loadFile(mainActivity.getActivity());
        pl = new PlayList(DataBase.getAllTracks(), false);
        Player.setPlayList(pl);
    }

    @Test
    public void testStart()
    {
        Track temp = pl.next();
        Player.start(temp);
        Assert.assertSame(Player.getCurrentTrack(), temp);
    }

    @Test
    public void testPlayPrevious()
    {
        Assert.assertEquals(false, Player.playPrevious());
        Player.playNext();
        Player.playNext();
        Assert.assertEquals(true, Player.playPrevious());
    }

    @Test
    public void testPlayNext()
    {
        Assert.assertEquals(Player.playNext(), true);
        pl.next();
        while(pl.hasNext())
        {
            pl.next();
            Player.playNext();
        }
        Assert.assertEquals(Player.playNext(), false);
    }

    @Test
    public void testPlayPlayList()
    {
        pl = new PlayList(new ArrayList<Track>(), false);
        Player.playPlayList(pl);
        Assert.assertSame(Player.getPlayList(), pl);
    }

    @Test
    public void testClearPlayList()
    {
        Player.clearPlayList();
        Assert.assertSame(Player.getPlayList(),null);
    }


}
