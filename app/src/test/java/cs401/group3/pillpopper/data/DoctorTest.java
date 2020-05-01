package cs401.group3.pillpopper.data;
import org.junit.Before;
import org.junit.Test;
import com.google.firebase.database.DatabaseReference;
import static org.junit.Assert.*;

public class DoctorTest {

    /**
     * Doctor class testing object
     */
    private Doctor testDoc;

    /**
     * Helper method to initialize variables before every test
     */
    @Before
    public void init(){
        testDoc = new Doctor();
    }

    /**
     * Method to test Register method to add Prescription class object to database
     */
    @Test
    public void testRegister() {
        assertTrue(testDoc.register());
    }

    /**
     * Method to test add_Patient method to add Patient by id
     */
    @Test
    public void testAdd_patient() {
        assertTrue(testDoc.add_patient("test_id"));
    }

    /**
     * Method to test add_Patient method to remove Patient by id
     */
    @Test
    public void testRemove_patient() {
        assertTrue(testDoc.remove_patient("test_id"));
    }
}