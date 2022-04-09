package com.example.lifestyleapp;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.lifestyleapp.Version1Activities.BMIActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BMIActivityUITest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule(BMIActivity.class);
    private String feet = "5";
    private String inches = "10";
    private String weight = "110";
    @Before
    public void setUp() throws Exception {
    }
    public double calculateBMI(){
        double f = Double.parseDouble(feet);
        double i = Double.parseDouble(inches);
        double w = Double.parseDouble(weight);
        double totalInches = (f*12) + i;
        double totalBMI = (w/(Math.pow(totalInches, 2)))*703;
        return totalBMI;
    }
    @Test
    public void testUserScenario(){
        double expectedResult = calculateBMI();
        Espresso.onView(withId(R.id.bmiEditTextHeight)).perform(typeText(feet));
        Espresso.onView(withId(R.id.bmiEditTextHeight2)).perform(typeText(inches));
        Espresso.onView(withId(R.id.bmiEditTextWeight)).perform(typeText(weight));
        Espresso.onView(withId(R.id.buttonBMI)).perform(click());
        String gotResult = Espresso.onView(withId(R.id.bmiCalculation)).toString();


    }
    @After
    public void tearDown() throws Exception {
    }
}