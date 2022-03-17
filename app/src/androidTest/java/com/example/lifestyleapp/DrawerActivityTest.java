package com.example.lifestyleapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static com.example.lifestyleapp.EspressoTestMatchers.hasDrawable;
import static com.example.lifestyleapp.EspressoTestMatchers.noDrawable;
import static com.example.lifestyleapp.EspressoTestMatchers.withDrawable;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import static java.util.regex.Pattern.matches;

import android.view.Gravity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.hamcrest.Matcher;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = AndroidJUnit4.class)
public class DrawerActivityTest {
    ActivityScenario scenario;
    User user = new User("Alejandro ", "Serrano", "Male", "Guayaquil", "Ecuador", "110", "110");

    /**
     * Checks overall navigation from the Drawer Layout.
     * @throws InterruptedException
     */
    @Test
    public void navigateToAllFragments() throws InterruptedException {
        scenario = ActivityScenario.launch(DrawerActivity.class);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        Thread.sleep(2000);
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_bmi));

        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        Thread.sleep(2000);
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_calories));
        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        Thread.sleep(2000);
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home));
        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        Thread.sleep(2000);
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile));
        Thread.sleep(2000);
        onView(withId(R.id.buttonEditProfile)).perform((click()));
        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        Thread.sleep(2000);
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home));

    }

    /**
     * Tests if the picture taken is saved on the nav header
     * ImageView. Requires the image to be taken manually
     * @throws InterruptedException
     */
    @Test
    public void checkForImageInsideDrawerHeader() throws InterruptedException {
        scenario = ActivityScenario.launch(DrawerActivity.class);
        Thread.sleep(2000);
        onView(withId(R.id.profilePicView)).check(matches(noDrawable()));
        onView(withId(R.id.addPictureButton)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navHeaderProfPic)).check(matches(hasDrawable()));
    }
    @Test
    public void checkForProfileInputs() throws InterruptedException {
        scenario = ActivityScenario.launch(DrawerActivity.class);
        onView(withId(R.id.editTextFirstName)).perform(typeText(user.getFirstName()));
        Thread.sleep(2000);
        onView(withId(R.id.editTextFirstName)).check(matches(withText(user.getFirstName())));
        onView(withId(R.id.editTextLastName)).perform(typeText(user.getLastName()));
        Thread.sleep(2000);
        onView(withId(R.id.editTextLastName)).check(matches(withText(user.getLastName())));

    }


    @After
    public void tearDown() throws Exception {
        scenario = null;
    }
}