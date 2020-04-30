package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class Patient extends User {
    private int patient_id;
    private ArrayList<Prescription>[] schedule;

    public Patient() {
        this.patient_id = -1;
        this.schedule = new ArrayList[7];
    }

    public Patient(int id_in, String name_in, String picture_in, String description_in) {
        this.patient_id = id_in;
        this.username = name_in;
        this.picture_url = picture_in;
        this.personal_description = description_in;
        this.schedule = new ArrayList[7];
    }

    public int get_patient_id() {
        return patient_id;
    }

    public void set_patient_id(int id_in) {
        this.patient_id = id_in;
    }

}
