package cs401.group3.pillpopper.data;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: A class that holds message data and user information for holding a conversation between two users
 */
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Conversation {
    /**
     * String variables for conversation id, user ids, user names
     * Date variable for creation date
     * DatabaseReference variable for connecting to Firebase
     */
    private String id;
    private String user_id_1, user_id_2;
    private String user_name_1, user_name_2;
    private Date created_at;
    private DatabaseReference mDatabase;

    /**
     * Default constructor for Conversation
     */
    public Conversation(){
        this.id = "-1";
        created_at = new Date();
        created_at.getTime();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Parameterized constructor for use between two users
     * @param user_id_1 String first user sending conversation
     * @param user_id_2 String second user receiving conversation
     */
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

    /**
     * Connects to database and sends message from sender_id using conv_id
     * @param content
     * @param sender_id
     * @param conv_id
     */
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

    /**
     * id getter
     * @return conversation id
     */
    public String get_id() {
        return id;
    }

    /**
     * id setter
     * @param id string conversation id
     */
    public void set_id(String id) {
        this.id = id;
    }

    /**
     * user_id_1 getter
     * @return user_id_1 string for first user
     */
    public String getUser_id_1() {
        return user_id_1;
    }

    /**
     * user_id_1 setter
     * @param user_id_1 string for first user
     */
    public void setUser_id_1(String user_id_1) {
        this.user_id_1 = user_id_1;
    }

    /**
     * user_id_2 getter
     * @return user_id_2 string for second user
     */
    public String getUser_id_2() {
        return user_id_2;
    }

    /**
     * user_id_2 setter
     * @param user_id_2 string for second user
     */
    public void setUser_id_2(String user_id_2) {
        this.user_id_2 = user_id_2;
    }

    /**
     * user_name_1 getter
     * @return user_name_1 string for first username
     */
    public String getUser_name_1() {
        return user_name_1;
    }

    /**
     * user_name_1 setter
     * @param user_name_1 string for first username
     */
    public void setUser_name_1(String user_name_1) {
        this.user_name_1 = user_name_1;
    }

    /**
     * user_name_2 getter
     * @return user_name_2 string for second username
     */
    public String getUser_name_2() {
        return user_name_2;
    }

    /**
     * user_name_2 setter
     * @param user_name_2 string for second username
     */
    public void setUser_name_2(String user_name_2) {
        this.user_name_2 = user_name_2;
    }

    /**
     * created_at getter
     * @return created_at Date for creation time
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * created_at setter
     * @param created_at Date for creation time
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
