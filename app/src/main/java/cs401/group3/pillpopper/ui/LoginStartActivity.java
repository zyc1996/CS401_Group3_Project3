package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cs401.group3.pillpopper.R;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The "Main" starting activity, what is shown when the app is launched
 */
public class LoginStartActivity extends AppCompatActivity implements View.OnClickListener {

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

        if (username.equals("")) {
            Toast.makeText(LoginStartActivity.this, "Please enter a username.",
                    Toast.LENGTH_SHORT).show();

        } else if (password.equals("")) {
            Toast.makeText(LoginStartActivity.this, "Please enter a password.",
                    Toast.LENGTH_SHORT).show();

        } else {

            DatabaseReference result = FirebaseDatabase.getInstance().getReference("patients");
            Query q = result.orderByChild("email").equalTo(username);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapElement : dataSnapshot.getChildren()) {
                            if (snapElement.child("password").getValue().toString().equals(password)) {
                                Log.i("my tag", "Logged in successfully");
                                launchPatientHomePageActivity(snapElement.getKey());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("my tag", "Login error");
                }
            });

            result = FirebaseDatabase.getInstance().getReference("doctors");
            q = result.orderByChild("email").equalTo(username);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapElement : dataSnapshot.getChildren()) {
                            if (snapElement.child("password").getValue().toString().equals(password)) {
                                Log.i("my tag", "Logged in successfully");
                                launchDoctorHomePageActivity(snapElement.getKey());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("my tag", "Login error");
                }
            });

            // Step 3: Confirm sign in with mAuth
/*
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
                                Toast.makeText(LoginStartActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
*/
        }

    }

    // Private helper method to launch the register page
    private void launchRegisterActivity() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
    }

    // Private helper method to launch the home page
    private void launchPatientHomePageActivity(String userID) {
        Intent intent = new Intent(this, HomepagePatientActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type", 1);
        startActivity(intent);
    }
    private void launchDoctorHomePageActivity(String userID) {
        Intent intent = new Intent(this, HomepageDoctorActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type", 2);
        startActivity(intent);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(resultCode == RESULT_OK){
//            if(data.hasExtra("success_regist")){
//                Log.d("success","Got here");
//                Toast.makeText(this,data.getExtras().getString("success_regist"),Toast.LENGTH_LONG).show();
//            }
//            else if(data.hasExtra("fail_regist")){
//                Toast.makeText(this,data.getExtras().getString("fail_regist"),Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
