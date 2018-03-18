package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.team9.flashbackmusic_team9.MainActivity;
import com.example.team9.flashbackmusic_team9.User;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Chutong on 3/14/18.
 */

public class JUnitTestUser
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void TestAddFriend()
    {
        User user = new User("ken@ucsd.edu","Kent");
        user.addFriend("chy257@ucsd,edu","Tony");
        Assert.assertTrue(user.getFriends().containsValue("Tony"));
    }

}
