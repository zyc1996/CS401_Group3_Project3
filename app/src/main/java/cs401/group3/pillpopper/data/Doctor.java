package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class Doctor extends User {

    private int doctor_id;
     private ArrayList<Integer> patient_id;
     //private ArrayList<String> patient_name;
     //private ArrayList<String> patient_pic;

     public Doctor(){
         patient_id = new ArrayList<Integer>();
         //patient_name = new ArrayList<String>();
         //patient_pic = new ArrayList<String>();
    }

    public int get_doctor_id() {
        return doctor_id;
    }

    public void set_doctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public boolean add_patient(int patient_id_in){
         //check in database if patient exists
        //if exists
        /*patient_name_in & patient_pic_in from database
        //this.patient_id.add(patient_id_in);
        //this.patient_name.add(patient_name_in); //get from database
        //this.patient_pic.add(patient_pic_in); //get from database
        */
        return true;

        //if does not exist
        //return false;
    }

    public boolean remove_patient(int patient_id_in){
         for(int i = 0; i < this.patient_id.size(); i++){
             if (patient_id_in == this.patient_id.get(i)){
                 this.patient_id.remove(i);
                 //this.patient_name.remove(i);
                 //this.patient_pic.remove(i);
                 return true;
             }
         }
         return false;
    }
}
