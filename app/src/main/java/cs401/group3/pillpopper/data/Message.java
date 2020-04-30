package cs401.group3.pillpopper.data;

import java.util.Date;

public class Message {
    private int id;
    private String content;
    private boolean sent_by_user, edited;
    private Date timestamp;

    public Message(String input, boolean from_user, int id_in) {
        timestamp.getTime();
        content = input;
        id = id_in;
        sent_by_user = from_user; //is the message from the user? If not, it is from the other user
        edited = false;
    }

    public Message() {
        this.timestamp.getTime();
        this.content = "";
        sent_by_user = false;
        edited = false;
        id = -1; //value to mark non-normal message
    }

    public String get_content() { return content; }

    public int get_id() { return id; }

    public void content_edit(String input) {
        edited = true;
        content = input;
    }

    public boolean from_this_user(){
        return sent_by_user;
    }

    public boolean is_edited(){
        return edited;
    }

    public Date get_timestamp() {
        return timestamp;
    }
}
