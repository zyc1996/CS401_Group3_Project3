package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Doctor;

public class HomepageDoctorActivity extends AppCompatActivity {

    private TextView mUserName;
    private Doctor doctor = new Doctor("Doc Mike","docmike@gmail.com","123456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_doctor);

        mUserName = findViewById(R.id.user_name_title);
        mUserName.setText(doctor.get_user_name());
    }

    public void launchDoctorProfile(View view){
        Intent intent = new Intent(this,DoctorProfileActivity.class);
        String doctorID = doctor.get_doctor_id();
        intent.putExtra("doctor_ID",doctorID);
        startActivity(intent);
    }

    //needs database to find patient
    //click on patient to add stuff
}
