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

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.adapter.PrescriptionAdapter;
import cs401.group3.pillpopper.data.Patient;
import cs401.group3.pillpopper.data.Prescription;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: Activity for a doctor to view a patient and their information
 */
public class DoctorViewHomepagePatientActivity extends AppCompatActivity implements PrescriptionAdapter.RecyclerViewClickListener{

    /**
     * Strings for patient id and name
     */
    private String patientID, patientName;

    /**
     * account type doctor
     */
    private final int ACCOUNT_TYPE = 1;

    /**
     * patient object for adding
     */
    private Patient patient;

    /**
     * request code to add a prescription
     */
    private int REQUEST_CODE_ADD = 2;

    /**
     * request code to edit a prescription
     */
    private int REQUEST_CODE_EDIT = 3;

    /**
     * user info data
     */
    private DataSnapshot userInfo;

    /**
     * String for day selection
     */
    private String daySelection;

    /**
     * text input for patient name
     */
    private TextView mPatientName;

    /**
     * RecyclerView of the patients for the doctor
     */
    private RecyclerView mRecyclerView;

    /**
     * RecyclerView Adapter for the patients of the doctor
     */
    private RecyclerView.Adapter adapter;

    /**
     * List to store prescription data
     */
    private List<Prescription> prescriptionList = new ArrayList<>();

    /**
     * On creation of activity initializes doctor viewing homepage of patient
     * @param savedInstanceState Bundle for saving instance of activity
     */
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
        mPatientName.setText(patientName);
        Log.i("crash tag", mPatientName.getText().toString());
        patientID = intent.getExtras().getString("patient_ID");

        daySelection = "Monday";

        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");

        result.child(patientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInfo = dataSnapshot;
                    Log.i("my tag", userInfo.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });

        refreshPrescriptionList();

        //set recyclerview bounds
        mRecyclerView = findViewById(R.id.prescription_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriptionAdapter(prescriptionList,this);
        mRecyclerView.setAdapter(adapter);

    }

