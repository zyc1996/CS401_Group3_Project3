package cs401.group3.pillpopper.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Conversation {
    private String id;
    private String user_id_1, user_id_2;
    private String user_name_1, user_name_2;
    private Date created_at;
    private DatabaseReference mDatabase;

    public Conversation(){
        this.id = "-1";
        created_at = new Date();
        created_at.getTime();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Conversation(String user_id_1, String user_id_2){ // target is the other user in this conversation
        //check that users exist
        //then
        mDatabase = FirebaseDatabase.getInstance().getReference();

        class Entry{
            public String user_id_1, user_id_2;
            public String user_name_1, user_name_2;
            public Date created_at;
        }

        Entry new_entry = new Entry();
        new_entry.user_id_1 = this.user_id_1;
        new_entry.user_id_2 = this.user_id_2;
        new_entry.user_name_1 = this.user_name_1;
        new_entry.user_name_2 = this.user_name_2;

        new_entry.created_at = this.created_at;

        DatabaseReference ref;
        ref = mDatabase.child("prescriptions").push();
        ref.setValue(new_entry);

        this.id = ref.getKey();

    }

    public static void send_message(String content, String sender_id, String conv_id) {
        //Check if convo and sender exists in database
        //if it does
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("conversations");
        ref = ref.child(conv_id).child("messages").push();
        ref.child("content").setValue(content);
        //ref.child("sender").child("name").setValue(sender_name);
        ref.child("sender").child("id").setValue(sender_id);
        ref.child("created_at").setValue(new Date());
    }

    public String get_id() {
        return id;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public String getUser_id_1() {
        return user_id_1;
    }

    public void setUser_id_1(String user_id_1) {
        this.user_id_1 = user_id_1;
    }

    public String getUser_id_2() {
        return user_id_2;
    }

    public void setUser_id_2(String user_id_2) {
        this.user_id_2 = user_id_2;
    }

    public String getUser_name_1() {
        return user_name_1;
    }

    public void setUser_name_1(String user_name_1) {
        this.user_name_1 = user_name_1;
    }

    public String getUser_name_2() {
        return user_name_2;
    }

    public void setUser_name_2(String user_name_2) {
        this.user_name_2 = user_name_2;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
