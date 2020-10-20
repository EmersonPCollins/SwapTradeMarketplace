package com.example.myfirstapp;
import org.junit.Rule;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<GoodsActivity> activityActivityTestRule = new ActivityTestRule<GoodsActivity>(GoodsActivity.class);

    @Test
    public void testEmptyTitle() {
        onView(withId(R.id.titleText))
                .perform(clearText(),typeText(""));
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid title.")));
    }
    @Test
    public void testEmptyDescription() {
        onView(withId(R.id.descriptionText))
                .perform(click())
                .perform(typeText(""));
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a description.")));
    }

    @Test
    public void badTestDate() {
        onView(withId(R.id.dateText))
                .perform(click())
                .perform(typeText(""));
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid date.")));
        onView(withId(R.id.dateText))
                .perform(click())
                .perform(typeText("1/1/2020"));
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid date.")));
    }
    @Test
    public void testEmptyLocation() {
        onView(withId(R.id.locationText))
                .perform(click())
                .perform(typeText(""));
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a location.")));
    }
    @Test
    public void testValidDetails() {
        onView(withId(R.id.titleText))
                .perform(click())
                .perform(typeText("My Title"));
        onView(withId(R.id.descriptionText))
                .perform(click())
                .perform(typeText("My Description"));
        onView(withId(R.id.dateText))
                .perform(click())
                .perform(typeText("01/11/2020"));
        onView(withId(R.id.locationText))
                .perform(click())
                .perform(typeText("123 My Street Halifax NS"));
        onView(withId(R.id.submitGoodButton))
                .perform(click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("")));
    }
}
