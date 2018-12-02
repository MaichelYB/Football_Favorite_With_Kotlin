package com.example.maichel.submission2.main.Main


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.maichel.submission2.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        /*Thread.sleep(5000)
        onView(withId(R.id.spinner))
            .check(matches(isDisplayed()))
        onView(withId(R.id.spinner))
            .check(matches(isDisplayed())).perform(click())

        Thread.sleep(5000)*/

        Thread.sleep(5000)
        onView(withId(R.id.list_next_match))
            .check(matches(isDisplayed()))
        onView(withId(R.id.list_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.list_next_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )
        Thread.sleep(5000)

        onView(withId(R.id.add_to_favorite))
            .check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(R.id.add_to_favorite)).perform(click())

        onView(withText("Added to Favorite"))
            .check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.teams))
            .check(matches(isDisplayed()))
        onView(withId(R.id.teams)).perform(click())
        Thread.sleep(5000)

        onView(withId(R.id.list_team))
            .check(matches(isDisplayed()))
        onView(withId(R.id.list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.list_team)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )
        Thread.sleep(5000)

        onView(withId(R.id.add_to_favorite))
            .check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()

        onView(withId(R.id.favorites))
            .check(matches(isDisplayed()))
        onView(withId(R.id.favorites)).perform(click())
        Thread.sleep(5000)

        onView(withId(R.id.list_match_favorite))
            .check(matches(isDisplayed()))
        onView(withId(R.id.list_match_favorite)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.list_match_favorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
    }
}
