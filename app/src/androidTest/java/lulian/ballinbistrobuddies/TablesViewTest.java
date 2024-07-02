package lulian.ballinbistrobuddies;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;

import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import lulian.ballinbistrobuddies.view.TablesView;
import lulian.ballinbistrobuddies.view.StartView;

/**
 * Defines a set of Espresso UI tests for the TablesView activity in the Ballin' Bistro Buddies app.
 * This class includes tests to verify the functionality and UI elements of the TablesView, such as
 * selecting online users, choosing a quadrant, and verifying the display of quadrants and tables.
 * Each test method within this class is annotated with {@code @Test} to indicate that it is a test case.
 * The {@code ActivityScenarioRule} is used to launch the activity under test before each test method.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class TablesViewTest {

    @Rule
    public ActivityScenarioRule<TablesView> activityScenarioRule =
            new ActivityScenarioRule<>(TablesView.class);

    /**
     * Test if user can press on the user_view button and see the online users.
     */
    @Test
    public void testSelectOnlineUser() {
        onView(withId(R.id.toolbar_title)).check(matches(withText("Quadrants")));
        // Click on openDrawerButton to open the drawer
        onView(withId(R.id.ButtonOpenDrawer)).perform(click());
        // Click on the user_view item in the drawer
        onView(withId(R.id.user_view)).perform(click());
        // Check if the toolbar title is "Online User"
        onView(withId(R.id.toolbar_title)).check(matches(withText("Online User")));
    }

    /**
     * Test if user can press on one quadrant and see the tables.
     */
    @Test
    public void testSelectQuadrant() {
        onView(withId(R.id.toolbar_title)).check(matches(withText("Quadrants")));
        // Click on one of the quadrants
        onView(withId(R.id.button1)).perform(click());
        // Check if the toolbar title is "Tables"
        onView(withId(R.id.toolbar_title)).check(matches(withText("Tables")));
    }

    /**
     * Verifies the display of quadrants in the UI.
     * This test checks if the "Quadrants" title is correctly displayed in the toolbar
     * and then verifies that all 8 quadrant buttons are displayed on the screen.
     */
    @Test
    public void testQuadrants() {
        onView(withId(R.id.toolbar_title)).check(matches(withText("Quadrants")));
        // Check if 8 quadrants are shown
        onView(withId(R.id.button1)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(isDisplayed()));
        onView(withId(R.id.button3)).check(matches(isDisplayed()));
        onView(withId(R.id.button4)).check(matches(isDisplayed()));
        onView(withId(R.id.button5)).check(matches(isDisplayed()));
        onView(withId(R.id.button6)).check(matches(isDisplayed()));
        onView(withId(R.id.button7)).check(matches(isDisplayed()));
        onView(withId(R.id.button8)).check(matches(isDisplayed()));
    }

    /**
     * Placeholder for a test that verifies the functionality of sitting down at a table.
     * Currently, this test is not implemented and returns immediately.
     */
    @Test
    public void testSitDown(){
        return;
    }

    /**
     * Placeholder for a test that verifies the functionality of standing up from a table.
     * Currently, this test is not implemented and returns immediately.
     */
    @Test
    public void testStandUp(){
        return;
    }
}
