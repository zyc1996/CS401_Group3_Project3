package cs401.group3.pillpopper.data;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Date;

public class PatientTest {

    /**
     * Patient class testing object
     */
    Patient testPat;

    /**
     * Patient class testing object
     */
    Prescription testPre;

    /**
     * Helper method to initialize variables before every test
     */
    @Before
    public void init(){
        testPat = new Patient();
        testPre = new Prescription();
    }

    /**
     * Method to test Register method to add Patient class object to database
     */
    @Test
    public void testRegister() {
        assertTrue(testPat.register());
    }

    /**
     * Method to test add_prescription method to add Prescription class object to database by patient id with day
     */
    @Test
    public void testAdd_prescription() {

        //database add
        testPat.add_prescription("test_id", testPre, "mon");
    }

}