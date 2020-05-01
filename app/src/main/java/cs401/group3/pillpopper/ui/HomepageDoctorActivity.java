package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Doctor;

public class HomepageDoctorActivity extends AppCompatActivity {

    private String userID;
    private final int ACCOUNT_TYPE = 2;
    private TextView mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_doctor);

        Intent intent = getIntent();
        userID = intent.getExtras().getString("user_ID");

        mUserName = findViewById(R.id.user_name_title);
        mUserName.setText(doctor.get_user_name());
    }

    public void launchDoctorProfile(View view){
        Intent intent = new Intent(this,DoctorProfileActivity.class);
        intent.putExtra("doctor_ID",userID);
        startActivity(intent);
    }

    //needs database to find patient
    //click on patient to add stuff
}
