package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescription {
    private String id;
    private String content;
    private boolean timed; // true means that prescription is taken at a specific time
                   // false means at any time that day (or with meals, etc)
    private int times_per_day; // how many times prescription should repeat itself
                       // <= 0 treated as once.
    private int time_between_dose; // how long between each dose, if timed and times_per_day > 1
                            // automatically repeat prescription in schedule after this much time
    private Date created_at;
    private DatabaseReference mDatabase;

    public Prescription() {
        content = "";
        timed = false;
        times_per_day = 0;
        time_between_dose = 0; //Should this mean minutes?
        created_at = new Date();
        created_at.getTime();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Prescription(String content_in, boolean timed_in, int times_in
            , int time_between_in) {
        content = content_in;
        timed = timed_in;
        times_per_day = times_in;
        time_between_dose = time_between_in; //Should this mean minutes?
        created_at = new Date();
        created_at.getTime();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void by_doctor(int id_in, String name_in){
        //does doctor field exist in database object?
    }

    public boolean register(){
        class Entry{
            public String content;
            public boolean timed;
            public int times_per_day;
            public int time_between_dose;
            public Date created_at;
        }

        Entry new_entry = new Entry();
        new_entry.content = this.content;
        new_entry.timed = this.timed;
        new_entry.times_per_day = this.times_per_day;
        new_entry.time_between_dose = this.time_between_dose;
        new_entry.created_at = this.created_at;

        DatabaseReference ref;
        ref = mDatabase.child("prescriptions").push();
        ref.setValue(new_entry);

        this.id = ref.getKey();

        return true;
    }

    public String get_id() {
        return this.id;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public String get_content() {
        return this.content;
    }

    public void set_content(String content) {
        this.content = content;
    }

    public boolean is_timed() {
        return this.timed;
    }

    public void set_timed(boolean timed) {
        this.timed = timed;
    }

    public int get_times_per_day() {
        return this.times_per_day;
    }

    public void set_times_per_day(int times_per_day) {
        this.times_per_day = times_per_day;
    }

    public int get_time_between_dose() {
        return this.time_between_dose;
    }

    public void set_time_between_dose(int time_between_dose) {
        this.time_between_dose = time_between_dose;
    }

    public String get_created_at() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(created_at);
    }
}
