package cs401.group3.pillpopper.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
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

    /**
     * The Request Code to edit the profile
     */
    private int REQUEST_CODE = 1;

    /**
     * String of the user's ID
     */
    private String userID;

    /**
     * Code for the account type (Patient = 1, Doctor = 2)
     */
    private int ACCOUNT_TYPE;

    /**
     * The patient object to store GUI data
     */
    private Patient patient;

    /**
     * The TextView widget for the description
     */
    private TextView mDescription;

    /**
     * The TextView widget for the name
     */
    private TextView mName;

    /**
     * The TextView widget for the code
     */
    private TextView mCode;

    /**
     * The TextView widget for the join date
     */
    private TextView mJoinDate;

    /**
     * the image view that hold the profile picture
     */
    private ImageView mProfilePicture;
    /**
     * the bitmap that holds the profile picture value
     */
    private Bitmap mProfilePhoto;

    /**
     * On creation of activity initializes patient profile activity
     * @param savedInstanceState Bundle for saving instance of activity
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_patient_toolbar);
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
                    patient.setPersonalDescription(dataSnapshot.child("personal_description").getValue(String.class));
                    patient.setCreatedAt(dataSnapshot.child("created_at").getValue(Date.class));
                    mProfilePhoto = StringToBitMap(dataSnapshot.child("profile_picture").getValue(String.class));
                    mProfilePicture.setImageBitmap(mProfilePhoto);

                    Log.i("my tag", dataSnapshot.getValue().toString());

                    if(patient.getPersonalDescription() != null) {
                        mDescription.setText(patient.getPersonalDescription());
                    }
                    mName.setText(patient.getUserName());
                    mCode.setText(patient.getEmail());
                    mJoinDate.setText("Member since: "+patient.getCreatedAt());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });
    }

    /**
     *  Menu icons are inflated just as they were with actionbar
     * @param menu Menu inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    /**
     * edit profile of patient passing chosen data
     * @param profile MenuItem for profile to edit
     */
    public void editProfile(MenuItem profile) {
        Intent intent  = new Intent(this, ProfileEditActivity.class);
        String patientName = mName.getText().toString();
        String patientDescription = mDescription.getText().toString();

        //pass the name and description over, no picture URL yet
        intent.putExtra("name",patientName);
        intent.putExtra("description",patientDescription);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        intent.putExtra("profile_picture",mProfilePhoto);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * finish activity on back item
     * @param back MenuItem for back
     */
    public void profileBack(MenuItem back) {
        finish();
    }

    /**
     * receiving changes into database
     * @param requestCode Integer code requested
     * @param resultCode Integer resulting code
     * @param data input data
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
            if(data.hasExtra("profile_picture")){
                mProfilePhoto = (Bitmap) data.getExtras().get("profile_picture");
                mProfilePicture.setImageBitmap(mProfilePhoto);
                upPic = BitMapToString(mProfilePhoto);
            }
            //update personal description
            if(data.hasExtra("description")){
                Log.d("TagD","Description returned");
                upDesc = data.getExtras().getString("description");
            }
            Patient.updatePatient(userID, upPic, upDesc);
            mDescription.setText(upDesc);
        }
    }

    /**
     * Encoding bitmap to string
     * @param bitmap the profile picture bitmap
     * @return profile picture string
     */
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * encoding string to bitmap
     * @param encodedString the profile picture string
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
