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
    public void init() {
        testDoc = new Doctor();
    }

    /**
     * Method to test Register method to add Prescription class object to database
     */
    @Test
    public void testRegister() {
        assertTrue(testDoc.register());
    }
}
