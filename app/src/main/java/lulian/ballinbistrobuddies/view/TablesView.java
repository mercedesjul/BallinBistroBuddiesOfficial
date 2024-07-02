package lulian.ballinbistrobuddies.view;


import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.GridLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;



import lulian.ballinbistrobuddies.R;
import lulian.ballinbistrobuddies.control.ConnectionManager;
import lulian.ballinbistrobuddies.control.TableController;

import lulian.ballinbistrobuddies.model.TableModel;
import lulian.ballinbistrobuddies.model.UserModel;

/**
 * Represents the main activity view for managing tables, quadrants, and user interactions within the app.
 * This class extends AppCompatActivity and implements NavigationView.OnNavigationItemSelectedListener
 * to handle navigation drawer interactions.
 */
public class TablesView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private GridLayout gridLayoutQuadrants;
    private GridLayout tablesLayout;
    private GridLayout userLayout;
    private TextView toolbarTitle;
    private Button backButton;
    private TableController tableController;
    private int currentQuadrant;
    private UserModel currentUser;

    ConnectionManager connectionManager;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, registering click listeners, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserModelFromPreferences();
        initializeNetwork();
        tableController = TableController.getInstance(currentUser);
        setContentView(R.layout.activity_tables_view);

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find TextView for Toolbar title
        toolbarTitle = findViewById(R.id.toolbar_title);

        // Initialize Layouts for Quadrants, Tables, and Users
        gridLayoutQuadrants = findViewById(R.id.gridLayoutQuadrants);
        tablesLayout = findViewById(R.id.tablesLayout);
        userLayout = findViewById(R.id.userLayout);

        // Initialize NavigationDrawer
        navigationView.setNavigationItemSelectedListener(this);

        // Initialize Back Button
        backButton = findViewById(R.id.ButtonGoBack);

        // Setup quadrant buttons (This is so scuffed, but oh well)
        setupQuadrantButton(R.id.button1, "Quadrant 1", 1);
        setupQuadrantButton(R.id.button2, "Quadrant 2", 2);
        setupQuadrantButton(R.id.button3, "Quadrant 3", 3);
        setupQuadrantButton(R.id.button4, "Quadrant 4", 4);
        setupQuadrantButton(R.id.button5, "Quadrant 5", 5);
        setupQuadrantButton(R.id.button6, "Quadrant 6", 6);
        setupQuadrantButton(R.id.button7, "Quadrant 7", 7);
        setupQuadrantButton(R.id.button8, "Quadrant 8", 8);

        // Setup click listener for opening drawer
        Button openDrawerButton = findViewById(R.id.ButtonOpenDrawer);
        openDrawerButton.setOnClickListener(this::onOpenDrawerClick);

        // method to handle the back button click
        backButton.setOnClickListener(v -> {
            // if the view is the tables view, go back to the quadrants view
            if (tablesLayout.getVisibility() == View.VISIBLE) {
                openQuadrantView();
            }
            // if the view is the user view, go back to quadrants view
            else if (userLayout.getVisibility() == View.VISIBLE) {
                openQuadrantView();
            }
            // if the view is the quadrants view, go back to the main view
            else if (gridLayoutQuadrants.getVisibility() == View.VISIBLE) {
                finish();
            }
        });
    }

    /**
     * Sets up buttons for each table in the current quadrant.
     * Throws IllegalStateException if currentQuadrant is 0, indicating no quadrant is selected.
     */
    private void setupTableButtons() {
        if (currentQuadrant == 0) {
            throw new IllegalStateException("Current Quadrant must not be 0 for table creation");
        }
        tablesLayout.removeAllViews();
        tableController.getTablesByQuadrant(currentQuadrant).forEach(table -> {
           Button tableButton = new Button(getApplicationContext());
           tableButton.setBackground(new ColorDrawable(Color.WHITE));
           tableButton.setText(table.toString());
           tableButton.setOnClickListener(v -> {
               if (!(v.getBackground() instanceof ColorDrawable)) {
                   return;
               }
               clearActiveTableButtonStates();
               tableController.seatCurrentUserAtTable(currentUser, table);
               connectionManager.advertiseService();
               v.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.button_outline));
           });
           tablesLayout.addView(tableButton);
        });
    }

    private void clearActiveTableButtonStates() {
        for (int i = 0; i < tablesLayout.getChildCount(); i++) {
            Button button = (Button) tablesLayout.getChildAt(i);
            button.setBackground(new ColorDrawable(Color.WHITE));
        }
    }

    /**
     * Initializes the network communication components for the application.
     * This method sets up the intent filter for Wi-Fi P2P (peer-to-peer) state changes and actions,
     * creates and initializes the ConnectionManager singleton instance, and registers the application
     * to listen for the specified Wi-Fi P2P intents. This setup is crucial for enabling Wi-Fi Direct
     * features within the app, such as discovering and connecting to nearby devices without the need
     * for a Wi-Fi access point.
     */
    private void initializeNetwork() {
        connectionManager = ConnectionManager.getInstance(getApplicationContext(), this);
        connectionManager.initialize();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();
        // Check if the item clicked is the settings item
        if (id == R.id.settings) {
            // Open SettingsView activity
            Intent intent = new Intent(this, SettingsView.class);
            startActivity(intent);
        } else if (id == R.id.user_view) {
            openUserView();
        } else if(id == R.id.tables_view) {
            openQuadrantView();
        } else if (id == R.id.meal_plan){
            Intent intent = new Intent(this, LunchMenuView.class);
            startActivity(intent);
        }

        // Close the drawer when item is clicked
        drawerLayout.closeDrawer(navigationView);
        // Return true to indicate the item was clicked
        return true;
    }

    /**
     * Sets up a quadrant button with specified properties.
     * This method finds a button by its ID, sets its text to the provided value, and assigns an onClickListener
     * that triggers the opening of the TableView for the specified quadrant.
     *
     * @param buttonId The resource ID of the button to be set up.
     * @param text The text to be displayed on the button.
     * @param quadrantId The ID of the quadrant that the button represents, used to open the correct TableView.
     */
    private void setupQuadrantButton(int buttonId, String text, int quadrantId) {
        Button button = findViewById(buttonId);
        button.setText(text);
        button.setOnClickListener(view -> openTableView(quadrantId));
    }

    /**
     * Dynamically creates and adds buttons for each user to the userLayout.
     * This method first clears any existing views from the userLayout to ensure
     * that it is starting with a clean slate. It then retrieves a list of all user names
     * from the tableController and iterates over them. For each user name, it creates a new
     * Button, sets the button's text to the user's name, and adds the button to the userLayout.
     * This allows for dynamic UI updates based on the current set of users.
     */
    private void setupUserButtons() {
        userLayout.removeAllViews();
        tableController.getAllUserNames().forEach(e -> {
            Button button = new Button(getApplicationContext());
            button.setText(e);
            userLayout.addView(button);
        });
    }

    /**
     * Opens the Quadrant View by making the grid layout visible and hiding the tables and user layouts.
     * This method is responsible for transitioning the UI to display the quadrant view where users can
     * select different quadrants. It also updates the toolbar title to "Quadrants" to reflect the current view.
     */
    private void openQuadrantView() {
        showGridLayout();
        hideTablesLayout();
        hideUserLayout();
        toolbarTitle.setText("Quadrants");
    }

    /**
     * Opens the TableView for a specific quadrant.
     * This method sets the current quadrant to the one specified, then proceeds to setup table buttons
     * for that quadrant. It also manages the visibility of different layouts to ensure that only the
     * tables layout is visible, while hiding the grid and user layouts. Finally, it updates the toolbar
     * title to "Tables" to reflect the current view being displayed.
     *
     * @param currentQuadrant The ID of the quadrant for which the table view should be opened.
     */
    private void openTableView(int currentQuadrant) {
        this.currentQuadrant = currentQuadrant;
        setupTableButtons();
        hideGridLayout();
        showTablesLayout();
        hideUserLayout();
        toolbarTitle.setText("Tables");
    }

    /**
     * Opens the User View by updating the UI to display user-specific information.
     * This method performs several UI updates to transition from the current view to the User View:
     * 1. Calls {@code setupUserButtons()} to dynamically create and add buttons for each user to the userLayout.
     * 2. Calls {@code hideGridLayout()} to hide the grid layout that displays quadrants.
     * 3. Calls {@code hideTablesLayout()} to hide the tables layout.
     * 4. Calls {@code showUserLayout()} to make the user layout visible, displaying the user-specific buttons.
     * 5. Sets the toolbar title to "Online User" to reflect the current view being displayed.
     */
    private void openUserView() {
        setupUserButtons();
        hideGridLayout();
        hideTablesLayout();
        showUserLayout();
        toolbarTitle.setText("Online User");
    }

    /**
     * Handles the click event to open the navigation drawer.
     * This method is linked to a UI element (e.g., a button) via its onClick attribute in the layout XML.
     * When the UI element is clicked, this method opens the navigation drawer, allowing the user to access
     * the navigation options.
     *
     * @param view The view that was clicked to trigger this method.
     */
    public void onOpenDrawerClick(View view) {
        drawerLayout.openDrawer(navigationView);
    }

    // --------------------------------------------------
    //  Helper methods:

    private void hideGridLayout() {
        gridLayoutQuadrants.setVisibility(View.GONE);
    }
    private void showGridLayout(){
        gridLayoutQuadrants.setVisibility(View.VISIBLE);
    }

    private void showTablesLayout() {
        tablesLayout.setVisibility(View.VISIBLE);
    }
    private void hideTablesLayout() {
        tablesLayout.setVisibility(View.GONE);
    }

    public UserModel getUserModelFromPreferences() {
        if (currentUser != null) {
            return currentUser;
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        return currentUser = new UserModel(
                sharedPref.getString("displayName", "John Doe"),
                sharedPref.getString("theme_mode_selection", "")
        );
    }

    private void showUserLayout() {userLayout.setVisibility(View.VISIBLE);}
    private void hideUserLayout() {userLayout.setVisibility(View.GONE);}
}