package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.example.team9.flashbackmusic_team9.Album;
import com.example.team9.flashbackmusic_team9.DataBase;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.PlayList;
import com.example.team9.flashbackmusic_team9.PlayerToolBar;
import com.example.team9.flashbackmusic_team9.Track;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by pranavseshadri on 2/16/18.
 */

public class JUnitTestPlaylist {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);




    Album album1 = new Album("GenerationZ");
    Track track1 = new Track("Faded", "ZHU", album1, null);
    Track track2 = new Track("Automatic", "ZHU", album1, null);
    Track track3 = new Track("GoodLife", "ZHU", album1, null);
    ArrayList<Track> testTracks = new ArrayList<Track>();
    PlayList test ;

    @Before
    public void setUp(){
        testTracks.add(track1);
        testTracks.add(track2);
        test = new PlayList(testTracks, true);
        Track first = test.next();


    }


    @Test
    public void testHasNext() {
        Assert.assertTrue("Checks if the playlist has a next track", test.hasNext());



    }

    @Test
    public void testNext() {
        Track next = test.next();
        Assert.assertEquals(track2, next);

    }


    @Test
    public void testHasPrevious()
    {

        Track next = test.next();
        //Tests the playlists hasPrevious() method
        Assert.assertTrue("Checks if there is a previous track", test.hasPrevious());

    }



    @Test
     public  void testPrevious() {



        test.next();
        Track previous = test.previous();
        Assert.assertEquals(track1, previous);

    }


    @Test
    public void testNextIndex() {

        //Test the nextIndex() method
        Assert.assertTrue("Checks if the nextIndex() method works", test.nextIndex() == 1);


    }


    @Test
     public void testPreviousIndex() {


        //Test the previousIndex() method
        Assert.assertFalse("Checks if the previousIndex() method works", test.previousIndex() == 0);



    }


    @Test
    public void testRemove(){

        test.remove();
        Assert.assertTrue("Should not have any tracks next because playlist is cleared",testTracks.isEmpty());



    }
}
