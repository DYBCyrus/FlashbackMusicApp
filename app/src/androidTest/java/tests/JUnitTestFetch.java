package tests;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.DataBase;
import com.example.team9.flashbackmusic_team9.FetchAddressIntentService;
import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.PlayList;
import com.example.team9.flashbackmusic_team9.Player;
import com.example.team9.flashbackmusic_team9.PlayingActivity;
import com.example.team9.flashbackmusic_team9.Track;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chutong on 2/16/18.
 */

public class JUnitTestFetch
{
    private String mAddressOutput;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    ActivityTestRule<PlayingActivity> playingActivity;
    @Before
    public void setUp()
    {
        DataBase.loadFile(mainActivity.getActivity());
        PlayList pl = new PlayList(DataBase.getAllTracks(), false);
        Player.setPlayList(pl);
        Player.start(pl.next());
        playingActivity = new ActivityTestRule<>(PlayingActivity.class);
    }

    @Test
    public void testFetch()
    {

        AddressResultReceiver mResultReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(playingActivity.getActivity(), FetchAddressIntentService.class);
        intent.putExtra("receiver", mResultReceiver);
        if (Player.getCurrentTrack().getLocation() != null) {
            intent.putExtra("location", Player.getCurrentTrack().getLocation());
            playingActivity.getActivity().startService(intent);
        }
        System.out.println(mAddressOutput);
    }
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString("result");
        }

    }
}

