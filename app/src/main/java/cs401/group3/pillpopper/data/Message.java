package cs401.group3.pillpopper.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private int id;
    private String content;
    private boolean sent_by_user, edited;
    private Date created_at;

    public Message(String input, boolean from_user, int id_in) {
        created_at.getTime();
        content = input;
        id = id_in;
        sent_by_user = from_user; //is the message from the user? If not, it is from the other user
        edited = false;
        created_at = new Date();
        created_at.getTime();
    }

    public Message() {
        this.created_at.getTime();
        this.content = "";
        sent_by_user = false;
        edited = false;
        id = -1; //value to mark non-normal message
        created_at = new Date();
        created_at.getTime();
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_content() {
        return content;
    }

    public void set_content(String content) {
        this.edited = true;
        this.content = content;
    }

    public boolean is_sent_by_user() {
        return sent_by_user;
    }

    public void set_sent_by_user(boolean sent_by_user) { //false means this message was
                                                        // not from the user containing this object
        this.sent_by_user = sent_by_user;
    }

    public boolean is_edited() {
        return edited;
    }

    public String get_created_at() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(created_at);
    }
}
