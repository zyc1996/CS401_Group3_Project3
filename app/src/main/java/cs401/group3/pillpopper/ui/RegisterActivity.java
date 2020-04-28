package cs401.group3.pillpopper.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cs401.group3.pillpopper.R;

// The page to register a new user
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

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


        //grab object poitning to BUtton in GUI and register THIS class as its event handler
        ((Button) findViewById(R.id.registerButton)).setOnClickListener(this);
    }



    // When the page starts
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    /**
     * Event listener to perform firebase registration of new user using an email and password
     * @param v View from the activity to pass to onClick
     */
    @Override
    public void onClick(View v) {
        //STEP 1: get user info from GUI
        // Grab username, password, confirm password, doctor code the user typed in
        username = ((EditText) findViewById(R.id.userNameEdit)).getText().toString();

        // Note: Need to rename XML Ids
        password = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();
        doctorCode = ((EditText) findViewById(R.id.doctorCode)).getText().toString();



        //STEP2:  data vailadtion
        // confirm that all 3 fields have data etc (username, apssword, confirmpassword)
        // confirm password and confrimPasword are the asme before continuing




        //STEP 3: at this point all info correct go to create new user
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("REGISTER", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("REGISTER", "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Registration Successful.",
                                    Toast.LENGTH_LONG).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("REGISTER", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}