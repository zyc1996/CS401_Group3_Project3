package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class Doctor extends User {

    private Integer doctor_ID;
    private ArrayList<Patient> list_of_patient;

    public Doctor() {
        doctor_ID = 0;
        name = "";
        picture = "";
        personal_description = "";
        list_of_patient = new ArrayList<>();
    }

    public Doctor(Integer ID, String name, String picture, String description, ArrayList<Patient> list_of_patient, ArrayList<Prescription> list_of_prescription, ArrayList<Message> list_of_messages) {
        this.doctor_ID = ID;
        this.name = name;
        this.picture = picture;
        this.personal_description = description;
        this.list_of_patient = list_of_patient;
        this.list_of_prescription = list_of_prescription;
        this.list_of_messages = list_of_messages;
    }

    public void Patient_add() { }

    public void Patient_edit() { }

    public void Patient_remove() { }

    public void Description_edit() { }

    public void Prescription_create() { }

    public void Prescription_edit() { }

    public void Prescription_delete() { }

    public ArrayList<Patient> getList_of_patient() {
        return list_of_patient;
    }

    public void setList_of_patient(ArrayList<Patient> list_of_patient) {
        this.list_of_patient = list_of_patient;
    }

    public Integer getDoctor_ID() { return doctor_ID; }

    public void setDoctor_ID(Integer ID) { this.doctor_ID = ID; }

}
