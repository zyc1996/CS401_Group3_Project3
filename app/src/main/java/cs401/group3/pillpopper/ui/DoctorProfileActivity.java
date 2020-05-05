package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Doctor;

/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The Doctor profile starting activity, what is shown on the doctor profile screen
 */
public class DoctorProfileActivity extends AppCompatActivity {
    /**
     * Integer to act as code for data intent requests
     */
    private int REQUEST_CODE = 1;

    /**
     * The String to represent the user ID
     */
    private String userID;

    /**
     * The int to represent the type of account Doctor
     */
    private int ACCOUNT_TYPE;

    /**
     * The sample doctor to fill GUI data with
     */
    private Doctor doctor;

    /**
     * The TextView for the description
     */
    private TextView mDescription;

    /**
     * The TextView for the name
     */
    private TextView mName;

    /**
     * The TextView for the Doctor Code
     */
    private TextView mCode;

    /**
     * The TextView for the Join Date
     */
    private TextView mJoinDate;

    /**
     * On creation of activity initializes doctor profile activity
     * @param savedInstanceState Bundle for saving instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_doctor_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mDescription = findViewById(R.id.description_text);
        mName = findViewById(R.id.user_name);
        mCode = findViewById(R.id.user_code_display);
        mJoinDate = findViewById(R.id.join_date_display);

        //for database usage
        Intent intent = getIntent();
        if (intent.getExtras().isEmpty()) {
            return;
        }
        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");

        doctor = new Doctor();

        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("doctors");

        result.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    doctor = new Doctor(dataSnapshot.child("user_name").getValue(String.class),
                            dataSnapshot.child("email").getValue(String.class),
                            "");
                    doctor.setPersonalDescription(dataSnapshot.child("personal_description").getValue(String.class));
                    doctor.setCreatedAt(dataSnapshot.child("created_at").getValue(Date.class));

                    Log.i("my tag", dataSnapshot.getValue().toString());

                    if(doctor.getPersonalDescription() != null) {
                        mDescription.setText(doctor.getPersonalDescription());
                    }
                    mName.setText(doctor.getUserName());
                    mCode.setText(doctor.getEmail());
                    mJoinDate.setText("Member since: "+doctor.getCreatedAt());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });

    }

    /**
     * Menu icons are inflated just as they were with actionbar
     * @param menu Menu
     * @return always true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    /**
     * edit profile of doctor
     * @param profile MenuItem profile of doctor with doctor data
     */
    public void editProfile(MenuItem profile) {
        Intent intent  = new Intent(this, ProfileEditActivity.class);
        String doctorName = mName.getText().toString();
        String doctorDescription = mDescription.getText().toString();

        intent.putExtra("name",doctorName);
        intent.putExtra("description",doctorDescription);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * Finishes activity
     * @param back MenuItem back to previous
     */
    public void profileBack(MenuItem back) {
        finish();
    }

    /**
     * receiving changes
     * @param requestCode Integer code requested
     * @param resultCode Integer resulting code
     * @param data information received
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String upDesc = "" , upPic = "";

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("dummy_data")){
                //dummy data idk
            }
            //updates picture URL
            if(data.hasExtra("picture_URL")){
                Log.d("TagP","Picture URL returned");
                //TODO:Picture stuff later
                upPic = data.getExtras().getString("picture_URL");
            }
            //update personal description
            if(data.hasExtra("description")){
                Log.d("TagD","Description returned");
                upDesc = data.getExtras().getString("description");
            }
            Doctor.updateDoctor(userID, upPic, upDesc);
            mDescription.setText(upDesc);
        }
    }






}
