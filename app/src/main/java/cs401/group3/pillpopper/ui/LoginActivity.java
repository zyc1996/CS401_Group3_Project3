package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;

// The "Main" starting activity, what is shown when the app is launched
public class LoginActivity extends AppCompatActivity {

    // Buttons
    private Button mRegisterButton;
    private Button mLoginButton;

    // onCreate method (What happens when the layout is created)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);

        // Link the register button to send you to the register page
        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegisterActivity();
            }
        });

        // Link the login button to send you to the home page
        mLoginButton = findViewById(R.id.signInButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomePageActivity();
            }
        });
    }

    // Private helper method to launch the register page
    private void launchRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // Private helper method to launch the home page
    private void launchHomePageActivity() {
        Intent intent = new Intent(this, PatientHomepageActivity.class);
        startActivity(intent);
    }
}
