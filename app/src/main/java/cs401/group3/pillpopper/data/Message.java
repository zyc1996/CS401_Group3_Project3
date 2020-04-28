package cs401.group3.pillpopper.data;

public class Message {
    //doctor_Owner
    //Patient_Owner
    private String date, content;
    //posted_by

    public Message() {
        //doctor_Owner =
        //Patient_Owner =
        date = "00/00/0000";
        content = "";
        //posted_by =
    }

    public Message(String date, String content) {
        //doctor_Owner =
        //Patient_Owner =
        this.date = date;
        this.content = content;
        //posted_by =
    }

    public void content_edit() {}

    public void message_to() {}

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
