package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.adapter.PatientAdapter;
import cs401.group3.pillpopper.data.Patient;

public class HomepageDoctorActivity extends AppCompatActivity implements PatientAdapter.OnPatientListener {

    private String userID;
    private int ACCOUNT_TYPE;
    private TextView mUserName;
    private DataSnapshot user_info;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<Patient> patients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_doctor);


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.homepageDoctorToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

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

        mRecyclerView = findViewById(R.id.patient_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //patient dummy test data
        if(patients.isEmpty()) {
            Patient p = new Patient("Jacky Jack", "JJ@gmail.com", "letmein");
            p.set_patient_id("Patient 1");
            p.set_personal_description("This is just a really long string that in hopes to test out the scroll view of the patient adapter of which hold this patient object that I dont't know if it even works or not but if it works then perfect and I don't want to debuug this shtty piece of sht APP over and over again cuz it's a waste of time and effort and I just don't like this project at all so please for the love of god just end my life already");
            patients.add(p);
            p = new Patient("Lilly Rose", "LR@gmail.com", "letmein");
            p.set_patient_id("Patient 2");
            p.set_personal_description("This is just a really long string that in hopes to test out the scroll view of the patient adapter of which hold this patient object that I dont't know if it even works or not but if it works then perfect and I don't want to debuug this shtty piece of sht APP over and over again cuz it's a waste of time and effort and I just don't like this project at all so please for the love of god just end my life already");
            patients.add(p);
            p = new Patient("Mike McDonalds", "MM@gmail.com", "letmein");
            p.set_patient_id("Patient 3");
            p.set_personal_description("This is just a really long string that in hopes to test out the scroll view of the patient adapter of which hold this patient object that I dont't know if it even works or not but if it works then perfect and I don't want to debuug this shtty piece of sht APP over and over again cuz it's a waste of time and effort and I just don't like this project at all so please for the love of god just end my life already");
            patients.add(p);
            p = new Patient("Rick Randy", "RR@gmail.com", "letmein");
            p.set_patient_id("Patient 4");
            p.set_personal_description("This is just a really long string that in hopes to test out the scroll view of the patient adapter of which hold this patient object that I dont't know if it even works or not but if it works then perfect and I don't want to debuug this shtty piece of sht APP over and over again cuz it's a waste of time and effort and I just don't like this project at all so please for the love of god just end my life already");
            patients.add(p);
            p = new Patient("Abby Enderson", "AE@gmail.com", "letmein");
            p.set_patient_id("Patient 5");
            p.set_personal_description("This is just a really long string that in hopes to test out the scroll view of the patient adapter of which hold this patient object that I dont't know if it even works or not but if it works then perfect and I don't want to debuug this shtty piece of sht APP over and over again cuz it's a waste of time and effort and I just don't like this project at all so please for the love of god just end my life already");

        }

        adapter = new PatientAdapter(patients,this);
        mRecyclerView.setAdapter(adapter);
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

//    public void launchDoctorProfile(View view){
//        Intent intent = new Intent(this,DoctorProfileActivity.class);
//        intent.putExtra("user_ID",userID);
//        intent.putExtra("account_type",ACCOUNT_TYPE);
//        startActivity(intent);
//    }

    //needs database to find patient
    //click on patient to add stuff
    public void launchMessages(MenuItem messages) {
        Intent intent = new Intent(this, MessagesActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivity(intent);
    }

    public void launchProfile(MenuItem profile) {
        Intent intent = new Intent(this,DoctorProfileActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivity(intent);
    }

    public void onLogout(MenuItem logout) {
        Intent intent = new Intent(this, LoginStartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPatientClick(int position) {
        Intent intent = new Intent(this,DoctorViewHomepagePatientActivity.class);
        String patientName = patients.get(position).get_user_name();
        intent.putExtra("patient_name",patientName);
        String patientID = patients.get(position).get_patient_id();
        intent.putExtra("patient_ID",patientID);
        startActivity(intent);
    }

    //needs database to find patient
    //click on patient to add stuff
}

