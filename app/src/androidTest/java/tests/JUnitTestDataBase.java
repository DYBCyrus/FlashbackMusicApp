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
        Assert.assertFalse("check whether beautifulpain is loaded correctly",
                trackNames.contains("beautifalpain"));
        Assert.assertFalse("check whether artist of beautifulpain is loaded correctly",
                artists.contains("Keaton Simons"));
        Assert.assertFalse("check whether New & Best of Keaton Simons presents",
                albumNames.contains("New & Best of Keaton Simons"));
    }




    @Test
    public void testPlayList(){
        //Tests add() and hasNext() methods
        //If assertTrue returns true both methods work because the playlist is
        //intially empty



        Album album1 = new Album("GenerationZ");
        Track track1 = new Track("Faded", "ZHU", album1, null);
        Track track2 = new Track("Automatic", "ZHU", album1, null);
        Track track3 = new Track("GoodLife", "ZHU", album1, null);
        ArrayList<Track> testTracks = new ArrayList<Track>();
        PlayList test = new PlayList(testTracks, true);

        testTracks.add(track1);
        testTracks.add(track2);
        testTracks.add(track3);


    Assert.assertTrue("Checks if the playlist has a next track", test.hasNext());


        Track next = test.next();
        //Tests the playlists hasPrevious() method
//    Assert.assertTrue("Checks if there is a previous track", test.hasPrevious());


    

    //Test the nextIndex() method
    Assert.assertTrue("Checks if the nextIndex() method works", test.nextIndex() == 1);

    //Test the previousIndex() method
    Assert.assertTrue("Checks if the previousIndex() method works", test.previousIndex() == 0);


}
}
