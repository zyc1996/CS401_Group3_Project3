package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Patient;

public class PatientProfileActivity extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    private Patient patient = new Patient(324525,"Jack Jumbo","Dummy Picture URL");

    private TextView mDescription;
    private TextView mName;
    private TextView mCode;
    private TextView mJoinDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        mDescription = findViewById(R.id.description_text);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.patientProfileToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        mName = findViewById(R.id.user_name);
        mCode = findViewById(R.id.user_code_display);
        mJoinDate = findViewById(R.id.join_date_display);

        if(patient.get_personal_description() != null) {
            mDescription.setText(patient.get_personal_description());
        }
        mName.setText(patient.get_user_name());
        mCode.setText("Patient Code: " + patient.get_patient_id());
        mJoinDate.setText("Member since: "+patient.get_created_at());
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    public void profileBack(MenuItem back) {
        Intent intent = new Intent(this, HomepagePatientActivity.class);
        startActivity(intent);
    }

    public void editProfile(MenuItem edit) {
        Intent intent  = new Intent(this, ProfileEditActivity.class);
        String patient_Name = mName.getText().toString();
        String patient_description = mDescription.getText().toString();

        //pass the name and description over, no picture URL yet
        intent.putExtra("name",patient_Name);
        intent.putExtra("description",patient_description);
        startActivityForResult(intent,REQUEST_CODE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    //receiving changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
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