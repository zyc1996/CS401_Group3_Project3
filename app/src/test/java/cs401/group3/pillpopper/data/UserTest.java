package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * User class testing object
     */
    private User testUser;

    /**
     * AccountType class testing object
     */
    private int accountType; //0 is user, 1 is patient, 2 is doctor

    /**
     * string class testing objects for user_name, email, picture_url, personal_description, and password
     */
    private String user_name,
            email,
            picture_url, //option url for profile image
            personal_description, //optional profile description
            password;

    /**
     * Date class testing object
     */
    private Date created_at;

    protected DatabaseReference mDatabase;

    /**
     * Helper method to initialize variables before every test
     */
    @Before
    public void init(){
        testUser = new User();
    }

    /**
     * Method to test User's send_message method with a target that does not exist
     */
    @Test
    public void testSend_messageFalse() {
        //check if sender exists and that we already have a conversation with the target
        //if we do, we call
        //Conversation.send_message(content, sender_id, conv_id);


        //if we reached this point, we do not have a conversation with the target yet
        //check if target id exists in database

        //if they do
        //Conversation new_convo = new Conversation(sender_id, target_id);
        //Conversation.send_message(content, sender_id, new_convo.get_id());

        assertFalse(testUser.send_message("test", "sender_id", String "test_id"));
        //if target does not exist, return false

    }

    /**
     * Method to test User's is_doctor method
     */
    @Test
    public void testIs_doctor() {
        //testUser accountType = 0
        assertFalse(testUser.is_doctor());
    }

    /**
     * Method to test User's is_patient method
     */
    @Test
    public void testIs_patient() {
        //testUser accountType = 0
        assertFalse(testUser.is_patient());
    }

}