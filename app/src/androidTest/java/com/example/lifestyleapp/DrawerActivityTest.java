package com.example.lifestyleapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static com.example.lifestyleapp.EspressoTestMatchers.hasDrawable;
import static com.example.lifestyleapp.EspressoTestMatchers.noDrawable;
import static com.example.lifestyleapp.EspressoTestMatchers.withDrawable;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
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

        // Input the data into the fields and checks that
        // the input is correct
        onView(withId(R.id.editTextFirstName)).perform(typeText(user.getFirstName()));
        Thread.sleep(2000);
        onView(withId(R.id.editTextFirstName)).check(matches(withText(user.getFirstName())));
        onView(withId(R.id.editTextLastName)).perform(typeText(user.getLastName()));
        Thread.sleep(2000);
        onView(withId(R.id.editTextLastName)).check(matches(withText(user.getLastName())));

        onView(withId(R.id.editTextHeight)).perform(typeText(user.getHeight()));
        onView(withId(R.id.editTextHeight)).check(matches(withText(user.getHeight())));

        onView(withId(R.id.editTextWeight)).perform(typeText(user.getWeight()));
        onView(withId(R.id.editTextWeight)).check(matches(withText(user.getWeight())));

        onView(withId(R.id.maleRadioButton)).perform((click()));
        onView(withId(R.id.maleRadioButton)).check(matches(isChecked()));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editTextCity)).perform((typeText(user.getCity())));
        onView(withId(R.id.editTextCity)).check(matches(withText(user.getCity())));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editTextCountry)).perform((typeText(user.getCountry())));
        onView(withId(R.id.editTextCountry)).check(matches(withText(user.getCountry())));

        Espresso.closeSoftKeyboard();

        // Check that image transfers to EditProfile fragment view
        onView(withId(R.id.addPictureButton)).perform(click());

        onView(withId(R.id.button)).perform(click());

        // Check that the information transfers to the Edit profile view

        onView(withId(R.id.tvDisplayFirstName)).check(matches(withText(user.getFirstName())));
        onView(withId(R.id.tvDisplayLastName)).check(matches(withText(user.getLastName())));
        onView(withId(R.id.tvDisplayHeight)).check(matches(withText(user.getHeight())));
        onView(withId(R.id.tvDisplayWeight)).check(matches(withText(user.getWeight())));
        onView(withId(R.id.tvDisplayCity)).check(matches(withText(user.getCity())));
        onView(withId(R.id.tvDisplayCountry)).check(matches(withText(user.getCountry())));
        onView(withId(R.id.tvDisplaySex)).check(matches(withText(user.getGender())));

        onView(withId(R.id.profilePicture)).check(matches(hasDrawable()));
    }

    /**
     * Checks if after wrong input the view handles the mistake before
     * moving on.
     * @throws InterruptedException
     */
    @Test
    public void checkForWrongInput() throws InterruptedException {

        scenario = ActivityScenario.launch(DrawerActivity.class);

        onView(withId(R.id.editTextFirstName)).perform(typeText("Alejandro"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Serrano"));


        onView(withId(R.id.editTextHeight)).perform(typeText("a"));

        onView(withId(R.id.maleRadioButton)).perform((click()));
        onView(withId(R.id.maleRadioButton)).check(matches(isChecked()));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editTextCity)).perform((typeText("Some City")));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editTextCountry))
                .perform((typeText("Country")));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());

        // Check that the correct view is being displayed
        onView(withId(R.id.registerUserFragment))
                .check(matches(isDisplayed()));
    }

    @Test
    public void editViewTestWhenCorrectingMistake(){

        scenario = ActivityScenario.launch(DrawerActivity.class);

        onView(withId(R.id.editTextFirstName)).perform(typeText("Alejandro"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Serrano"));


        onView(withId(R.id.editTextHeight)).perform(typeText("a"));

        onView(withId(R.id.maleRadioButton)).perform((click()));
        onView(withId(R.id.maleRadioButton)).check(matches(isChecked()));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editTextCity))
                .perform((typeText("Some City")));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editTextCountry))
                .perform((typeText("Country")));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.registerUserFragment))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextHeight)).perform(clearText());
        onView(withId(R.id.editTextHeight)).perform(typeText("67"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.editProfileView))
                .check(matches(isDisplayed()));

    }


    @After
    public void tearDown() throws Exception {
        scenario = null;
    }
}