package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Patient;

public class HomepagePatientActivity extends AppCompatActivity {

    private Patient patient = new Patient(324525,"Jack Jumbo","Dummy Picture URL");
    private int REQUEST_CODE = 2;

    private TextView mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);

        mUserName = findViewById(R.id.user_name_title);
        mUserName.setText(patient.get_user_name());
    }

    public void launchPatientProfile(View view){
        Intent intent = new Intent(this,PatientProfileActivity.class);
        int patientID = patient.get_patient_id();
        intent.putExtra("patient_ID",patientID);
        startActivity(intent);
    }

    public void launchAddPrescription(View view){
        Intent intent = new Intent (this,AddPrescriptionActivity.class);
        String name = mUserName.getText().toString();
        intent.putExtra("name",name);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
