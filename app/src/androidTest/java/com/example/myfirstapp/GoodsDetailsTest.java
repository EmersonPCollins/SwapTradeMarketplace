package com.example.myfirstapp;

import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.myfirstapp.Activity.GoodsActivity;

public class GoodsDetailsTest {

    @Rule
    public ActivityScenarioRule<GoodsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(GoodsActivity.class);

    @Test
    public void testEmptyTitle() {
        onView(withId(R.id.locationText))
                .perform(scrollTo(), click())
                .perform(typeText("123 My Street Halifax NS"), closeSoftKeyboard());
        onView(withId(R.id.dateText))
                .perform(scrollTo(), click())
                .perform(typeText("01/11/2020"), closeSoftKeyboard());
        onView(withId(R.id.descriptionText))
                .perform(scrollTo(), click(), closeSoftKeyboard())
                .perform(typeText("My Description"));

        onView(withId(R.id.titleText))
                .perform(scrollTo(), click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(scrollTo(), click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid title.")));
    }
    @Test
    public void testEmptyDescription() {
        onView(withId(R.id.titleText))
                .perform(scrollTo(), click())
                .perform(typeText("My Title"), closeSoftKeyboard());
        onView(withId(R.id.locationText))
                .perform(scrollTo(), click())
                .perform(typeText("123 My Street Halifax NS"), closeSoftKeyboard());
        onView(withId(R.id.dateText))
                .perform(scrollTo(), click())
                .perform(typeText("01/11/2020"), closeSoftKeyboard());

        onView(withId(R.id.descriptionText))
                .perform(scrollTo(), click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(scrollTo(), click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a description.")));
    }

    @Test
    public void badTestDate() {
        onView(withId(R.id.titleText))
                .perform(scrollTo(), click())
                .perform(typeText("My Title"), closeSoftKeyboard());
        onView(withId(R.id.locationText))
                .perform(scrollTo(), click())
                .perform(typeText("123 My Street Halifax NS"), closeSoftKeyboard());
        onView(withId(R.id.descriptionText))
                .perform(scrollTo(), click(), closeSoftKeyboard())
                .perform(typeText("My Description"), closeSoftKeyboard());

        onView(withId(R.id.dateText))
                .perform(scrollTo(), click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(scrollTo(), click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid date.")));

        onView(withId(R.id.dateText))
                .perform(scrollTo(), click())
                .perform(typeText("1/11/2020"), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(scrollTo(), click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a valid date.")));

    }
    @Test
    public void testEmptyLocation() {
        onView(withId(R.id.titleText))
                .perform(scrollTo(), click())
                .perform(typeText("My Title"), closeSoftKeyboard());
        onView(withId(R.id.dateText))
                .perform(scrollTo(), click())
                .perform(typeText("01/11/2020"), closeSoftKeyboard());
        onView(withId(R.id.descriptionText))
                .perform(scrollTo(), click(), closeSoftKeyboard())
                .perform(typeText("My Description"), closeSoftKeyboard());

        onView(withId(R.id.locationText))
                .perform(scrollTo(), click())
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(scrollTo(), click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("Error: Enter a location.")));
    }
    @Test
    public void testValidDetails() {
        onView(withId(R.id.titleText))
                .perform(scrollTo(), click())
                .perform(typeText("My Title"), closeSoftKeyboard());
        onView(withId(R.id.locationText))
                .perform(scrollTo(), click())
                .perform(typeText("123 My Street Halifax NS"), closeSoftKeyboard());
        onView(withId(R.id.dateText))
                .perform(scrollTo(), click())
                .perform(typeText("01/11/2020"), closeSoftKeyboard());
        onView(withId(R.id.descriptionText))
                .perform(scrollTo(), click(), closeSoftKeyboard())
                .perform(typeText("My Description"), closeSoftKeyboard());
        onView(withId(R.id.submitGoodButton))
                .perform(scrollTo(), click());
        onView(withId(R.id.errorMessageTextView))
                .check(matches(withText("")));
    }
}