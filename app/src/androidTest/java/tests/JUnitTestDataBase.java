package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.DataBase;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.PlayList;
import com.example.team9.flashbackmusic_team9.PlayerToolBar;
import com.example.team9.flashbackmusic_team9.Track;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Chutong on 2/12/18.
 */

public class JUnitTestDataBase
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp()
    {
        DataBase.loadFile(mainActivity.getActivity());
    }

    @Test
    public void testLoadFile()
    {
        ArrayList<Track> allTracks = DataBase.getAllTracks();
        ArrayList<Album> allAlbums = DataBase.getAllAlbums();

        ArrayList<String> trackNames = new ArrayList<String>(allTracks.size());
        ArrayList<String> artists = new ArrayList<String>(allTracks.size());
        for(  int i = 0; i < allTracks.size(); i++  )
        {
            trackNames.add(allTracks.get(i).getName());
            artists.add(allTracks.get(i).getName());
        }

        ArrayList<String> albumNames = new ArrayList<String>(allAlbums.size());
        for(  int i = 0; i < allTracks.size(); i++  )
        {
            albumNames.add(allTracks.get(i).getName());
        }

        // success
        Assert.assertFalse("check whether beautifulpain is loaded correctly",
                trackNames.contains("beautifalpain"));
        Assert.assertFalse("check whether artist of beautifulpain is loaded correctly",
                artists.contains("Keaton Simons"));
        Assert.assertFalse("check whether New & Best of Keaton Simons presents",
                albumNames.contains("New & Best of Keaton Simons"));
        //fail
        Assert.assertFalse("check whether space presents",
                !albumNames.contains(""));
    }
}
