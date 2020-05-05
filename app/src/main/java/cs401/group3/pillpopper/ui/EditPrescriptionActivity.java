package cs401.group3.pillpopper.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import cs401.group3.pillpopper.R;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The prescription editing activity for changing prescription information, and use data for patients
 */
public class EditPrescriptionActivity extends AppCompatActivity {

    private String userID;
    private int ACCOUNT_TYPE;
    private String prescriptionID;

    private TextView mName;
    private RadioGroup mScheduleType;
    private RadioButton mSelected,mTimed,mUntimed;
    private EditText mStartTime, mTimesPerDay, mBreakHours,mDescription;
    Calendar calendar;
    int hour, minute;

    private boolean days[] = new boolean[]{false,false,false,false,false,false,false};
    private boolean scheduleType = false; //false = untimed, true = timed

    /**
     * On creation of activity initializes edit of presciption data
     * @param savedInstanceState Bundle for saving instance of activity
     */
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

    /**
     * check button for timed or untimed prescription
     * @param view
     */
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

    /**
     * Submit prescription to database with input information
     * @param view current view
     */
    public void submitPrescription(View view){

        //if it is timed, returns the starting time
        String startTime = "", description="";
        int timesPerDay = -1, breakHours = -1;
        if(scheduleType){
            if(!mStartTime.getText().toString().isEmpty()) {
                startTime = mStartTime.getText().toString();
            }
        }
        if(!mTimesPerDay.getText().toString().isEmpty()) {
            timesPerDay = Integer.parseInt(mTimesPerDay.getText().toString());
        }
        if(!mBreakHours.getText().toString().isEmpty()){
            breakHours = Integer.parseInt(mBreakHours.getText().toString());
        }
        if(!mDescription.getText().toString().isEmpty()) {
            description = mDescription.getText().toString();
        }

        if( description.isEmpty() || timesPerDay < 0 || breakHours < 0 || (startTime.isEmpty() && scheduleType)){
            Toast.makeText(EditPrescriptionActivity.this, "Invalid variable field(s), creation aborts", Toast.LENGTH_LONG).show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 5000);
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra("schedule_type",scheduleType);
        //if it is timed
        if(scheduleType){
            replyIntent.putExtra("start_time",startTime);
        }else{
            replyIntent.putExtra("start_time","");
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
