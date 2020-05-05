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
 * Purpose: The patient homepage activity for after a patient signs in
 */
public class HomepagePatientActivity extends AppCompatActivity implements PrescriptionAdapter.RecyclerViewClickListener{

    /**
     * The string of the userID
     */
    private String userID;

    /**
     * The int to represent the account type. 1 = Patient, 2 = Doctor
     */
    private int ACCOUNT_TYPE;

    /**
     * patient object for adding
     */
    private Patient patient;

    /**
     * The request code to add a prescription
     */
    private int REQUEST_CODE_ADD = 2;

    /**
     * The request code to edit a prescription
     */
    private int REQUEST_CODE_EDIT = 3;

    /**
     * The data snapshot of the user info
     */
    private DataSnapshot userInfo;

    /**
     * The string to select a day
     */
    private String daySelection;

    /**
     * The TextView widget of the user name
     */
    private TextView mUserName;

    /**
     * The RecyclerView of the prescriptions
     */
    private RecyclerView mRecyclerView;

    /**
     * The adapter for the recycler view
     */
    private RecyclerView.Adapter adapter;

    /**
     * The list of prescriptions
     */
    private List<Prescription> prescriptionList = new ArrayList<>();

    /**
     * On creation of activity initializes patient homepage activity
     * @param savedInstanceState Bundle for saving instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.homepage_patient_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mUserName = findViewById(R.id.user_name_title);

        Intent intent = getIntent();

        if (intent.getExtras() == null) {
            return;
        }

        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");


        mRecyclerView = findViewById(R.id.prescription_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriptionAdapter(prescriptionList,this);
        mRecyclerView.setAdapter(adapter);

        daySelection = "Monday";
        refreshPrescriptionList();

    }

    /**
     * pull prescription list data from Firebase
     */
    public void refreshPrescriptionList(){
        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");
        result.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInfo = dataSnapshot;
                    mUserName.setText(dataSnapshot.child("user_name").getValue(String.class));
                    Log.i("my tag", userInfo.getValue().toString());
                    ArrayList<String> prescriptionKeys;
                    prescriptionKeys = new ArrayList<String>();

                    // for each day in the prescriptions section of the user's data,
                    // get the prescription keys for the selected day
                    for(DataSnapshot key : dataSnapshot.child("prescriptions").child(daySelection).getChildren()){
                        prescriptionKeys.add(key.getKey());
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
     * populate prescription list with data pulled from Firebase
     * @param keys ArrayList<String> of key data from database
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
     * Menu icons are inflated just as they were with actionbar
     * @param menu Menu item inflated
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    /**
     * Launch patient profile
     * @param profile MenuItem for patient profile
     */
    public void launchProfile(MenuItem profile){
        Intent intent = new Intent(this,PatientProfileActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivity(intent);
    }

    /**
     * finish activity when patient logs out
     * @param logout MenuItem logout button
     */
    public void onLogout(MenuItem logout) {
        finish();
    }

    /**
     * Launch add prescription with input data
     * @param view View current
     */
    public void launchAddPrescription(View view){
        Intent intent = new Intent (this, AddPrescriptionActivity.class);
        String name = mUserName.getText().toString();
        intent.putExtra("name",name);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    /**
     * Load all the data onto the database with selected choices
     * @param requestCode Integer code requested
     * @param resultCode Integer resulting code
     * @param data resulting data
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
                            patient.addPrescription(userID,prescription,"Monday");
                            break;
                        case 1:
                            patient.addPrescription(userID,prescription,"Tuesday");
                            break;
                        case 2:
                            patient.addPrescription(userID,prescription,"Wednesday");
                            break;
                        case 3:
                            patient.addPrescription(userID,prescription,"Thursday");
                            break;
                        case 4:
                            patient.addPrescription(userID,prescription,"Friday");
                            break;
                        case 5:
                            patient.addPrescription(userID,prescription,"Saturday");
                            break;
                        case 6:
                            patient.addPrescription(userID,prescription,"Sunday");
                            break;
                        default:
                            break;
                    }
                }
            }

        }
        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){
            int changeIndex = -1;

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
     * for clicking list tracked by position
     * @param position Integer position clicked
     */
    @Override
    public void recyclerViewListClicked(int position) {
        Intent intent = new Intent(this, EditPrescriptionActivity.class);
        String name = mUserName.getText().toString();
        intent.putExtra("name",name);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        intent.putExtra("prescription_ID", prescriptionList.get(position).getId());

        Log.i("test tag", prescriptionList.get(position).getId());

        intent.putExtra("schedule_type", prescriptionList.get(position).isTimed());
        intent.putExtra("start_time", prescriptionList.get(position).getStartTime());
        intent.putExtra("times_per_day", prescriptionList.get(position).getTimesPerDay());
        intent.putExtra("break_hours", prescriptionList.get(position).getTimeBetweenDose());
        intent.putExtra("description", prescriptionList.get(position).getContent());
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

    /**
     * Select monday and refresh prescription list with day selection
     * @param v View current
     */
    public void mondayButton(View v){
        daySelection = "Monday";
        refreshPrescriptionList();
    }

    /**
     * Select tuesday and refresh prescription list with day selection
     * @param v View current
     */
    public void tuesdayButton(View v){
        daySelection = "Tuesday";
        refreshPrescriptionList();
    }

    /**
     * Select wednesday and refresh prescription list with day selection
     * @param v View current
     */
    public void wednesdayButton(View v){
        daySelection = "Wednesday";
        refreshPrescriptionList();
    }

    /**
     * Select thursday and refresh prescription list with day selection
     * @param v View current
     */
    public void thursdayButton(View v){
        daySelection = "Thursday";
        refreshPrescriptionList();
    }

    /**
     * Select friday and refresh prescription list with day selection
     * @param v View current
     */
    public void fridayButton(View v){
        daySelection = "Friday";
        refreshPrescriptionList();
    }

    /**
     * Select saturday and refresh prescription list with day selection
     * @param v View current
     */
    public void saturdayButton(View v){
        daySelection = "Saturday";
        refreshPrescriptionList();
    }

    /**
     * Select sunday and refresh prescription list with day selection
     * @param v View current
     */
    public void sundayButton(View v){
        daySelection = "Sunday";
        refreshPrescriptionList();
    }
}