    /**
     * pull prescription data from database to populate app list
     */
    public void refreshPrescriptionList() {
        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");
        result.child(patientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInfo = dataSnapshot;
                    Log.i("my tag", userInfo.getValue().toString());
                    ArrayList<String> prescriptionKeys;
                    prescriptionKeys = new ArrayList<String>();

                    // for each day in the prescriptions section of the user's data,
                    // get the prescription keys for the selected day
                    if(dataSnapshot.child("prescriptions").exists()){
                        for (DataSnapshot key : dataSnapshot.child("prescriptions").child(daySelection).getChildren()) {
                            prescriptionKeys.add(key.getKey());
                        }
                    }
                    //next, query the database with the keys you received.
                    populatePrescriptions(prescriptionKeys);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("my tag", "User data retrieval error");
            }
        });
    }

    /**
     * populate app list with pulled data
     * @param keys ArrayList<String> keys from database
     */
    public void populatePrescriptions(ArrayList<String> keys){
        prescriptionList.clear();
        adapter.notifyDataSetChanged();
        //for each key in keys, query the database
        for(String key : keys){
            FirebaseDatabase.getInstance().getReference("prescriptions")
                    .child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataIn) {

                    Prescription newEntry = new Prescription(
                            dataIn.child("content").getValue(String.class),
                            dataIn.child("timed").getValue(Boolean.class),
                            dataIn.child("times_per_day").getValue(Integer.class),
                            dataIn.child("time_between_dose").getValue(Integer.class),
                            dataIn.child("start_time").getValue(String.class)
                    );
                    newEntry.setId(dataIn.getKey());
                    prescriptionList.add(newEntry);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("my tag", "User data retrieval error");
                }
            });
        }
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu Menu inflated
     * @return true always
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    /**
     * finishes activity on back
     * @param back MenuItem back button
     */
    public void onBack(MenuItem back) {
        finish();
    }

    /**
     * go to add prescription activity with input data
     * @param view View current
     */
    public void launchAddPrescription(View view){
        Intent intent = new Intent (this, AddPrescriptionActivity.class);
        String name = mPatientName.getText().toString();
        intent.putExtra("name",name);
        intent.putExtra("user_ID", patientID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    /**
     * When the activity receives a result, handle receiving data
     * @param requestCode The integer to request data for
     * @param resultCode The integer of the type of resulting data
     * @param data The data
     */
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
                            patient.addPrescription(patientID,prescription,"Monday");
                            break;
                        case 1:
                            patient.addPrescription(patientID,prescription,"Tuesday");
                            break;
                        case 2:
                            patient.addPrescription(patientID,prescription,"Wednesday");
                            break;
                        case 3:
                            patient.addPrescription(patientID,prescription,"Thursday");
                            break;
                        case 4:
                            patient.addPrescription(patientID,prescription,"Friday");
                            break;
                        case 5:
                            patient.addPrescription(patientID,prescription,"Saturday");
                            break;
                        case 6:
                            patient.addPrescription(patientID,prescription,"Sunday");
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
                for(int i = 0; i < prescriptionList.size(); i++){
                    Log.i("return tag2", prescriptionList.get(i).getId());
                    if(prescriptionList.get(i).getId().equals(returnedID)){
                        changeIndex = i;
                    }
                }
                if(changeIndex == -1){ //if somehow not found
                    return;
                }else{// if found
                    if(data.hasExtra("schedule_type")){
                        prescriptionList.get(changeIndex).setTimed(data.getExtras().getBoolean("schedule_type"));
                        if(data.getExtras().getBoolean("schedule_type")){
                            prescriptionList.get(changeIndex).setStartTime(data.getExtras().getString("start_time"));
                        }else{
                            prescriptionList.get(changeIndex).setStartTime("");
                        }
                    }
                    if(data.hasExtra("times_per_day")){
                        prescriptionList.get(changeIndex).setTimesPerDay(data.getExtras().getInt("times_per_day"));
                    }
                    if(data.hasExtra("break_hours")){
                        prescriptionList.get(changeIndex).setTimeBetweenDose(data.getExtras().getInt("break_hours"));
                    }
                    if(data.hasExtra("description")){
                        prescriptionList.get(changeIndex).setContent(data.getExtras().getString("description"));
                    }
                }
                Prescription.updatePrescription(returnedID,
                        data.getExtras().getString("description"),
                        data.getExtras().getInt("times_per_day"),
                        data.getExtras().getInt("break_hours"),
                        data.getExtras().getString("start_time"),
                        data.getExtras().getBoolean("schedule_type"));
            }
            adapter.notifyItemChanged(changeIndex);
        }
    }

    /**
     * When an item on the recycler view for prescriptions is clicked
     * @param position Position in the recycler view of the clicked item
     */
    @Override
    public void recyclerViewListClicked(int position) {
        Intent intent = new Intent(this, EditPrescriptionActivity.class);
        String name = mPatientName.getText().toString();
        intent.putExtra("name",name);
        intent.putExtra("user_ID", patientID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        intent.putExtra("prescription_ID", prescriptionList.get(position).getId());

        intent.putExtra("schedule_type", prescriptionList.get(position).isTimed());
        intent.putExtra("start_time", prescriptionList.get(position).getStartTime());
        intent.putExtra("times_per_day", prescriptionList.get(position).getTimesPerDay());
        intent.putExtra("break_hours", prescriptionList.get(position).getTimeBetweenDose());
        intent.putExtra("description", prescriptionList.get(position).getContent());
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

    /**
     * When clicked the Monday button
     * @param v The view passed to the prescription
     */
    public void mondayButton(View v){
        daySelection = "Monday";
        refreshPrescriptionList();
    }

    /**
     * When clicked the Tuesday button
     * @param v The view passed to the prescription
     */
    public void tuesdayButton(View v){
        daySelection = "Tuesday";
        refreshPrescriptionList();
    }

    /**
     * When clicked the Wednesday button
     * @param v The view passed to the prescription
     */
    public void wednesdayButton(View v){
        daySelection = "Wednesday";
        refreshPrescriptionList();
    }

    /**
     * When clicked the Thursday button
     * @param v The view passed to the prescription
     */
    public void thursdayButton(View v){
        daySelection = "Thursday";
        refreshPrescriptionList();
    }

    /**
     * When clicked the Friday button
     * @param v The view passed to the prescription
     */
    public void fridayButton(View v){
        daySelection = "Friday";
        refreshPrescriptionList();
    }

    /**
     * When clicked the Saturday button
     * @param v The view passed to the prescription
     */
    public void saturdayButton(View v){
        daySelection = "Saturday";
        refreshPrescriptionList();
    }

    /**
     * When clicked the Sunday button
     * @param v The view passed to the prescription
     */
    public void sundayButton(View v){
        daySelection = "Sunday";
        refreshPrescriptionList();
    }
}
