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
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(created_at);

    }

    public void set_created_at(Date created_at) {
        this.created_at = created_at;
    }
}
