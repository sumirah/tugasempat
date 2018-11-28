package id.go.lombokbaratkab.sumirah.tugasempat.main


import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import id.go.lombokbaratkab.sumirah.tugasempat.R

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.v7.widget.RecyclerView
import id.go.lombokbaratkab.sumirah.tugasempat.utils.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @JvmField
    @Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val recyclerView = onView(
                allOf(withId(R.id.rv_prev),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val actionMenuItemView = onView(
                allOf(withId(R.id.favorite_star), withContentDescription("Favorit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        val appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_fav), withContentDescription("Favorit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.btn_navigation),
                                        0),
                                2),
                        isDisplayed()))

        val bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_prev), withContentDescription("Last Match"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.btn_navigation),
                                        0),
                                0),
                        isDisplayed()))
        bottomNavigationItemView2.perform(click())

    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}
