package cs401.group3.pillpopper.data;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;

/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: A subclass of User for Patient objects. Used for storing data for patient accounts.
 */
public class Patient extends User {

    /**
     * String variable for patient id for database
     */
    private String patient_id; //database key

    /**
     * Default constructor for Patient
     */
    public Patient() {
        this.patient_id = "-1";
        user_name = "";
        email = "";
        password = "";
        created_at = new Date();
        accountType = 1;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for Patient with name, email, and password
     * @param name_in String patient name
     * @param email_in String patient email
     * @param password_in String patient account password
     */
    public Patient(String name_in, String email_in, String password_in) {
        this.user_name = name_in;
        this.email = email_in;
        this.password = password_in;
        created_at = new Date();
        accountType = 1;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Registers a new doctor user in the database
     * Connects to Firebase database and sends class object of new patient entry
     * @return true if new patient account registered
     */
    public boolean register(){
        class Entry{
            public String user_name;
            public String email;
            public String password;
            public Date created_at;
        }
        Entry new_entry = new Entry();
        new_entry.user_name = this.user_name;
        new_entry.email = this.email;
        new_entry.password = this.password;
        new_entry.created_at = this.created_at;

        DatabaseReference ref;
        ref = mDatabase.child("patients").push();
        ref.setValue(new_entry);

        this.patient_id = ref.getKey();
        return true;
    }

    /**
     * patient_id getter
     * @return patient_id String patient identification code
     */
    public String get_patient_id() {
        return patient_id;
    }

    /**
     * patient_id setter
     * @param patient_id String patient identification code
     */
    public void set_patient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    /**
     * Connect to Firebase database and add new prescription object to patient by id
     * Day also added to database
     * @param patient_id String patient identification code
     * @param new_prescription Prescription new prescription object added under patient by id
     * @param day String day of prescription
     */
    public static void add_prescription(String patient_id, Prescription new_prescription, String day){
        //day should be of format mon/tue/wed/thu/fri/sat/sun
        //database add
        new_prescription.register();
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("patients");
        ref.child(patient_id).child("prescriptions").child(day).child(new_prescription.get_id()).setValue(true);
    }

}
