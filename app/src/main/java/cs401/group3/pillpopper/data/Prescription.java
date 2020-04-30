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
        created_at.getTime();
    }

    public Prescription(String content_in, boolean timed_in, int times_in
            , int time_between_in) {
        content = content_in;
        timed = timed_in;
        times_per_day = times_in;
        time_between_dose = time_between_in; //Should this mean minutes?
        by_patient = true;
        created_at.getTime();
    }

    public void by_doctor(int id_in, String name_in){
        by_patient = false;
        doctor_id = id_in;
        doctor_name = name_in;
    }
}
