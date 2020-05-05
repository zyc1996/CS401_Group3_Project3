package cs401.group3.pillpopper.data;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: A subclass of User for Doctor objects. Used for storing data for doctor accounts.
 */
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Doctor extends User {

    /**
     * String variable for doctor id code
     */
    private String doctorId;

    /**
     * Default constructor for Doctor
     */
    public Doctor() {
        super();
        accountType = -1;
    }

    /**
     * Parameterized constructor with name, email, and password
     * @param nameIn String doctor name
     * @param emailIn String doctor email
     * @param passwordIn String doctor account password
     */
     public Doctor(String nameIn, String emailIn, String passwordIn){
         super(nameIn, emailIn, passwordIn);
         accountType = 2;
    }

    /**
     * Registers a new doctor user in the database
     * Connects to Firebase database and sends class object of new doctor entry
     * @return true if new doctor account registered
     */
    public boolean register(){
        class Entry{
            public String user_name;
            public String email;
            public String picture_url;
            public String password;
            public Date created_at;
            public String personal_description;
        }
        Entry newEntry = new Entry();
        newEntry.user_name = this.userName;
        newEntry.email = this.email;
        newEntry.picture_url = this.pictureUrl;
        newEntry.password = this.password;
        newEntry.personal_description = this.getPersonalDescription();
        newEntry.created_at = this.createdAt;

        DatabaseReference ref;
        ref = mDatabase.child("doctors").push();
        ref.setValue(newEntry);
        this.doctorId = ref.getKey();

        return true;
    }

    /**
     * doctorId getter
     * @return doctorId String doctor identification code
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * doctorId setter
     * @param doctorId String doctor identification code
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Adds a patient's key to the doctor's entry in the database
     * @param doctorId Doctor entry to update
     * @param patientId Patient entry to add
     */
    public static void addPatient(String doctorId, String patientId){
        //database add
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        ref.child(doctorId).child("patients").child(patientId).setValue(true);
    }

    /**
     * Update doctor in database with id, picture, and description
     * @param doctorIdIn String id for doctor
     * @param picIn string picture for doctor
     * @param descIn string description of doctor
     */
    public static void updateDoctor(String doctorIdIn, String picIn, String descIn ){
        //database update
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        ref.child(doctorIdIn).child("personal_description").setValue(descIn);
        ref.child(doctorIdIn).child("picture_url").setValue(picIn);
    }
}
