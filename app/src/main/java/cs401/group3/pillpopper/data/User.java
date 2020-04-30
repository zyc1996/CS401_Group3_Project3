package cs401.group3.pillpopper.data;

import java.util.ArrayList;
import java.util.Date;

public class User {
    protected String user_name, email,
            picture_url, //option url for profile image
            personal_description, //optional profile description
            password;
    protected ArrayList<Conversation> conversations;
    protected Date created_at;

    public User(){
        user_name = "";
        email = "";
        picture_url = "";
        personal_description = "";
        password = "";
        conversations = new ArrayList<Conversation>();
        created_at = new Date();
        created_at.getTime();
    }

    public User(String name_in, String email_in, String password_in){
        user_name = name_in;
        email = email_in;
        password = password_in;
        picture_url = "";
        personal_description = "";
        conversations = new ArrayList<Conversation>();
        created_at = new Date();
        created_at.getTime();
    }

    //Send a message to another user
    public boolean send_message(String content, int target_id){ //returns true if message was sent, false if error
            //check if we already have a conversation with the target
        for(int i = 0; i < conversations.size(); i++){
            if(target_id == conversations.get(i).get_target_user_id()){
                conversations.get(i).send_message(content);
                return true;
            }
        }


        //if we reached this point, we do not have a conversation with the target yet
        //check if target id exists in database

        //
        //DATABASE STUFF **********************************************
        //

        //if target does not exist, return false
        //else create a conversation with the target and add target's name from database
        String target_name = "";
        Conversation new_convo = new Conversation(target_id, target_name);
        new_convo.send_message(content);
        conversations.add(new_convo);
        return true;
    }

    //similar to send_message, but as the recipient of a message from another user
    public boolean receive_message(String content,int target_id){
        //check if we already have a conversation with the target
        for(int i = 0; i < conversations.size(); i++){
            if(target_id == conversations.get(i).get_target_user_id()){
                conversations.get(i).send_message(content);
                return true;
            }
        }


        //if we reached this point, we do not have a conversation with the target yet
        //check if target id exists in database

        //
        //DATABASE STUFF **********************************************
        //

        //if target does not exist, return false
        //else create a conversation with the target and add target's name from database
        String target_name = "";
        Conversation new_convo = new Conversation(target_id, target_name);
        new_convo.message_receive(content);
        conversations.add(new_convo);
        return true;
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

    public Date get_created_at() {
        return created_at;
    }
}
