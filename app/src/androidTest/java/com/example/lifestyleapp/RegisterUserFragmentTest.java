package com.example.lifestyleapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.lang.Thread.*;

import androidx.fragment.app.testing.FragmentScenario;

import junit.framework.TestCase;

import org.junit.Test;

public class RegisterUserFragmentTest extends TestCase {

    FragmentScenario scenario;
    public User user = new User("Alejandro ", "Serrano", "28", "Male", "Guayaquil", "Ecuador", "110", "110");
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testLaunch() throws InterruptedException {
        scenario = FragmentScenario.launchInContainer(new RegisterUserFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        onView(withId(R.id.editTextFirstName)).perform(typeText(user.getFirstName()));
        Thread.sleep(2000);

        onView(withId(R.id.editTextFirstName)).check(matches(withText(user.getFirstName())));
    }
    public void tearDown() throws Exception {
    }
}