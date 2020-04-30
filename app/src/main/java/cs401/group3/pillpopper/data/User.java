package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    protected int accountType; //0 is user, 1 is patient, 2 is doctor
    protected String user_name,
            email,
            picture_url, //option url for profile image
            personal_description, //optional profile description
            password;
    protected Date created_at;
    protected DatabaseReference mDatabase;


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

    public String login(String user_name, String password){
        //Check if username exists in database
        //first check doctor collection
        //if it exists in doctor collection, return string + id
        //return "doctor " + doctor id;

        //next check patient collection
        //if it exists in patient collection, return string + id
        //return "patient " + patient id;

        //else return none
        return "none";
    }

    //Send a message to another user
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

    public boolean is_doctor(){
        if(accountType == 2){
            return true;
        }
        return false;
    }

    public boolean is_patient(){
        if(accountType == 1){
            return true;
        }
        return false;
    }

    boolean is_doctor(){
        if(accountType == 2){
            return true;
        }
        return false;
    }

    boolean is_patient(){
        if(accountType == 1){
            return true;
        }
        return false;
    }

    public String get_user_name() {
        return user_name;
    }

    public String get_email() {
        return email;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public String get_picture_url() {
        return picture_url;
    }

    public void set_picture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String get_personal_description() {
        return personal_description;
    }

    public void set_personal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    public String get_password() {
        return password;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public String get_created_at() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(created_at);

    }
}
