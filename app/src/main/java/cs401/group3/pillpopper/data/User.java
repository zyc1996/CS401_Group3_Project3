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
    }

    public User(String name_in, String email_in, String password_in){
        user_name = name_in;
        email = email_in;
        password = password_in;
        picture_url = "";
        personal_description = "";
        conversations = new ArrayList<Conversation>();
        created_at.getTime();
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getPersonal_description() {
        return personal_description;
    }

    public void setPersonal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
