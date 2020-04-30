package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;

public class HomepagePatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.patientHomepageToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
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
        Intent intent = new Intent(this, PatientProfileActivity.class);
        startActivity(intent);
    }

    public void onLogout(MenuItem logout) {
        Intent intent = new Intent(this, LoginStartActivity.class);
        startActivity(intent);
    }
}
