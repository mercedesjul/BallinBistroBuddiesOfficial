package lulian.ballinbistrobuddies;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lulian.ballinbistrobuddies.view.StartView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;

/**
 * test class for testing the StartView functionality.
 */
@RunWith(AndroidJUnit4.class)
public class StartViewTest {

    @Rule
    public ActivityScenarioRule<StartView> activityScenarioRule =
            new ActivityScenarioRule<>(StartView.class);

    @Before
    public void clearPreferences() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().clear().apply();
    }

    /**
     * Tests that StartView is displayed correctly when the app is launched.
     */
    @Test
    public void testStartViewDisplayed() {
        onView(withId(R.id.main)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    /**
     * Tests the login functionality of StartView when a valid name is entered.
     */
    @Test
    public void testLoginButtonWithValidName() {
        // Enter a valid name and click login button
        onView(withId(R.id.editDisplayName)).perform(replaceText("John Doe"));
        onView(withId(R.id.login_button)).perform(click());

        // Verify that the TablesView is launched
        onView(withId(R.id.gridLayoutQuadrants)).check(matches(isDisplayed()));
    }

    /**
     * Tests the login functionality of StartView when no name is entered.
     */
    @Test
    public void testLoginButtonWithEmptyName() {
        // Leave name field empty and click login button
        onView(withId(R.id.login_button)).perform(click());

        // Verify that an error message is displayed
        onView(withId(R.id.editDisplayName)).check(matches(hasErrorText("Name is required")));
    }

    // Layout Tests:

    /**
     * Test case to verify if the EditText for display name input is displayed on the StartView.
     * It uses Espresso to check if the EditText view is visible.
     */
    @Test
    public void testEditTextDisplayed() {
        onView(withId(R.id.editDisplayName)).check(matches(isDisplayed()));
    }

    /**
     * Test case to verify if the ImageView logo is displayed on the StartView.
     * It uses Espresso to check if the ImageView is visible.
     */
    @Test
    public void testImageViewDisplayed() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
    }

    /**
     * Test case to verify if the Login button is displayed on the StartView.
     * It uses Espresso to check if the Button is visible.
     */
    @Test
    public void testLoginButtonDisplayed() {
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }
}

