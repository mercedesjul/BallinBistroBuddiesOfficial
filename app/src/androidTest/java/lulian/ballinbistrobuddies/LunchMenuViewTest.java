package lulian.ballinbistrobuddies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import lulian.ballinbistrobuddies.view.LunchMenuView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lulian.ballinbistrobuddies.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test class for testing the LunchMenuView activity.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LunchMenuViewTest {

    @Rule
    public ActivityScenarioRule<LunchMenuView> activityScenarioRule =
            new ActivityScenarioRule<>(LunchMenuView.class);

    /**
     * Test case to verify the initialization of WebView in LunchMenuView.
     */
    @Test
    public void testWebViewInitialization() {
        onView(withId(R.id.webView)).check(matches(isDisplayed()));
    }

    /**
     * Test case to verify that JavaScript is enabled in WebView settings.
     */
    @Test
    public void testWebViewJavaScriptEnabled() {
        onView(withId(R.id.webView)).check((view, noViewFoundException) -> {
            WebSettings webSettings = ((WebView) view).getSettings();
            assert webSettings != null;
            assert webSettings.getJavaScriptEnabled();
        });
    }

    /**
     * Test case to verify that the WebView loads the specified web page URL.
     */
    @Test
    public void testWebViewLoadsWebPage() {
        onView(withId(R.id.webView)).check((view, noViewFoundException) -> {
            WebView webView = (WebView) view;
            String expectedUrl = "https://www.stw.berlin/mensen/einrichtungen/hochschule-f%C3%BCr-technik-und-wirtschaft-berlin/mensa-htw-wilhelminenhof.html";
            String currentUrl = webView.getUrl();
            assert currentUrl != null && currentUrl.equals(expectedUrl);
        });
    }

    /**
     * Test case to verify if the WebView has correct dimensions and constraints.
     */
    @Test
    public void testWebViewDimensionsAndConstraints() {
        onView(withId(R.id.webView)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.webView)).check((view, noViewFoundException) -> {
            // Check width and height constraints of WebView
            int width = view.getWidth();
            int height = view.getHeight();
            assert width > 0 && height > 0; // Ensure WebView has non-zero dimensions
        });
    }
}
