package com.example.lifestyleapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.hamcrest.Matchers.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = AndroidJUnit4.class)
public class bmiFragmentTest {

    FragmentScenario fragmentScenario;
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void testLaunch(){

        User testuser = new User("Alejandro ", "Serrano","28", "Male", "Guayaquil", "Ecuador", "110", "110");
        Bundle bundle = new Bundle();
        bundle.putParcelable("user_data", testuser);
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_bmiEditTextHeight)).perform(typeText("5"));
        onView(withId(R.id.fr_bmiEditTextHeight2)).perform(typeText("4"));
        onView(withId(R.id.fr_bmiEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches(not(withText(""))));
    }

    @Test
    public void testForNonDigitInputs(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_bmiEditTextHeight)).perform(typeText("a"));
        onView(withId(R.id.fr_bmiEditTextHeight2)).perform(typeText("b"));
        onView(withId(R.id.fr_bmiEditTextWeight)).perform(typeText("c"));
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText("0.0 "))));
    }

    @Test
    public void testForFeetNonDigitInput(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_bmiEditTextHeight)).perform(typeText("a"));
        onView(withId(R.id.fr_bmiEditTextHeight2)).perform(typeText("3"));
        onView(withId(R.id.fr_bmiEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText("0.0 "))));
    }
    @Test
    public void testForInchesNonDigitInput(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_bmiEditTextHeight)).perform(typeText("5"));
        onView(withId(R.id.fr_bmiEditTextHeight2)).perform(typeText("a"));
        onView(withId(R.id.fr_bmiEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText("0.0 "))));
    }

    @Test
    public void testForWeighNonDigitInput(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_bmiEditTextHeight)).perform(typeText("5"));
        onView(withId(R.id.fr_bmiEditTextHeight2)).perform(typeText("4"));
        onView(withId(R.id.fr_bmiEditTextWeight)).perform(typeText("b"));
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText("0.0 "))));
    }
    @Test
    public void testWithUserBundle(){
        User testuser = new User("Alejandro ", "Serrano", "28", "Male", "Guayaquil", "Ecuador", "160", "67");
        Bundle bundle = new Bundle();
        bundle.putParcelable("user_data", testuser);
        String bmi = "25.1";
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), bundle, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText(bmi+" "))));
    }
    @Test
    public void testWithUserBundleAndBadData(){
        User testuser = new User("Alejandro ", "Serrano", "28", "Male", "Guayaquil", "Ecuador", "b", "67");
        Bundle bundle = new Bundle();
        bundle.putParcelable("user_data", testuser);
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), bundle, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText("0.0 "))));
    }

    @Test
    public void testWithUserBundleAndBadData2(){
        User testuser = new User("Alejandro ", "Serrano", "28","Male", "Guayaquil", "Ecuador", "110", "b");
        Bundle bundle = new Bundle();
        bundle.putParcelable("user_data", testuser);
        fragmentScenario = FragmentScenario.launchInContainer(new bmiFragment().getClass(), bundle, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_buttonBMI)).perform(click());
        onView(withId(R.id.fr_bmiCalculation)).check(matches((withText("0.0 "))));
    }

    @After
    public void tearDown() throws Exception {
        fragmentScenario = null;
    }
}