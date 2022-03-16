package com.example.lifestyleapp;

import android.view.View;

import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Matcher;
/**
 * Matcher utility class for checking consistency with images. These
 * are custom Espresso matchers for checking if an image exists after
 * the Camera intent. For more reference, check the DrawableMatcher class
 * or the website provided bellow.
 * Credit: Daniele Bottillo
 * https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f
 */
public class EspressoTestMatchers {

    public static TypeSafeMatcher<View>  withDrawable(final int resourceId) {

        return new DrawableMatcher(resourceId);
    }

    public static TypeSafeMatcher noDrawable() {
        return new DrawableMatcher(DrawableMatcher.EMPTY);
    }

    public static TypeSafeMatcher<View> hasDrawable() {

        return new DrawableMatcher(DrawableMatcher.ANY);
    }
}
