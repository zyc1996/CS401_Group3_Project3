package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;

public class PatientHomepageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);
    }

    public void launchPatientProfileEdit(View view) {
        Intent intent  = new Intent(this,EditPatientProfileActivity.class);
        startActivity(intent);
    }
}
