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
    private String patientId; //database key

    /**
     * Default constructor for Patient
     */
    public Patient() {
        this.patientId = "-1";
        userName = "";
        email = "";
        password = "";
        createdAt = new Date();
        accountType = 1;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for Patient with name, email, and password
     * @param nameIn String patient name
     * @param emailIn String patient email
     * @param passwordIn String patient account password
     */
    public Patient(String nameIn, String emailIn, String passwordIn) {
        this.userName = nameIn;
        this.email = emailIn;
        this.password = passwordIn;
        createdAt = new Date();
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
            public String picture_url;
            public String personal_description;
        }
        Entry newEntry = new Entry();
        newEntry.user_name = this.userName;
        newEntry.email = this.email;
        newEntry.password = this.password;
        newEntry.created_at = this.createdAt;
        newEntry.picture_url = this.getPictureUrl();
        newEntry.personal_description = this.getPersonalDescription();

        DatabaseReference ref;
        ref = mDatabase.child("patients").push();
        ref.setValue(newEntry);

        this.patientId = ref.getKey();
        return true;
    }

    /**
     * patientId getter
     * @return patientId String patient identification code
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * patientId setter
     * @param patientId String patient identification code
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Connect to Firebase database and add new prescription object to patient by id
     * Day also added to database
     * @param patientId String patient identification code
     * @param newPrescription Prescription new prescription object added under patient by id
     * @param day String day of prescription
     */
    public static void addPrescription(String patientId, Prescription newPrescription, String day){

        //database add
        newPrescription.register();
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("patients");
        ref.child(patientId).child("prescriptions").child(day).child(newPrescription.getId()).setValue(true);
    }

    /**
     * Update patient in database with id, picture, and description
     * @param patientIdIn String id for patient
     * @param picIn string picture for patient
     * @param descIn string description of patient
     */
    public static void updatePatient(String patientIdIn, String picIn, String descIn ){
        //database update
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("patients");
        ref.child(patientIdIn).child("personal_description").setValue(descIn);
        ref.child(patientIdIn).child("picture_url").setValue(picIn);
    }
}
