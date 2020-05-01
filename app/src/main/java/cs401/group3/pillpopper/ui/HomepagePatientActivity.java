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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Patient;

public class HomepagePatientActivity extends AppCompatActivity {

    private String userID;
    private int ACCOUNT_TYPE;
    private int REQUEST_CODE = 2;
    private DataSnapshot user_info;

    private TextView mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.homepagePatientToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mUserName = findViewById(R.id.user_name_title);
        mUserName.setText(patient.get_user_name());


        Intent intent = getIntent();
        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");
        DatabaseReference result;
        if(ACCOUNT_TYPE == 2){
            result = FirebaseDatabase.getInstance().getReference("doctors");
        } else {
            result = FirebaseDatabase.getInstance().getReference("patients");
        }

        result.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user_info = dataSnapshot;
                    Log.i("my tag", user_info.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });



        mUserName = findViewById(R.id.user_name_title);
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

    public void launchPatientProfile(View view){
        Intent intent = new Intent(this,PatientProfileActivity.class);
        intent.putExtra("patient_ID",userID);
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
