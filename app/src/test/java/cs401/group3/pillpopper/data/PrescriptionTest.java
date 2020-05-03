package cs401.group3.pillpopper.data;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrescriptionTest {

    /**
     * Doctor class testing object
     */
    private Prescription testPre;

    /**
     * Helper method to initialize variables before every test
     */
    @Before
    public void init(){
        testPre = new Prescription();
    }

    /**
     * Method to test Register method to add Prescription class object to database
     */
    @Test
    public void testRegister() {
        assertTrue(testPre.register());
    }
}