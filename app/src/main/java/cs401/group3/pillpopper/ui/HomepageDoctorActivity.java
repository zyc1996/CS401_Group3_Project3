package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import cs401.group3.pillpopper.data.Doctor;
import cs401.group3.pillpopper.data.Patient;

public class HomepageDoctorActivity extends AppCompatActivity implements PatientAdapter.OnPatientListener {

    private String userID;
    private int ACCOUNT_TYPE;
    private TextView mUserName;
    private TextView mPatientEmail;
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

        result = FirebaseDatabase.getInstance().getReference("doctors");
        result.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user_info = dataSnapshot;
                    Log.i("my tag", user_info.getValue().toString());
                    mUserName = findViewById(R.id.user_name_title);
                    mUserName.setText(user_info.child("user_name").getValue(String.class));
                    refresh_patient_list();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });

        mRecyclerView = findViewById(R.id.patient_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientAdapter(patients,this);
        mRecyclerView.setAdapter(adapter);
    }


    public void refresh_patient_list(){
        DatabaseReference result;
        ArrayList<String> keys;
        keys = new ArrayList<String>();
        result = FirebaseDatabase.getInstance().getReference("doc");
        for(DataSnapshot key : user_info.child("patients").getChildren()){
            keys.add(key.getKey());
        }

        populate_patients(keys);
    }


    public void populate_patients(ArrayList<String> keys){
        patients.clear();
        //for each key in keys, query the database
        for(String key : keys){
            FirebaseDatabase.getInstance().getReference("patients")
                    .child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataIn) {

                    Patient new_entry = new Patient(
                            dataIn.child("user_name").getValue(String.class),
                            dataIn.child("email").getValue(String.class), "");
                    new_entry.set_patient_id(dataIn.getKey());
                    patients.add(new_entry);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("my tag", "User data retrieval error");
                }
            });
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }


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
        finish();
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

    public void addPatient(View v){

        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");
        result.orderByChild("user_name").equalTo(mPatientEmail.getText().toString()).
                limitToFirst(1).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Doctor.add_patient(userID, data.getKey());
                            refresh_patient_list();
                        }
                    }
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("my tag", "User data retrieval error");
                    }
        });
    }
}

