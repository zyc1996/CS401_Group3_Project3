package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: A class for User to be used by Doctor and Patient subclasses
 */
public class User {
    /**
     * int variable to denote whether user is doctor or patient
     * String variables for user name, email, url of profile image, personal description, password
     * Date variable for creation date
     * DatabaseReference variable for use in database connection
     */
    protected int accountType; //0 is user, 1 is patient, 2 is doctor
    protected String user_name,
            email,
            picture_url, //option url for profile image
            personal_description, //optional profile description
            password;
    protected Date created_at;
    protected DatabaseReference mDatabase;

    /**
     * Default constructor for User
     */
    public User(){
        user_name = "";
        email = "";
        picture_url = "";
        personal_description = "";
        password = "";
        created_at = new Date();
        created_at.getTime();
        accountType = 0;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for user with name, email, and account password
     * @param name_in String user name
     * @param email_in String user email
     * @param password_in String user account password
     */
    public User(String name_in, String email_in, String password_in){
        user_name = name_in;
        email = email_in;
        password = password_in;
        picture_url = "";
        personal_description = "";
        created_at = new Date();
        created_at.getTime();
        accountType = 0;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Send a message to another user with content from sender to target
     * @param content String content of message
     * @param sender_id String sender id
     * @param target_id String id for message target
     * @return true if target exists or false if target does not exist
     */
    public static boolean send_message(String content, String sender_id, String target_id){ //returns true if message was sent, false if error
            //check if sender exists and that we already have a conversation with the target
            //if we do, we call
            //Conversation.send_message(content, sender_id, conv_id);


        //if we reached this point, we do not have a conversation with the target yet
        //check if target id exists in database

        //if they do
        Conversation new_convo = new Conversation(sender_id, target_id);
        Conversation.send_message(content, sender_id, new_convo.get_id());


        //if target does not exist, return false
        return false;
    }

    /**
     * Check if user is doctor
     * @return true if account type is 2 for doctor, false if not
     */
    public boolean is_doctor(){
        if(accountType == 2){
            return true;
        }
        return false;
    }

    /**
     * Check if user is patient
     * @return true if account type is 1 for patient, false if not
     */
    public boolean is_patient(){
        if(accountType == 1){
            return true;
        }
        return false;
    }

    /**
     * Getter for user_name
     * @return String user name
     */
    public String get_user_name() {
        return user_name;
    }

    /**
     * Setter for user_name
     * @param String user name
     */
    public String get_email() {
        return email;
    }

    /**
     * Getter for email
     * @return String user email
     */
    public void set_email(String email) {
        this.email = email;
    }

    /**
     * Getter for picture_url
     * @return String user picture url
     */
    public String get_picture_url() {
        return picture_url;
    }

    /**
     * Setter for picture_url
     * @param String user picture url
     */
    public void set_picture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    /**
     * Getter for personal_description
     * @return String short user personal description
     */
    public String get_personal_description() {
        return personal_description;
    }

    /**
     * Setter for personal_description
     * @param String short user personal description
     */
    public void set_personal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    /**
     * Getter for password
     * @return String user password
     */
    public String get_password() {
        return password;
    }

    /**
     * Setter for password
     * @param String user password
     */
    public void set_password(String password) {
        this.password = password;
    }

    /**
     * Getter for created_at
     * @return String user creation date
     */
    public String get_created_at() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(created_at);

    }

    /**
     * setter for created_at
     * @param String user creation date
     */
    public void set_created_at(Date created_at) {
        this.created_at = created_at;
    }
}
