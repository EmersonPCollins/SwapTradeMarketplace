package com.example.myfirstapp;
import android.app.Activity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

public class GoodsDetailsTest {

    @Rule
    public ActivityScenarioRule<GoodsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(GoodsActivity.class);

    @Test
    public void testEmptyTitle() {
        onView(withId(R.id.titleText))
                .perform(click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid title.")));
    }
    @Test
    public void testEmptyDescription() {
        onView(withId(R.id.descriptionText))
                .perform(click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a description.")));
    }

    @Test
    public void badTestDate() {
        onView(withId(R.id.dateText))
                .perform(click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid date.")));
        onView(withId(R.id.dateText))
                .perform(click())
                .perform(typeText("1/1/2020"), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid date.")));
    }
    @Test
    public void testEmptyLocation() {
        onView(withId(R.id.locationText))
                .perform(click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a location.")));
    }
    @Test
    public void testValidDetails() {
        onView(withId(R.id.titleText))
                .perform(click())
                .perform(typeText("My Title"), closeSoftKeyboard());
        onView(withId(R.id.locationText))
                .perform(click())
                .perform(typeText("123 My Street Halifax NS"), closeSoftKeyboard());
        onView(withId(R.id.dateText))
                .perform(click())
                .perform(typeText("01/11/2020"), closeSoftKeyboard());
        onView(withId(R.id.descriptionText))
                .perform(click(), closeSoftKeyboard())
                .perform(typeText("My Description"), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("")));
    }
}