package cs401.group3.pillpopper.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Patient;

public class PatientProfileActivity extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    //dummy variable
    private String userID;
    private int ACCOUNT_TYPE;
    private Patient patient = new Patient("Jack Jumbo","someone@gmail.com","123456");

    private TextView mDescription;
    private TextView mName;
    private TextView mCode;
    private TextView mJoinDate;

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



        mName.setText(patient.get_user_name());
        mCode.setText("Patient Code: " + patient.get_patient_id());
        mJoinDate.setText("Member since: "+patient.get_created_at());


        //for database usage
        Intent intent = getIntent();

        if (intent.getExtras().isEmpty()) {
            return;
        }

        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");

        if(patient.get_personal_description() != null) {
            mDescription.setText(patient.get_personal_description());
        }
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
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("dummy_data")){
                //dummy data idk
            }
            //updates picture URL
            if(data.hasExtra("picture_URL")){
                Log.d("TagP","Picture URL returned");
                //TODO:Picture stuff later
            }
            //update personal description
            if(data.hasExtra("description")){
                Log.d("TagD","Description returned");
                String description = data.getExtras().getString("description");
                mDescription.setText(description);
            }
        }
    }
}
