package cs401.group3.pillpopper.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import cs401.group3.pillpopper.R;

public class EditPrescriptionActivity extends AppCompatActivity {

    private String userID;
    private int ACCOUNT_TYPE;
    private String prescriptionID;

    private TextView mName;
    private CheckBox mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday, mSunday;
    private RadioGroup mScheduleType;
    private RadioButton mSelected,mTimed,mUntimed;
    private EditText mStartTime, mTimesPerDay, mBreakHours,mDescription;
    Calendar calendar;
    int hour, minute;

    private boolean days[] = new boolean[]{false,false,false,false,false,false,false};
    private boolean scheduleType = false; //false = untimed, true = timed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prescription);

        Intent intent = getIntent();
        if(intent.getExtras() == null){
            return;
        }

        String name = intent.getExtras().getString("name");
        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");
        prescriptionID = intent.getExtras().getString("prescription_ID");


        mName = findViewById(R.id.user_name);
        mName.setText(name);

        mMonday = findViewById(R.id.monday_checkbox);
        mMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMonday.isChecked()){
                    days[0] = true;
                }
                else{
                    days[0] = false;
                }
            }
        });
        mTuesday = findViewById(R.id.tuesday_checkbox);
        mTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTuesday.isChecked()){
                    days[1] = true;
                }
                else{
                    days[1] = false;
                }
            }
        });
        mWednesday = findViewById(R.id.wednesday_checkbox);
        mWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWednesday.isChecked()){
                    days[2] = true;
                }
                else{
                    days[2] = false;
                }
            }
        });
        mThursday = findViewById(R.id.thursday_checkbox);
        mThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mThursday.isChecked()){
                    days[3] = true;
                }
                else{
                    days[3] = false;
                }
            }
        });
        mFriday = findViewById(R.id.friday_checkbox);
        mFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFriday.isChecked()){
                    days[4] = true;
                }
                else{
                    days[4] = false;
                }
            }
        });
        mSaturday = findViewById(R.id.saturday_checkbox);
        mSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSaturday.isChecked()){
                    days[5] = true;
                }
                else{
                    days[5] = false;
                }
            }
        });
        mSunday = findViewById(R.id.sunday_checkbox);
        mSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSunday.isChecked()){
                    days[6] = true;
                }
                else{
                    days[6] = false;
                }
            }
        });

        mScheduleType = findViewById(R.id.schedule_type);
        mTimed = findViewById(R.id.timed_radio_button);
        mUntimed = findViewById(R.id.untimed_radio_button);
        boolean isTimed = intent.getExtras().getBoolean("schedule_type");
        if(isTimed){
            mTimed.setChecked(true);
        }else{
            mUntimed.setChecked(true);
        }


        mStartTime = findViewById(R.id.dose_time_fill);
        String startTime = intent.getExtras().getString("start_time");
        mStartTime.setText(startTime);
        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditPrescriptionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mStartTime.setText(String.format("%02d:%02d",hourOfDay,minute));
                    }
                },hour,minute,true);

                timePickerDialog.show();
            }
        });

        mTimesPerDay = findViewById(R.id.times_taken);
        int timesPerDay = intent.getExtras().getInt("times_per_day");
        mTimesPerDay.setText(String.valueOf(timesPerDay));

        mBreakHours = findViewById(R.id.dosage_break_time);
        int breakHours = intent.getExtras().getInt("break_hours");
        mBreakHours.setText(String.valueOf(breakHours));

        mDescription = findViewById(R.id.prescription_description_fill);
        String description = intent.getExtras().getString("description");
        mDescription.setText(description);

    }

    public void checkButton(View view){
        int radioID = mScheduleType.getCheckedRadioButtonId();
        mSelected = findViewById(radioID);
        String type = mSelected.getText().toString();

        Log.i("my tag", type);

        if(type.equals("Timed")){
            scheduleType = true;
        }
        else if(type.equals("Untimed")){
            scheduleType = false;
        }
        else{
            Log.d("RadioTag", "Error happened when select schedule type");
        }
    }

    public void submitPrescription(View view){

        //if it is timed, returns the starting time
        String startTime = "", description;
        int timesPerDay, breakHours;
        if(scheduleType){
            startTime = mStartTime.getText().toString();
        }

        timesPerDay = Integer.parseInt(mTimesPerDay.getText().toString());
        breakHours = Integer.parseInt(mBreakHours.getText().toString());
        description = mDescription.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra("schedule_type",scheduleType);
        //if it is timed
        if(scheduleType){
            replyIntent.putExtra("start_time",startTime);
        }
        replyIntent.putExtra("times_per_day", timesPerDay);
        replyIntent.putExtra("break_hours", breakHours);
        replyIntent.putExtra("description",description);

        replyIntent.putExtra("user_ID",userID);
        replyIntent.putExtra("account_type",ACCOUNT_TYPE);
        replyIntent.putExtra("prescription_ID",prescriptionID);

        Log.i("my tag", replyIntent.getExtras().toString());

        setResult(RESULT_OK,replyIntent);
        finish();
    }
}