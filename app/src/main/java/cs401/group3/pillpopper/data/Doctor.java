package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Doctor extends User {

    private String doctor_id;

    public Doctor() {
        super();
        accountType = -1;
    }

     public Doctor(String name_in, String email_in, String password_in){
         super(name_in, email_in, password_in);
         accountType = 2;
    }

    public boolean register(){
        class Entry{
            public String user_name;
            public String email;
            public String picture_url;
            public String password;
            public Date created_at;
        }
        Entry new_entry = new Entry();
        new_entry.user_name = this.user_name;
        new_entry.email = this.email;
        new_entry.picture_url = this.picture_url;
        new_entry.password = this.password;
        new_entry.created_at = this.created_at;

        DatabaseReference ref;
        ref = mDatabase.child("doctors").push();
        ref.setValue(new_entry);
        this.doctor_id = ref.getKey();

        return true;
    }

    public String get_doctor_id() {
        return doctor_id;
    }

    public void set_doctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }
}
