package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 *
 */
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
    private String userName,
            email,
            picture_url, //option url for profile image
            personal_description, //optional profile description
            password;

    /**
     * Date class testing object
     */
    private Date createdAt;

    protected DatabaseReference mDatabase;

    /**
     * Helper method to initialize variables before every test
     */
    @Before
    public void init(){
        testUser = new User();
    }

    /**
     * Method to test User's is_doctor method
     */
    @Test
    public void testIsDoctor() {
        //testUser accountType = 0
        assertFalse(testUser.isDoctor());
    }

    /**
     * Method to test User's is_patient method
     */
    @Test
    public void testIsPatient() {
        //testUser accountType = 0
        assertFalse(testUser.isPatient());
    }

}