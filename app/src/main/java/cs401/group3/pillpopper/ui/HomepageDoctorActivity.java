package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Doctor;

public class HomepageDoctorActivity extends AppCompatActivity {

    private TextView mUserName;
    private Doctor doctor = new Doctor("Doc Mike","docmike@gmail.com","123456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_doctor);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.homepageDoctorToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mUserName = findViewById(R.id.user_name_title);
        mUserName.setText(doctor.get_user_name());
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
        Intent intent = new Intent(this,DoctorProfileActivity.class);
        String doctorID = doctor.get_doctor_id();
        intent.putExtra("doctor_ID",doctorID);
        startActivity(intent);
    }

    public void onLogout(MenuItem logout) {
        Intent intent = new Intent(this, LoginStartActivity.class);
        startActivity(intent);
    }

    //needs database to find patient
    //click on patient to add stuff
}
