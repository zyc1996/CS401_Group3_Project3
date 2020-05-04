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

    private int REQUEST_CODE = 1;
    private String userID;
    private int ACCOUNT_TYPE;
    //dummy doctor
    private Doctor doctor;


    private TextView mDescription;
    private TextView mName;
    private TextView mCode;
    private TextView mJoinDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileDoctorToolbar);
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
                    doctor.set_personal_description(dataSnapshot.child("personal_description").getValue(String.class));
                    doctor.set_created_at(dataSnapshot.child("created_at").getValue(Date.class));

                    Log.i("my tag", dataSnapshot.getValue().toString());

                    if(doctor.get_personal_description() != null) {
                        mDescription.setText(doctor.get_personal_description());
                    }
                    mName.setText(doctor.get_user_name());
                    mCode.setText("Doctor Email: " + doctor.get_email());
                    mJoinDate.setText("Member since: "+doctor.get_created_at());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    public void editProfile(MenuItem profile) {
        Intent intent  = new Intent(this, ProfileEditActivity.class);
        String doctor_name = mName.getText().toString();
        String doctor_description = mDescription.getText().toString();

        intent.putExtra("name",doctor_name);
        intent.putExtra("description",doctor_description);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    public void profileBack(MenuItem back) {
        finish();
    }

    //receiving changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String up_desc = "" , up_pic = "";

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("dummy_data")){
                //dummy data idk
            }
            //updates picture URL
            if(data.hasExtra("picture_URL")){
                Log.d("TagP","Picture URL returned");
                //TODO:Picture stuff later
                up_pic = data.getExtras().getString("picture_URL");
            }
            //update personal description
            if(data.hasExtra("description")){
                Log.d("TagD","Description returned");
                up_desc = data.getExtras().getString("description");
            }
            Doctor.update_doctor(userID, up_pic, up_desc);
            mDescription.setText(up_desc);
        }
    }






}
