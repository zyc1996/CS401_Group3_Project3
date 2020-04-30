package cs401.group3.pillpopper.data;

import java.util.Date;

public class Prescription {
    private int id;
    private String content;
    private boolean timed; // true means that prescription is taken at a specific time
                   // false means at any time that day (or with meals, etc)
    private int times_per_day; // how many times prescription should repeat itself
                       // <= 0 treated as once.
    private int time_between_dose; // how long between each dose, if timed and times_per_day > 1
                            // automatically repeat prescription in schedule after this much time
    private boolean by_patient; // did the patient write this prescription themselves?

    private String doctor_name; // if by_patient == false, save doctor's name & id with the prescription
    private int doctor_id;
    private Date created_at;

    public Prescription() {
        content = "";
        timed = false;
        times_per_day = 0;
        time_between_dose = 0; //Should this mean minutes?
        by_patient = true;
        created_at = new Date();
        created_at.getTime();
    }

    public Prescription(String content_in, boolean timed_in, int times_in
            , int time_between_in) {
        content = content_in;
        timed = timed_in;
        times_per_day = times_in;
        time_between_dose = time_between_in; //Should this mean minutes?
        by_patient = true;
        created_at = new Date();
        created_at.getTime();
    }

    public void by_doctor(int id_in, String name_in){
        by_patient = false;
        doctor_id = id_in;
        doctor_name = name_in;
    }


    public int get_id() {
        return this.id;
    }

    public void set_id(int id) {
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

    public boolean is_by_patient() {
        return this.by_patient;
    }

    public void set_by_patient(boolean by_patient) {
        this.by_patient = by_patient;
    }

    public String get_doctor_name() {
        return this.doctor_name;
    }

    public void set_doctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int get_doctor_id() {
        return this.doctor_id;
    }

    public void set_doctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Date get_created_at() {
        return this.created_at;
    }
}
