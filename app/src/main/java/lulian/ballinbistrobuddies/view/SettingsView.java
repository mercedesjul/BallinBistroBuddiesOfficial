package lulian.ballinbistrobuddies.view;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceFragmentCompat;

import lulian.ballinbistrobuddies.R;

/**
 * SettingsView is an activity that displays the settings for the application.
 * It allows the user to modify the application settings, such as the theme and language.
 * The settings are displayed using a fragment that loads the preferences from an XML resource.
 */

public class SettingsView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout settingsLayout;

    /**
     * Initializes the activity, setting up the UI components and fragment for settings.
     * This includes the navigation drawer, toolbar, and the settings fragment.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Initialize Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Layout for Settings
        settingsLayout = findViewById(R.id.settings);

        // Initialize NavigationDrawer
        navigationView.setNavigationItemSelectedListener(this);

        // Setup click listener for opening drawer
        Button openDrawerButton = findViewById(R.id.ButtonOpenDrawer);
        openDrawerButton.setOnClickListener(this::onOpenDrawerClick);
    }

    /**
     * Handles navigation item selection.
     * This method is called whenever an item in the navigation menu is selected.
     * It navigates to different activities based on the item selected.
     *
     * @param item The selected menu item.
     * @return true to display the item as the selected item.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();
        // Check if the item clicked is the settings item
        if (id == R.id.tables_view) {
            // Open SettingsView activity
            Intent intent = new Intent(this, TablesView.class);
            startActivity(intent);
        } else if (id == R.id.user_view) {
            Intent intent = new Intent(this, TablesView.class);
            startActivity(intent);
        } else if (id == R.id.meal_plan) {
            Intent intent = new Intent(this, LunchMenuView.class);
            startActivity(intent);
        }
        // Close the drawer when item is clicked
        drawerLayout.closeDrawer(navigationView);
        // Return true to indicate the item was clicked
        return true;
    }

    /**
     * Opens the navigation drawer when the button is clicked.
     * This method is linked to the button click event through the layout XML.
     *
     * @param view The view that was clicked.
     */
    public void onOpenDrawerClick(View view) {
        drawerLayout.openDrawer(navigationView);
    }

    /**
     * SettingsFragment is a static inner class that extends {@link PreferenceFragmentCompat}.
     * It is responsible for loading the preferences from an XML resource and displaying them.
     * The user can interact with these preferences to modify application settings.
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {
        /**
         * Initializes the preference hierarchy for this fragment.
         * This method reads the preferences from a resource file and displays them.
         *
         * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
         * @param rootKey If non-null, this preference fragment should be rooted at the PreferenceScreen with this key.
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}