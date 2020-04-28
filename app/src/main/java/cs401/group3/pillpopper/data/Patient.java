package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class Patient extends User {

    private Integer patient_ID;

    public Patient() {
        patient_ID = 0;
        name = "";
        picture = "";
        personal_description = "";
        list_of_prescription = new ArrayList<>();
        list_of_messages = new ArrayList<>();
    }

    public Patient(Integer ID, String name, String picture, String description, ArrayList<Prescription> list_of_prescription, ArrayList<Message> list_of_messages) {
        this.patient_ID = ID;
        this.name = name;
        this.picture = picture;
        this.personal_description = description;
        this.list_of_prescription = list_of_prescription;
        this.list_of_messages = list_of_messages;
    }

    public void Prescription_adjustment() {}

    public void Prescription_request_delete() {}

    public Integer getPatient_ID() { return patient_ID; }

    public void setPatient_ID(Integer ID) { this.patient_ID = ID; }

}
