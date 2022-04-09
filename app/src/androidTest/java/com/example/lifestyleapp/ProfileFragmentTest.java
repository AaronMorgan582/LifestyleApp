package com.example.lifestyleapp;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;

import com.example.lifestyleapp.Misc.ProfileFragment;

import junit.framework.TestCase;

import org.junit.Test;

public class ProfileFragmentTest extends TestCase {

    FragmentScenario scenario;
    public User user;
    public void setUp() throws Exception {
        super.setUp();
    }
    @Test
    public void testLaunch() throws InterruptedException {

        scenario = FragmentScenario.launchInContainer(new ProfileFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        //onView(withId(R.id.editTextFirstName)).perform(typeText(user.getFirstName()));
        //Thread.sleep(2000);

        //onView(withId(R.id.editTextFirstName)).check(matches(withText(user.getFirstName())));
    }
    public void tearDown() throws Exception {
    }
}