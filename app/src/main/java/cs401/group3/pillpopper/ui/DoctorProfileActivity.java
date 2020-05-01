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
import cs401.group3.pillpopper.data.Doctor;

public class DoctorProfileActivity extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    private Doctor doctor = new Doctor("Doc Mike","docmike@gmail.com","123456");


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

        if(doctor.get_personal_description() != null){
            mDescription.setText(doctor.get_personal_description());
        }
        mName.setText(doctor.get_user_name());
        mCode.setText("Doctor Code: " + doctor.get_doctor_id());
        mJoinDate.setText("Member since: "+doctor.get_created_at());

        //for database usage
        Intent intent = getIntent();
        if (intent.getExtras().equals(null)) {
            return;
        }
        String doctorID = intent.getExtras().getString("doctor_ID");
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    public void launchProfileEdit(View view) {
        Intent intent  = new Intent(this, ProfileEditActivity.class);
        String doctor_name = mName.getText().toString();
        String doctor_description = mDescription.getText().toString();

        intent.putExtra("name",doctor_name);
        intent.putExtra("description",doctor_description);
        startActivityForResult(intent,REQUEST_CODE);
    }

    public void onBack(MenuItem back) {
        Intent intent = new Intent(this, LoginStartActivity.class);
        startActivity(intent);
    }

    //receiving changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            assert data != null;
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
