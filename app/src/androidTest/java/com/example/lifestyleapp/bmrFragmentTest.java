package com.example.lifestyleapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = AndroidJUnit4.class)
public class bmrFragmentTest {
    FragmentScenario fragmentScenario;

    public void setUp() throws Exception {

    }

    @Test
    public void testLaunch(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Enter text to UI input
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        // Checking radios
        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioActive)).check(matches(isChecked()));
        onView(withId(R.id.fr_radioSedentary)).check(matches(not(isChecked())));
        onView(withId(R.id.fr_radioSedentary)).perform((click()));
        onView(withId(R.id.fr_radioSedentary)).check(matches((isChecked())));
        onView(withId(R.id.fr_radioActive)).check(matches(not(isChecked())));
        onView(withId(R.id.fr_radioMale)).perform((click()));
        onView(withId(R.id.fr_radioMale)).check(matches((isChecked())));
        onView(withId(R.id.fr_radioFemale)).check(matches(not(isChecked())));
        onView(withId(R.id.fr_buttonBMR)).perform(click());

        // Check that with proper inputs the textview is not empty.
        // Does not check if the output is actually correct
        onView(withId(R.id.fr_tvBmrCalculation)).check(matches(not(withText(""))));

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Maintain"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Maintain"))));

        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Gain"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Gain"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches(not(withText(""))));

    }
    @Test
    public void testWithEmptyInput(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));
    }

    @Test
    public void testNoRadiosChecked(){
        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));

        // Should check if the toast shows up, but don't know how.

    }

    @Test
    public void testWrongInput(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Enter text to UI input
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("a"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));

    }

    @Test
    public void testWrongInput2(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Enter text to UI input
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("b"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));

    }

    @Test
    public void testWrongInput3(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Enter text to UI input
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("c"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));

    }

    @Test
    public void testWrongInput4(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Enter text to UI input
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("2c"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));

    }
    @Test
    public void testWrongInputForBMRAndCalories(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Enter text to UI input
        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("a"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        onView(withId(R.id.fr_buttonBMR)).perform(click());

        onView(withId(R.id.fr_tvBmrCalculation)).check(matches((withText("0 daily calories."))));

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches((withText(""))));

    }

    @Test
    public void calculateCaloriesWithoutCalories(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches((withText(""))));

    }
    @Test
    public void calculateCaloriesWithCaloriesActiveFemale(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches(not(withText(""))));

    }

    @Test
    public void calculateCaloriesWithCaloriesSedentaryFemale(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioSedentary)).perform((click()));
        onView(withId(R.id.fr_radioFemale)).perform((click()));

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches(not(withText(""))));

    }

    @Test
    public void calculateCaloriesWithCaloriesActiveMale(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioActive)).perform((click()));
        onView(withId(R.id.fr_radioMale)).perform((click()));

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches(not(withText(""))));

    }

    @Test
    public void calculateCaloriesWithCaloriesSedentaryMale(){

        fragmentScenario = FragmentScenario.launchInContainer(new bmrFragment().getClass(), null, R.style.Base_Theme_AppCompat);

        onView(withId(R.id.fr_goalsEditTextHeight)).perform(typeText("67"));
        onView(withId(R.id.fr_goalsEditTextWeight)).perform(typeText("110"));
        onView(withId(R.id.fr_goalsEditTextAge)).perform(typeText("27"));

        onView(withId(R.id.fr_radioSedentary)).perform((click()));
        onView(withId(R.id.fr_radioMale)).perform((click()));

        // Check for spinner selection
        onView(withId(R.id.fr_goalSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Lose"))).perform(click());
        onView(withId(R.id.fr_goalSpinner)).check(matches(withSpinnerText(containsString("Lose"))));

        // Check that amount of calories is not empty
        onView(withId(R.id.fr_goalsEditTextPounds)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fr_buttonCalorieEstimate)).perform(click());

        onView(withId(R.id.fr_tvCalories)).check(matches(not(withText(""))));


    }




}