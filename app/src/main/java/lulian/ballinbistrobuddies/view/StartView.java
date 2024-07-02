package lulian.ballinbistrobuddies.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import lulian.ballinbistrobuddies.R;

/**
 * StartView is an activity class that serves as the entry point for users to log in to the application.
 * It extends {@link AppCompatActivity} and demonstrates the use of shared preferences for storing user data,
 * handling window insets for edge-to-edge display, and navigating to another activity upon successful login.
 */
public class StartView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button button = findViewById(R.id.login_button);
        EditText editText = findViewById(R.id.editDisplayName);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = new Intent(this, TablesView.class);
        if (!sharedPref.getString("displayName", "").isEmpty()) {

        }
        button.setOnClickListener(v -> {
            if (editText.getText().length() == 0) {
                editText.setError("Name is required");
            } else {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("displayName", editText.getText().toString());
                editor.apply();
                startActivity(intent);
                finish(); // Finish the LoginActivity so user can't go back to it
            }
        });
    }
}