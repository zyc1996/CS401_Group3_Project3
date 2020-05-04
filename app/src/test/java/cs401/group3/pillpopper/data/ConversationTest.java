package cs401.group3.pillpopper.data;
import org.junit.Before;
import org.junit.Test;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;
import static org.junit.Assert.*;

public class ConversationTest {

    /**
     * Conversation class testing object
     */
    Conversation testCon;

    /**
     * Helper method to initialize variables before every test
     */
    @Before
    public void init(){
        testCon = new Conversation("test_id1", "test_id2");
    }

    /**
     * Method to test send_message method to add message string to database by send and receive id
     */
    @Test
    public void testSend_message() {
        //Check if conversation and sender exists in database
        //if it does send message
        testCon.send_message("test_id1", "test_message", "test_id2");
    }

}