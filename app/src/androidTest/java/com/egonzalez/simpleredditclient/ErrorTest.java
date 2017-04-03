package com.egonzalez.simpleredditclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.egonzalez.simpleredditclient.base.EspressoTest;
import com.egonzalez.simpleredditclient.view.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ErrorTest extends EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity>
        mActivityRule = new ActivityTestRule(MainActivity.class, false, false);

    @Before
    public void setUp() {
        mEspressoMockResponses.add(Uri.parse("error.json"));

        super.setUp();
    }

    @Test
    public void sampleTopListTest() throws Exception {

        final Activity activity =
            mActivityRule.launchActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class));

        checkDisplayed(R.id.fragment_error_button);
    }
}
