package cs401.group3.pillpopper.data;

import java.util.ArrayList;
import java.util.Date;

public class User {
    protected String username, email,
            picture_url, //option url for profile image
            personal_description, //optional profile description
            password;
    protected ArrayList<Conversation> conversations;
    protected Date created_at;

    public User(){
        username = "";
        email = "";
        picture_url = "";
        personal_description = "";
        password = "";
        conversations = new ArrayList<Conversation>();
        created_at.getTime();
    }

    public User(String name_in, String email_in, String password_in){
        username = name_in;
        email = email_in;
        password = password_in;
        picture_url = "";
        personal_description = "";
        conversations = new ArrayList<Conversation>();
        created_at.getTime();
    }

}
