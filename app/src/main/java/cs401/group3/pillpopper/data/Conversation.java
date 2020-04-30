package cs401.group3.pillpopper.data;

import java.util.ArrayList;
import java.util.Date;

public class Conversation {
    private int target_user_id, message_count;
    private String target_user_name;
    private ArrayList<Message> messages;
    private Date created_at;


    public Conversation(){
        target_user_id = -1;
        target_user_name = "";
        messages = new ArrayList<>();
        created_at.getTime();
    }

    public Conversation(int target_id, String target_name){
        target_user_id = target_id;
        target_user_name = target_name;
        messages = new ArrayList<>();
        created_at.getTime();
    }

    public void send_message(String content) {
        //Create a message object with the string input as the content and mark it as sent by user
        Message new_message = new Message(content, true, message_count);
        messages.add(new_message);
    }

    public void message_receive(String content){
        //Create a message object with the string input as the content and
        //mark it as sent by the other person in the conversation
        Message new_message = new Message(content, false, message_count);
        messages.add(new_message);
    }

    public void message_delete(Integer message_id) {
        //remove message ID from both sender/receiver objects
        //then delete message
        for (int i = 0; i < messages.size(); i++){
            if (messages.get(i).get_id() == message_id){
                messages.remove(i);
                return;
            }
        }
    }
}
