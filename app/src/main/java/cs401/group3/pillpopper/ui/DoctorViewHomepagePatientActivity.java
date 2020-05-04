package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: Activity for a doctor to view a patient and their information
 */
public class DoctorViewHomepagePatientActivity extends AppCompatActivity implements PrescriptionAdapter.RecyclerViewClickListener{

    private String patientID, patientName;
    private final int ACCOUNT_TYPE = 1;
    private Patient patient;
    private int REQUEST_CODE_ADD = 2;
    private int REQUEST_CODE_EDIT = 3;
    private DataSnapshot user_info;

    private TextView mPatientName;

    //recyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    //dummy data (local)
    private List<Prescription> prescription = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient_doctor_view);

//        // Find the toolbar view inside the activity layout
//        Toolbar toolbar = (Toolbar) findViewById(R.id.homepagePatientToolbar);
//        // Sets the Toolbar to act as the ActionBar for this Activity window.
//        setSupportActionBar(toolbar);


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

        //set recyclerview bounds
        mRecyclerView = findViewById(R.id.prescription_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //create dummy prescription if there isn't any
        if(prescription.isEmpty()){
            Random random = new Random();
            for(int i = 0; i < 10; i++){
                //dummy data

                String content = "Dummy prescription " + (i+1);
                boolean timed;
                if(i%2 == 0){
                    timed = true;
                }else{
                    timed = false;
                }
                int times_in = random.nextInt(9)+1;
                int time_between = random.nextInt(3)+1;
                String start_time = "";
                if(timed) {
                    start_time = (random.nextInt(11)+1) + ":00 pm";
                }
                Prescription p = new Prescription(content,timed,times_in,time_between,start_time);
                p.set_id("Position" + i);
                prescription.add(p);
            }
        }

        adapter = new PrescriptionAdapter(prescription,this);
        mRecyclerView.setAdapter(adapter);

    }


//    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_back, menu);
//        return true;
//    }


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
                for(int i = 0; i < prescription.size(); i++){
                    Log.i("return tag2", prescription.get(i).get_id());
                    if(prescription.get(i).get_id().equals(returnedID)){
                        changeIndex = i;
                    }
                }
                if(changeIndex == -1){ //if somehow not found
                    return;
                }else{// if found
                    if(data.hasExtra("schedule_type")){
                        prescription.get(changeIndex).set_timed(data.getExtras().getBoolean("schedule_type"));
                        if(data.getExtras().getBoolean("schedule_type")){
                            prescription.get(changeIndex).setStart_time(data.getExtras().getString("start_time"));
                        }else{
                            prescription.get(changeIndex).setStart_time("");
                        }
                    }
                    if(data.hasExtra("times_per_day")){
                        prescription.get(changeIndex).set_times_per_day(data.getExtras().getInt("times_per_day"));
                    }
                    if(data.hasExtra("break_hours")){
                        prescription.get(changeIndex).set_time_between_dose(data.getExtras().getInt("break_hours"));
                    }
                    if(data.hasExtra("description")){
                        prescription.get(changeIndex).set_content(data.getExtras().getString("description"));
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
        intent.putExtra("prescription_ID",prescription.get(position).get_id());

        intent.putExtra("schedule_type",prescription.get(position).is_timed());
        intent.putExtra("start_time",prescription.get(position).get_Start_time());
        intent.putExtra("times_per_day",prescription.get(position).get_times_per_day());
        intent.putExtra("break_hours",prescription.get(position).get_time_between_dose());
        intent.putExtra("description",prescription.get(position).get_content());
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

}
