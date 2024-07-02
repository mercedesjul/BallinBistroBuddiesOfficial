package lulian.ballinbistrobuddies;

import android.view.Gravity;
import androidx.core.view.GravityCompat;
import android.content.Intent;
import android.view.View;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import lulian.ballinbistrobuddies.view.TablesView;
import lulian.ballinbistrobuddies.view.StartView;
import lulian.ballinbistrobuddies.view.SettingsView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.containsString;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;

/**
 * Instrumented test class for testing navigation drawer interactions in SettingsView.
 */
@RunWith(AndroidJUnit4.class)
public class NavigationDrawerTest {

    @Rule
    public ActivityScenarioRule<SettingsView> activityScenarioRule =
            new ActivityScenarioRule<>(SettingsView.class);

    /**
     * Tests if the navigation drawer can be opened by clicking the open drawer button.
     */
    @Test
    public void testOpenNavigationDrawer() {
        onView(withId(R.id.ButtonOpenDrawer)).perform(click());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }

    /**
     * Tests if the TablesView can be selected from the navigation drawer.
     */
    @Test
    public void testSelectTablesView() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(GravityCompat.END));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tables_view));
        onView(withId(R.id.toolbar_title)).check(matches(withText("Quadrants")));
    }

    /**
     * Tests if the MealPlan can be selected from the navigation drawer.
     */
    @Test
    public void testSelectMealPlan() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(GravityCompat.END)); // Open drawer
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.meal_plan)); // Navigate to MealPlan
        // Wait for the WebView to load
        onWebView().check(webMatches(getCurrentUrl(), containsString("https://www.stw.berlin/mensen/einrichtungen/hochschule-f%C3%BCr-technik-und-wirtschaft-berlin/mensa-htw-wilhelminenhof.html"))); // Replace with your expected URL
    }
}