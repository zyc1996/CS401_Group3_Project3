package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Doctor;

public class HomepageDoctorActivity extends AppCompatActivity {

    private String userID;
    private int ACCOUNT_TYPE;
    private TextView mUserName;
    private DataSnapshot user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_doctor);

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

    public void launchDoctorProfile(View view){
        Intent intent = new Intent(this,DoctorProfileActivity.class);
        intent.putExtra("doctor_ID",userID);
        startActivity(intent);
    }

    //needs database to find patient
    //click on patient to add stuff
}
