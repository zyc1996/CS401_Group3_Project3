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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.adapter.PrescriptionAdapter;
import cs401.group3.pillpopper.data.Patient;
import cs401.group3.pillpopper.data.Prescription;

public class DoctorViewHomepagePatientActivity extends AppCompatActivity implements PrescriptionAdapter.RecyclerViewClickListener{

    private String patientID, patientName;
    private final int ACCOUNT_TYPE = 1;
    private Patient patient;
    private int REQUEST_CODE_ADD = 2;
    private int REQUEST_CODE_EDIT = 3;
    private DataSnapshot user_info;
    private String day_selection;

    private TextView mPatientName;

    //recyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    //dummy data (local)
    private List<Prescription> prescription_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient_doctor_view);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.doctor_view_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if(intent.getExtras() == null){
            return;
        }
        mPatientName = findViewById(R.id.unique_view_patient_name_from_doc);
        patientName = intent.getExtras().getString("patient_name");
        Log.i("string check", patientName);
        mPatientName.setText(patientName); //WHY DO YOU CRASH FOR NULL OBJECT REFERENCE WHEN THERE IS NO NULL OBJECT
        Log.i("crash tag", mPatientName.getText().toString());
        patientID = intent.getExtras().getString("patient_ID");


        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");

        result.child(patientID).addListenerForSingleValueEvent(new ValueEventListener() {
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

        refresh_prescription_list();

        //set recyclerview bounds
        mRecyclerView = findViewById(R.id.prescription_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriptionAdapter(prescription_list,this);
        mRecyclerView.setAdapter(adapter);

    }

    public void refresh_prescription_list(){
        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");
        result.child(patientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user_info = dataSnapshot;
                    Log.i("my tag", user_info.getValue().toString());
                    ArrayList<String> prescription_keys;
                    prescription_keys = new ArrayList<String>();

                    // for each day in the prescriptions section of the user's data,
                    // get the prescription keys for the selected day
                    for(DataSnapshot key : dataSnapshot.child("prescriptions").child(day_selection).getChildren()){
                        prescription_keys.add(key.getKey());
                    }

                    //next, query the database with the keys you received.
                    populate_prescriptions(prescription_keys);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });

    public void populate_prescriptions(ArrayList<String> keys){
        prescription_list.clear();
        //for each key in keys, query the database
        for(String key : keys){
            FirebaseDatabase.getInstance().getReference("prescriptions")
                    .child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataIn) {

                    Prescription new_entry = new Prescription(
                            dataIn.child("content").getValue(String.class),
                            dataIn.child("timed").getValue(Boolean.class),
                            dataIn.child("times_per_day").getValue(Integer.class),
                            dataIn.child("time_between_dose").getValue(Integer.class),
                            dataIn.child("start_time").getValue(String.class)
                    );
                    new_entry.set_id(dataIn.getKey());
                    prescription_list.add(new_entry);
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
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }
    public void onBack(MenuItem back) {
        finish();
    }

    public void launchAddPrescription(View view){
        Intent intent = new Intent (this, AddPrescriptionActivity.class);
        String name = mPatientName.getText().toString();
        intent.putExtra("name",name);
        intent.putExtra("user_ID", patientID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {

            boolean[] days = new boolean[]{false,false,false,false,false,false,false};
            boolean scheduleType=false;
            String startTime = "", description = "";
            int timesPerDay=0, breakHours=0;
            Prescription prescription;

            //Step 1: get all the returning datas
            if(data.hasExtra("Dummy Extra")){
                //idk dummy variables
            }
            if (data.hasExtra("days")) {
                days = Arrays.copyOf(data.getExtras().getBooleanArray("days"), 7);
            }
            if (data.hasExtra("schedule_type")) {
                scheduleType = data.getExtras().getBoolean("schedule_type");
                if (scheduleType) {
                    if (data.hasExtra("start_time")) {
                        startTime = data.getExtras().getString("start_time");
                    }
                }
            }
            if (data.hasExtra("times_per_day")) {
                timesPerDay = data.getExtras().getInt("times_per_day");
            }
            if (data.hasExtra("break_hours")) {
                breakHours = data.getExtras().getInt("break_hours");
            }
            if (data.hasExtra("description")) {
                description = data.getExtras().getString("description");
            }

            //step 2: construct the prescription
            prescription = new Prescription(description,scheduleType,timesPerDay,breakHours,startTime);

            //step 3: load prescription into patient's date array

            for(int i = 0; i < 7 ; i++){
                if(days[i]){
                    switch (i) {
                        case 0:
                            patient.add_prescription(patientID,prescription,"Monday");
                            break;
                        case 1:
                            patient.add_prescription(patientID,prescription,"Tuesday");
                            break;
                        case 2:
                            patient.add_prescription(patientID,prescription,"Wednesday");
                            break;
                        case 3:
                            patient.add_prescription(patientID,prescription,"Thursday");
                            break;
                        case 4:
                            patient.add_prescription(patientID,prescription,"Friday");
                            break;
                        case 5:
                            patient.add_prescription(patientID,prescription,"Saturday");
                            break;
                        case 6:
                            patient.add_prescription(patientID,prescription,"Sunday");
                            break;
                        default:
                            break;
                    }
                }
            }

        }
        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){
            int changeIndex = -1;
            if(data.hasExtra("Dummy Extra")){
                //idk dummy variables
            }
            //check with ID first
            if(data.hasExtra("prescription_ID")){
                String returnedID = data.getExtras().getString("prescription_ID");
                //fetch the correct prescription

                Log.i("return tag1",returnedID);
                for(int i = 0; i < prescription_list.size(); i++){
                    Log.i("return tag2", prescription_list.get(i).get_id());
                    if(prescription_list.get(i).get_id().equals(returnedID)){
                        changeIndex = i;
                    }
                }
                if(changeIndex == -1){ //if somehow not found
                    return;
                }else{// if found
                    if(data.hasExtra("schedule_type")){
                        prescription_list.get(changeIndex).set_timed(data.getExtras().getBoolean("schedule_type"));
                        if(data.getExtras().getBoolean("schedule_type")){
                            prescription_list.get(changeIndex).setStart_time(data.getExtras().getString("start_time"));
                        }else{
                            prescription_list.get(changeIndex).setStart_time("");
                        }
                    }
                    if(data.hasExtra("times_per_day")){
                        prescription_list.get(changeIndex).set_times_per_day(data.getExtras().getInt("times_per_day"));
                    }
                    if(data.hasExtra("break_hours")){
                        prescription_list.get(changeIndex).set_time_between_dose(data.getExtras().getInt("break_hours"));
                    }
                    if(data.hasExtra("description")){
                        prescription_list.get(changeIndex).set_content(data.getExtras().getString("description"));
                    }
                }
            }
            adapter.notifyItemChanged(changeIndex);
        }
    }

    @Override
    public void recyclerViewListClicked(int position) {
        Intent intent = new Intent(this, EditPrescriptionActivity.class);
        String name = mPatientName.getText().toString();
        intent.putExtra("name",name);
        intent.putExtra("user_ID", patientID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        intent.putExtra("prescription_ID", prescription_list.get(position).get_id());

        intent.putExtra("schedule_type", prescription_list.get(position).is_timed());
        intent.putExtra("start_time", prescription_list.get(position).get_Start_time());
        intent.putExtra("times_per_day", prescription_list.get(position).get_times_per_day());
        intent.putExtra("break_hours", prescription_list.get(position).get_time_between_dose());
        intent.putExtra("description", prescription_list.get(position).get_content());
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

    public void mondayButton(View v){
        day_selection = "Monday";
        refresh_prescription_list();
    }

    public void tuesdayButton(View v){
        day_selection = "Tuesday";
        refresh_prescription_list();
    }

    public void wednesdayButton(View v){
        day_selection = "Monday";
        refresh_prescription_list();
    }

    public void thursdayButton(View v){
        day_selection = "Thursday";
        refresh_prescription_list();
    }

    public void fridayButton(View v){
        day_selection = "Friday";
        refresh_prescription_list();
    }

    public void saturdayButton(View v){
        day_selection = "Saturday";
        refresh_prescription_list();
    }

    public void sundayButton(View v){
        day_selection = "Sunday";
        refresh_prescription_list();
    }
}
