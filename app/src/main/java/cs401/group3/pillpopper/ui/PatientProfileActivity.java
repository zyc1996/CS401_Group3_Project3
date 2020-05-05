package cs401.group3.pillpopper.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
import cs401.group3.pillpopper.data.Patient;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The Patient profile starting activity, what is shown on the patient profile screen
 */
public class PatientProfileActivity extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    //dummy variable
    private String userID;
    private int ACCOUNT_TYPE;
    private Patient patient;

    private TextView mDescription;
    private TextView mName;
    private TextView mCode;
    private TextView mJoinDate;

    private ImageView mProfilePicture;
    private Bitmap mProfilePhoto;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.profilePatientToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mDescription = findViewById(R.id.description_text);
        mName = findViewById(R.id.user_name);
        mCode = findViewById(R.id.user_code_display);
        mJoinDate = findViewById(R.id.join_date_display);
        mProfilePicture = findViewById(R.id.profile_picture);

        //for database usage
        Intent intent = getIntent();

        if (intent.getExtras().isEmpty()) {
            return;
        }

        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");


        patient = new Patient();

        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");

        result.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    patient = new Patient(dataSnapshot.child("user_name").getValue(String.class),
                            dataSnapshot.child("email").getValue(String.class),
                            "");
                    patient.set_personal_description(dataSnapshot.child("personal_description").getValue(String.class));
                    patient.set_created_at(dataSnapshot.child("created_at").getValue(Date.class));

                    Log.i("my tag", dataSnapshot.getValue().toString());

                    if(patient.get_personal_description() != null) {
                        mDescription.setText(patient.get_personal_description());
                    }
                    mName.setText(patient.get_user_name());
                    mCode.setText(patient.get_email());
                    mJoinDate.setText("Member since: "+patient.get_created_at());
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
        String patient_Name = mName.getText().toString();
        String patient_description = mDescription.getText().toString();

        //pass the name and description over, no picture URL yet
        intent.putExtra("name",patient_Name);
        intent.putExtra("description",patient_description);
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
            if(data.hasExtra("profile_picture")){
                mProfilePhoto = (Bitmap) data.getExtras().get("profile_picture");
                mProfilePicture.setImageBitmap(mProfilePhoto);
            }
            //update personal description
            if(data.hasExtra("description")){
                Log.d("TagD","Description returned");
                up_desc = data.getExtras().getString("description");
            }
            Patient.update_patient(userID, up_pic, up_desc);
            mDescription.setText(up_desc);
        }
    }
}
