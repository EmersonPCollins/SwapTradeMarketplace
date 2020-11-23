package com.example.myfirstapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.myfirstapp.Activity.SearchActivity;
import com.example.myfirstapp.domain.Good;

import java.util.ArrayList;

public class DisplayGoodsTest {
    public static final String title = "My Title";
    public static final String startDate = "19/11/2020";
    public static final String endDate = "19/12/2020";
    public static final String description = "My Description";
    public static final String exchange_location = "123 My Street Halifax NS";
    public static final String user_email = "test@email.com";
    public static final String image_url = "images/JPEG_20201119_100505.jpeg";
    public static final String type = "Art";
    Good good;
    ArrayList<Good> goods;

    @Rule
    public ActivityScenarioRule<SearchActivity> activityScenarioRule
            = new ActivityScenarioRule<>(SearchActivity.class);

    @Before
    public void setup() throws Exception {
        goods = new ArrayList<Good>();
        good = new Good(title, startDate, endDate, description, exchange_location, user_email, image_url, type);
        goods.add(good);
    }
    @Test
    public void displayGoods() {
        SearchActivity.displayGoods(goods);
        onView(withId(R.id.goodName))
                .check(matches(withText(title)));
    }


}
