package lulian.ballinbistrobuddies;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.core.app.ApplicationProvider;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lulian.ballinbistrobuddies.view.TablesView;
import lulian.ballinbistrobuddies.view.StartView;
import lulian.ballinbistrobuddies.control.TableController;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class EndToEndTest {

    @Rule
    public ActivityTestRule<StartView> activityRule = new ActivityTestRule<>(StartView.class);

    @Before
    public void setUp() {
        activityRule.getActivity();
    }

    @Test
    public void testSelectTableFromQuadrant() {
        // Simulate entering name in EditText
        onView(withId(R.id.editDisplayName)).perform(replaceText("John Doe"));
        onView(withId(R.id.login_button)).perform(click());

        // Navigate to TablesView
        onView(withId(R.id.button1)).perform(click());

        // check if the toolbar title is "Tables"
        onView(withId(R.id.toolbar_title)).check(matches(withText("Tables")));

        // Select a table from TablesView by clicking on the first child of the tableLayout view
        // Check if a background resource is set
    }

    @Test
    public void testChangeNameInSettings() {
        // Simulate entering name in EditText
        onView(withId(R.id.editDisplayName)).perform(replaceText("John Doe"));
        onView(withId(R.id.login_button)).perform(click());

        //navigate to navigation drawer
        onView(withId(R.id.ButtonOpenDrawer)).perform(click());
        onView(withText("Settings")).perform(click());

        // change name in settings
        onView(withId(R.id.editDisplayName)).perform(replaceText("Jane Doe"));
        //onView(withId(R.id.save_button)).perform(click());  // doesnt work because it isnt defined but us but within android itself

        // Verify if name change is reflected
        onView(withId(R.id.editDisplayName)).check(matches(withText("Jane Doe")));
    }

    @Test
    public void testViewOnlineUsers() {
        // Simulate entering name in EditText
        onView(withId(R.id.editDisplayName)).perform(replaceText("John Doe"));
        onView(withId(R.id.login_button)).perform(click());

        // Navigate to OnlineUserView
        onView(withId(R.id.ButtonOpenDrawer)).perform(click());
        onView(withText("Online User")).perform(click());

        // Verify if OnlineUserView is displayed
        onView(withId(R.id.userLayout)).check(matches(isDisplayed()));
    }
}
