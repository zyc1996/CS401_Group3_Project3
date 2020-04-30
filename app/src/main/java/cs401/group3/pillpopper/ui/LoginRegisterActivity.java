package cs401.group3.pillpopper.ui;

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

// The page to register a new user
public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // Firebase Authenticator object
    private FirebaseAuth mAuth;

    // Class variables to hold Username, Password, Doctor Code
    private String username;
    private String password;
    private String confirmPassword;
    private String doctorCode;

    // When the page is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // Get the current login instance from the Firebase Authenticator
        mAuth = FirebaseAuth.getInstance();

        // Grab object pointing to button in GUI and register THIS class as its event handler
        ((Button) findViewById(R.id.registerButton)).setOnClickListener(this);
    }

    // When the page starts
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Event listener to perform firebase registration of new user using an email and password
     * @param v View from the activity to pass to onClick
     */

    @Override
    public void onClick(View v) {

        // STEP 1: get user info from GUI
        // Grab username, password, confirm password, doctor code the user typed in
        // Note: Need to rename XML Ids

        username = ((EditText) findViewById(R.id.usernameEdit)).getText().toString();
        password = ((EditText) findViewById(R.id.passwordEdit)).getText().toString();
        confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();
        doctorCode = ((EditText) findViewById(R.id.doctorCode)).getText().toString();

        // STEP2: Data validation
        // Confirm that all 3 fields have data etc (username, password, confirm password)
        // confirm password and confirm password are the same before continuing

        if (username == null) {
            Toast.makeText(LoginRegisterActivity.this, "Please enter a username.",
                    Toast.LENGTH_SHORT).show();

        } else if (password == null) {
            Toast.makeText(LoginRegisterActivity.this, "Please enter a password.",
                    Toast.LENGTH_SHORT).show();

        } else if (confirmPassword == null) {
            Toast.makeText(LoginRegisterActivity.this, "Please confirm your password.",
                    Toast.LENGTH_SHORT).show();

        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(LoginRegisterActivity.this, "Password and confirm password do not match.",
                    Toast.LENGTH_SHORT).show();

        } else {

        // STEP 3: At this point all info correct go to create new user
            mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d("REGISTER", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String reply = "Registration Successful.";
//                            Intent replyIntent = new Intent();
//                            replyIntent.putExtra("success_regist","Registration Successful.");
//                            setResult(RESULT_OK,replyIntent);
                            Toast.makeText(LoginRegisterActivity.this, "Registration Successful.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.

                            Log.d("REGISTER", "createUserWithEmail:failure", task.getException());
                            String reply = "Registration failed.";
//                            Intent replyIntent = new Intent();
//                            replyIntent.putExtra("fail_regist","Registration failed.");
//                            setResult(RESULT_OK,replyIntent);
                            Toast.makeText(LoginRegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        }


        //sees the toast then delay the activity end
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                },
                5000);
    }

}