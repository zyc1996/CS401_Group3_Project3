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

public class PatientProfileActivity extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    //private Patient patient;

    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);
        mDescription = findViewById(R.id.description_text);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.patientProfileToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
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
        startActivityForResult(intent, REQUEST_CODE);
    }

    //receiving changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("picture_URL")){
                Log.d("TagP","Picture URL returned");
                //TODO:Picture stuff later
            }
            if(data.hasExtra("description")){
                Log.d("TagD","Description returned");
                String description = data.getExtras().getString("description");
                mDescription.setText(description);
            }
        }
    }
}
