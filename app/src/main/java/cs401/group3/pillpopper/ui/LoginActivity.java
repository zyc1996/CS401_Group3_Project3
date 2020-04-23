package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cs401.group3.pillpopper.R;

// The "Main" starting activity, what is shown when the app is launched
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Firebase authenticator
    private FirebaseAuth mAuth;

    // Buttons
    private Button mRegisterButton;
    private Button mLoginButton;

    // Strings to store username/password
    private String username;
    private String password;

    // onCreate method (What happens when the layout is created)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);

        // Get the current login instance from the Firebase Authenticator
        mAuth = FirebaseAuth.getInstance();

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
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // STEP 1: get user info from GUI
        // Grab username, password, confirm password, doctor code the user typed in
        // Note: Need to rename XML Ids

        username = ((EditText) findViewById(R.id.editUsername)).getText().toString();
        password = ((EditText) findViewById(R.id.editPassword)).getText().toString();

        // STEP2: Data validation
        // Confirm that fields have data etc (username, password)

        if (username == null) {
            Toast.makeText(LoginActivity.this, "Please enter a username.",
                    Toast.LENGTH_SHORT).show();

        } else if (password == null) {
            Toast.makeText(LoginActivity.this, "Please enter a password.",
                    Toast.LENGTH_SHORT).show();

        } else {

            // Step 3: Confirm sign in with mAuth
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Log.d("LOGIN", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                launchHomePageActivity();

                            } else {
                                // If sign in fails, display a message to the user

                                Log.d("LOGIN", "signInWithEmail:failure");
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

        }

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
