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
    private String start_time;
    /**
     * true means that prescription is taken at a specific time
     * false means at any time that day (or with meals, etc)
     */
    private boolean timed;
    /**
     * how many times prescription should repeat itself
     * <= 0 treated as once.
     */
    private int times_per_day;
    /**
     * how long between each dose, if timed and times_per_day > 1
     * automatically repeat prescription in schedule after this much time
     */
    private int time_between_dose;
    /**
     * Date variable for creation date
     */
    private Date created_at;
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
        start_time = "";
        times_per_day = 0;
        time_between_dose = 0; //Should this mean minutes?
        created_at = new Date();
        created_at.getTime();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for Prescription with description, whether or not timed, times per day, time between use, and start time
     * @param content_in String content of prescription
     * @param timed_in Boolean whether or not it is timed
     * @param times_in Integer times per day
     * @param time_between_in Integer time between use
     * @param start_Time String Start time of prescription
     */
    public Prescription(String content_in, boolean timed_in, int times_in
            , int time_between_in, String start_Time) {
        content = content_in;
        timed = timed_in;
        start_time = start_Time;
        times_per_day = times_in;
        time_between_dose = time_between_in; //Should this mean minutes?
        created_at = new Date();
        created_at.getTime();

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

        Entry new_entry = new Entry();
        new_entry.content = this.content;
        new_entry.timed = this.timed;
        new_entry.start_time = this.start_time;
        new_entry.times_per_day = this.times_per_day;
        new_entry.time_between_dose = this.time_between_dose;
        new_entry.created_at = this.created_at;

        DatabaseReference ref;
        ref = mDatabase.child("prescriptions").push();
        ref.setValue(new_entry);

        this.id = ref.getKey();

        return true;
    }

    /**
     * getter for start_time
     * @return String start time of prescription
     */
    public String get_Start_time() {
        return start_time;
    }

    /**
     * setter for start_time
     * @param String start time of prescription
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     * getter for id
     * @return String id of prescription
     */
    public String get_id() {
        return this.id;
    }

    /**
     * setter for id
     * @param String id of prescription
     */
    public void set_id(String id) {
        this.id = id;
    }

    /**
     * getter for content
     * @return String description of prescription
     */
    public String get_content() {
        return this.content;
    }

    /**
     * setter for content
     * @param String prescription of prescription
     */
    public void set_content(String content) {
        this.content = content;
    }

    /**
     * getter for is_timed
     * @return Boolean if prescription is taken at a specific time
     */
    public boolean is_timed() {
        return this.timed;
    }

    /**
     * setter for is_timed
     * @param Boolean if prescription is taken at a specific time
     */
    public void set_timed(boolean timed) {
        this.timed = timed;
    }

    /**
     * getter for times_per_day
     * @return Integer times to take prescription per day
     */
    public int get_times_per_day() {
        return this.times_per_day;
    }

    /**
     * setter for times_per_day
     * @param Integer times to take prescription per day
     */
    public void set_times_per_day(int times_per_day) {
        this.times_per_day = times_per_day;
    }

    /**
     * getter for time_between_dose
     * @return Integer time between prescription dose
     */
    public int get_time_between_dose() {
        return this.time_between_dose;
    }

    /**
     * setter for time_between_dose
     * @param Integer time between prescription dose
     */
    public void set_time_between_dose(int time_between_dose) {
        this.time_between_dose = time_between_dose;
    }

    /**
     * getter for created_at
     * @return String formatted date of creation
     */
    public String get_created_at() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(created_at);
    }
}
