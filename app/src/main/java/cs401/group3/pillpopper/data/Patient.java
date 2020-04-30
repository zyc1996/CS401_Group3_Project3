package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class Patient extends User {
    private int patient_id;
    private ArrayList<Prescription>[] schedule;

    public Patient() {
        this.patient_id = -1;
        this.schedule = new ArrayList[7]; //mon, tues, wed, thurs, fri, sat, sun
                                          //0,    1,    2,    3,    4,   5,   6
        for(int i = 0; i < 7; i++){
            this.schedule[i] = new ArrayList<Prescription>();
        }
    }

    public Patient(int id_in, String name_in, String picture_in) {
        this.patient_id = id_in;
        this.user_name = name_in;
        this.picture_url = picture_in;
        this.schedule = new ArrayList[7]; //mon, tues, wed, thurs, fri, sat, sun
                                          //0,    1,    2,    3,    4,   5,   6
        for(int i = 0; i < 7; i++){
            this.schedule[i] = new ArrayList<Prescription>();
        }
    }

    public int get_patient_id() {
        return patient_id;
    }

    public void set_patient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void add_prescription(Prescription new_prescription, int day){
        if (day >=0 && day <= 6){
            this.schedule[day].add(new_prescription);
        }
    }
    public void remove_prescription(int prescription_id, int day){
        if (day >=0 && day <= 6){
            for(int i = 0; i < this.schedule[day].size(); i++){
                if(this.schedule[day].get(i).get_id() == prescription_id){
                    this.schedule[day].remove(i);
                    return;
                }
            }
        }
    }

    public ArrayList<Prescription> get_schedule(int day) {
        return schedule[day];
    }
}
