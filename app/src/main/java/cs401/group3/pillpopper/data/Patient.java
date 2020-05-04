package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Patient extends User {
    private String patient_id; //database key

    public Patient() {
        this.patient_id = "-1";
        user_name = "";
        email = "";
        password = "";
        created_at = new Date();
        accountType = 1;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Patient(String name_in, String email_in, String password_in) {
        this.user_name = name_in;
        this.email = email_in;
        this.password = password_in;
        created_at = new Date();
        accountType = 1;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public boolean register(){
        class Entry{
            public String user_name;
            public String email;
            public String password;
            public Date created_at;
            public String picture_url;
            public String personal_description;
        }
        Entry new_entry = new Entry();
        new_entry.user_name = this.user_name;
        new_entry.email = this.email;
        new_entry.password = this.password;
        new_entry.created_at = this.created_at;
        new_entry.picture_url = this.get_picture_url();
        new_entry.personal_description = this.get_personal_description();

        DatabaseReference ref;
        ref = mDatabase.child("patients").push();
        ref.setValue(new_entry);

        this.patient_id = ref.getKey();
        return true;
    }

    public String get_patient_id() {
        return patient_id;
    }

    public void set_patient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public static void add_prescription(String patient_id, Prescription new_prescription, String day){

        //database add
        new_prescription.register();
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("patients");
        ref.child(patient_id).child("prescriptions").child(day).child(new_prescription.get_id()).setValue(true);
    }
}
