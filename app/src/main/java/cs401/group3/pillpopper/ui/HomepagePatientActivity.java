package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Patient;

public class HomepagePatientActivity extends AppCompatActivity {

    private Patient patient = new Patient("Jack Jumbo","someone@gmail.com","123456");
    private int REQUEST_CODE = 2;

    private TextView mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.registerToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mUserName = findViewById(R.id.user_name_title);
        mUserName.setText(patient.get_user_name());
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    public void launchMessages(MenuItem messages) {
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    public void launchProfile(MenuItem profile) {
        Intent intent = new Intent(this,PatientProfileActivity.class);
        String patientID = patient.get_patient_id();
        intent.putExtra("patient_ID",patientID);
        startActivity(intent);
    }

    public void onLogout(MenuItem logout) {
        Intent intent = new Intent(this, LoginStartActivity.class);
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
