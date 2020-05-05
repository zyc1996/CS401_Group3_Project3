package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: A class for tracking prescription data. Class objects can be added to patient accounts tied to doctors.
 */
public class Prescription {
    /**
     * String variables for prescription id, description of prescription, and start time of taking
     */
    private String id;
    private String content;
    private String startTime;
    /**
     * true means that prescription is taken at a specific time
     * false means at any time that day (or with meals, etc)
     */
    private boolean timed;
    /**
     * how many times prescription should repeat itself
     * <= 0 treated as once.
     */
    private int timesPerDay;
    /**
     * how long between each dose, if timed and timesPerDay > 1
     * automatically repeat prescription in schedule after this much time
     */
    private int timeBetweenDose;
    /**
     * Date variable for creation date
     */
    private Date createdAt;
    /**
     * Database connectivity variable
     */
    private DatabaseReference mDatabase;

    /**
     * Default constructor for Prescription
     */
    public Prescription() {
        content = "";
        timed = false;
        startTime = "";
        timesPerDay = 0;
        timeBetweenDose = 0; //Should this mean minutes?
        createdAt = new Date();
        createdAt.getTime();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for Prescription with description, whether or not timed, times per day, time between use, and start time
     * @param contentIn String content of prescription
     * @param timedIn Boolean whether or not it is timed
     * @param timesIn Integer times per day
     * @param timeBetweenIn Integer time between use
     * @param startTime String Start time of prescription
     */
    public Prescription(String contentIn, boolean timedIn, int timesIn
            , int timeBetweenIn, String startTime) {
        content = contentIn;
        timed = timedIn;
        this.startTime = startTime;
        timesPerDay = timesIn;
        timeBetweenDose = timeBetweenIn; //Should this mean minutes?
        createdAt = new Date();
        createdAt.getTime();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Registers a new prescription in the database
     * Connects to Firebase database and sends class object of new prescription entry
     * @return true if new prescription registered
     */
    public boolean register(){
        class Entry{
            public String content;
            public boolean timed;
            public int times_per_day;
            public int time_between_dose;
            public String start_time;
            public Date created_at;
        }

        Entry newEntry = new Entry();
        newEntry.content = this.content;
        newEntry.timed = this.timed;
        newEntry.start_time = this.startTime;
        newEntry.times_per_day = this.timesPerDay;
        newEntry.time_between_dose = this.timeBetweenDose;
        newEntry.created_at = this.createdAt;

        DatabaseReference ref;
        ref = mDatabase.child("prescriptions").push();
        ref.setValue(newEntry);

        this.id = ref.getKey();

        return true;
    }

    /**
     * getter for start_time
     * @return String start time of prescription
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * setter for start_time
     * @param startTime Start time of prescription
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * getter for id
     * @return String id of prescription
     */
    public String getId() {
        return this.id;
    }

    /**
     * setter for id
     * @param id String id of prescription
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter for content
     * @return String description of prescription
     */
    public String getContent() {
        return this.content;
    }

    /**
     * setter for content
     * @param content String for prescription of prescription
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter for is_timed
     * @return Boolean if prescription is taken at a specific time
     */
    public boolean isTimed() {
        return this.timed;
    }

    /**
     * setter for is_timed
     * @param timed Boolean if prescription is taken at a specific time
     */
    public void setTimed(boolean timed) {
        this.timed = timed;
    }

    /**
     * getter for times_per_day
     * @return Integer times to take prescription per day
     */
    public int getTimesPerDay() {
        return this.timesPerDay;
    }

    /**
     * setter for timesPerDay
     * @param timesPerDay Integer as times to take prescription per day
     */
    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    /**
     * getter for time_between_dose
     * @return Integer time between prescription dose
     */
    public int getTimeBetweenDose() {
        return this.timeBetweenDose;
    }

    /**
     * setter for timeBetweenDose
     * @param timeBetweenDose Integer time between prescription dose
     */
    public void setTimeBetweenDose(int timeBetweenDose) {
        this.timeBetweenDose = timeBetweenDose;
    }

    /**
     * getter for created_at
     * @return String formatted date of creation
     */
    public String getCreatedAt() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(createdAt);
    }

    /**
     * Update prescription in database with parameters
     * @param prescriptionIdIn String id of prescrition
     * @param descriptionIn String description of prescription
     * @param timesPerDayIn Integer times taken per day
     * @param breakHours Integer hours between taking
     * @param startTimeIn String date of prescription start
     * @param scheduleIn Boolean if scheduled
     */
    public static void updatePrescription(String prescriptionIdIn,
                                          String descriptionIn, int timesPerDayIn,
                                          int breakHours,
                                          String startTimeIn, Boolean scheduleIn){

        //database update
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("prescriptions");
        ref.child(prescriptionIdIn).child("content").setValue(descriptionIn);
        ref.child(prescriptionIdIn).child("start_time").setValue(startTimeIn);
        ref.child(prescriptionIdIn).child("time_between_dose").setValue(breakHours);
        ref.child(prescriptionIdIn).child("timed").setValue(scheduleIn);
        ref.child(prescriptionIdIn).child("times_per_day").setValue(timesPerDayIn);

    }
}
