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

    private String userID;
    private int ACCOUNT_TYPE;
    private Patient patient;
    private int REQUEST_CODE_ADD = 2;
    private int REQUEST_CODE_EDIT = 3;
    private DataSnapshot user_info;
    private String day_selection;

    private TextView mUserName;

    //recyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    private List<Prescription> prescription_list= new ArrayList<>();

    /**
     * On creation of activity initializes patient homepage activity
     * @param savedInstanceState Bundle for saving instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.homepagePatientToolbar);
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
        adapter = new PrescriptionAdapter(prescription_list,this);
        mRecyclerView.setAdapter(adapter);

        day_selection = "Monday";
        refresh_prescription_list();

    }

    /**
     * pull prescription list data from Firebase
     */
    public void refresh_prescription_list(){
        DatabaseReference result;
        result = FirebaseDatabase.getInstance().getReference("patients");
        result.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user_info = dataSnapshot;
                    mUserName.setText(dataSnapshot.child("user_name").getValue(String.class));
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
    }

    /**
     * populate prescription list with data pulled from Firebase
     * @param keys ArrayList<String> of key data from database
     */
    public void populate_prescriptions(ArrayList<String> keys){
        prescription_list.clear();
        adapter.notifyDataSetChanged();
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
     * launch messages menu with menu item
     * @param messages MenuItem for messages
     */
    public void launchMessages(MenuItem messages) {
        Intent intent = new Intent(this, MessagesActivity.class);
        intent.putExtra("user_ID",userID);
        intent.putExtra("account_type",ACCOUNT_TYPE);
        startActivity(intent);
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
                            patient.add_prescription(userID,prescription,"Monday");
                            break;
                        case 1:
                            patient.add_prescription(userID,prescription,"Tuesday");
                            break;
                        case 2:
                            patient.add_prescription(userID,prescription,"Wednesday");
                            break;
                        case 3:
                            patient.add_prescription(userID,prescription,"Thursday");
                            break;
                        case 4:
                            patient.add_prescription(userID,prescription,"Friday");
                            break;
                        case 5:
                            patient.add_prescription(userID,prescription,"Saturday");
                            break;
                        case 6:
                            patient.add_prescription(userID,prescription,"Sunday");
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
                Prescription.update_prescription(returnedID,
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
        intent.putExtra("prescription_ID",prescription_list.get(position).get_id());

        Log.i("test tag", prescription_list.get(position).get_id());

        intent.putExtra("schedule_type",prescription_list.get(position).is_timed());
        intent.putExtra("start_time",prescription_list.get(position).get_Start_time());
        intent.putExtra("times_per_day",prescription_list.get(position).get_times_per_day());
        intent.putExtra("break_hours",prescription_list.get(position).get_time_between_dose());
        intent.putExtra("description",prescription_list.get(position).get_content());
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

    /**
     * Select monday and refresh prescription list with day selection
     * @param v View current
     */
    public void mondayButton(View v){
        day_selection = "Monday";
        refresh_prescription_list();
    }

    /**
     * Select tuesday and refresh prescription list with day selection
     * @param v View current
     */
    public void tuesdayButton(View v){
        day_selection = "Tuesday";
        refresh_prescription_list();
    }

    /**
     * Select wednesday and refresh prescription list with day selection
     * @param v View current
     */
    public void wednesdayButton(View v){
        day_selection = "Wednesday";
        refresh_prescription_list();
    }

    /**
     * Select thursday and refresh prescription list with day selection
     * @param v View current
     */
    public void thursdayButton(View v){
        day_selection = "Thursday";
        refresh_prescription_list();
    }

    /**
     * Select friday and refresh prescription list with day selection
     * @param v View current
     */
    public void fridayButton(View v){
        day_selection = "Friday";
        refresh_prescription_list();
    }

    /**
     * Select saturday and refresh prescription list with day selection
     * @param v View current
     */
    public void saturdayButton(View v){
        day_selection = "Saturday";
        refresh_prescription_list();
    }

    /**
     * Select sunday and refresh prescription list with day selection
     * @param v View current
     */
    public void sundayButton(View v){
        day_selection = "Sunday";
        refresh_prescription_list();
    }
}
