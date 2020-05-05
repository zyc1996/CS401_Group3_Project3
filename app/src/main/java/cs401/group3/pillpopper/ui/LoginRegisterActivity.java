package cs401.group3.pillpopper.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Doctor;
import cs401.group3.pillpopper.data.Patient;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The page to register a new user
 */
public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // Firebase Authenticator object
    //private FirebaseAuth mAuth;

    // Class variables to hold Username, Password, Doctor Code
    private String userFullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String doctorCode;
    private int account_type;
    private Object new_user;
    private boolean email_taken;
    private boolean check1;
    private boolean check2;

    /**
     * On creation of activity initializes registration of new login activity
     * @param savedInstanceState Bundle for saving instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.registerToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        // Get the current login instance from the Firebase Authenticator
        //mAuth = FirebaseAuth.getInstance();

        // Grab object pointing to button in GUI and register THIS class as its event handler
        ((Button) findViewById(R.id.registerButton)).setOnClickListener(this);

        email_taken = false;
    }

    /**
     * When the page starts
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * enu icons are inflated just as they were with actionbar
     * @param menu Menu inflated
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    /**
     * finish when back menu item is clicked
     * @param back MenuItem for back
     */
    public void onBack(MenuItem back) {
        finish();
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
        userFullName = ((EditText) findViewById(R.id.name_field)).getText().toString();
        email = ((EditText) findViewById(R.id.usernameEdit)).getText().toString();
        password = ((EditText) findViewById(R.id.passwordEdit)).getText().toString();
        confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();
        doctorCode = ((EditText) findViewById(R.id.doctorCode)).getText().toString();
        check1 = false;
        check2 = false;

        // STEP2: Data validation
        // Confirm that all 3 fields have data etc (username, password, confirm password)
        // confirm password and confirm password are the same before continuing
        if (userFullName.equals("")) {
            Toast.makeText(LoginRegisterActivity.this, "Please enter your full name.",
                    Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(LoginRegisterActivity.this, "Please enter an email address.",
                    Toast.LENGTH_SHORT).show();
        }else if (password.equals("")) {
            Toast.makeText(LoginRegisterActivity.this, "Please enter a password.",
                    Toast.LENGTH_SHORT).show();

        } else if (confirmPassword.equals("")) {
            Toast.makeText(LoginRegisterActivity.this, "Please confirm your password.",
                    Toast.LENGTH_SHORT).show();

        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(LoginRegisterActivity.this, "Password and confirm password do not match.",
                    Toast.LENGTH_SHORT).show();
        } else {

            DatabaseReference r1, r2;
            if(!doctorCode.equals("")){
                if(doctorCode.equals("doctor_code")){
                    account_type = 2;

                }else{
                    Toast.makeText(LoginRegisterActivity.this, "Doctor code is incorrect." +
                                    " Leave the field blank to register as a normal user.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                account_type = 1;
            }
            r1 = FirebaseDatabase.getInstance().getReference("patients");
            r2 = FirebaseDatabase.getInstance().getReference("doctors");

            Query q = r1.orderByChild("email").equalTo(email);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Toast.makeText(LoginRegisterActivity.this, "This email has already been used.",
                                Toast.LENGTH_SHORT).show();
                            email_taken = true;
                    }else{
                        check1 = true;
                        if(check1 && check2){
                            registerUser();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("my tag", "Login error");
                }
            });

            q = r2.orderByChild("email").equalTo(email);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Toast.makeText(LoginRegisterActivity.this, "This email has already been used.",
                                Toast.LENGTH_SHORT).show();
                        email_taken = true;
                    }else{
                        check2 = true;
                        if(check1 && check2){
                            registerUser();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("my tag", "Login error");
                }
            });


            /* STEP 3: At this point all info correct go to create new user
            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Log.d("REGISTER", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                            String reply = "Registration Successful.";
//                            Intent replyIntent = new Intent();
//                            replyIntent.putExtra("success_regist","Registration Successful.");
//                            setResult(RESULT_OK,replyIntent);
                                Toast.makeText(LoginRegisterActivity.this, "Registration Successful.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                // If sign in fails, display a message to the user.

                                Log.d("REGISTER", "createUserWithEmail:failure", task.getException());
//                            String reply = "Registration failed.";
//                            Intent replyIntent = new Intent();
//                            replyIntent.putExtra("fail_regist","Registration failed.");
//                            setResult(RESULT_OK,replyIntent);
                                Toast.makeText(LoginRegisterActivity.this, "Registration failed.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/
        }
    }

    /**
     * Register user with information in database
     */
    void registerUser(){
        if(account_type == 1){
            new_user = new Patient(userFullName, email, password);
            ((Patient) new_user).register();
            Log.i("my tag", "Patient created");

            Toast.makeText(LoginRegisterActivity.this, "Patient Account Successfully Registered.", Toast.LENGTH_LONG).show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            finish();
                        }
                    },
                    5000);

        } else {
            new_user = new Doctor(userFullName, email, password);
            ((Doctor) new_user).register();
            Log.i("my tag", "Doctor created");

            Toast.makeText(LoginRegisterActivity.this, "Doctor Account Successfully Registered.", Toast.LENGTH_LONG).show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            finish();
                        }
                    },
                    5000);
        }
    }
}