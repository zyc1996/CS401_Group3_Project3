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
     * The picture URL was a placeholder for the profile image functionality which we ended up not being able to implement
     * Date variable for creation date
     * DatabaseReference variable for use in database connection
     */
    protected int accountType; //0 is user, 1 is patient, 2 is doctor
    protected String userName,
            email,
            pictureUrl, //option url for profile image
            personalDescription, //optional profile description
            password;
    protected Date createdAt;
    protected DatabaseReference mDatabase;
    protected String profile_picture;

    /**
     * Default constructor for User
     */
    public User(){
        userName = "";
        email = "";
        pictureUrl = "";
        personalDescription = "";
        password = "";
        createdAt = new Date();
        createdAt.getTime();
        accountType = 0;
        profile_picture = "";

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for user with name, email, and account password
     * @param nameIn String user name
     * @param emailIn String user email
     * @param passwordIn String user account password
     */
    public User(String nameIn, String emailIn, String passwordIn){
        userName = nameIn;
        email = emailIn;
        password = passwordIn;
        pictureUrl = "";
        personalDescription = "";
        createdAt = new Date();
        createdAt.getTime();
        accountType = 0;
        profile_picture = "";

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Check if user is doctor
     * @return true if account type is 2 for doctor, false if not
     */
    public boolean isDoctor(){
        if(accountType == 2){
            return true;
        }
        return false;
    }

    /**
     * Check if user is patient
     * @return true if account type is 1 for patient, false if not
     */
    public boolean isPatient(){
        if(accountType == 1){
            return true;
        }
        return false;
    }

    /**
     * Getter for user name
     * @return String user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for user_name
     * @param uName user name to set
     */
    public void setUserName(String uName) { userName = uName; }

    /**
     * Getter for email
     * @return email as String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param email String to set email to
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for picture_url
     * @return String user picture url
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * Setter for pictureUrl
     * @param pictureUrl user picture url
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * Getter for personal_description
     * @return String short user personal description
     */
    public String getPersonalDescription() {
        return personalDescription;
    }

    /**
     * Setter for personalDescription
     * @param personalDescription short user personal description
     */
    public void setPersonalDescription(String personalDescription) {
        this.personalDescription = personalDescription;
    }

    /**
     * Getter for password
     * @return String user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for created_at
     * @return String user creation date
     */
    public String getCreatedAt() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(createdAt);
    }

    /**
     * setter for createdAt
     * @param createdAt user creation date
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * getter for profile picture
     * @return the profile picture string
     */
    public String getProfile_picture() {
        return profile_picture;
    }

    /**
     * setter for profile_picture
     * @param profile_picture the profile_picture string
     */
    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
