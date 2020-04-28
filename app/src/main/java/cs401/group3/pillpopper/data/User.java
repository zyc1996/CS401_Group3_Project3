package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class User {

    protected String name, picture, personal_description;
    protected ArrayList<Prescription> list_of_prescription;
    protected ArrayList<Message> list_of_messages;

    public User() {
        name = "";
        picture = "";
        personal_description = "";
        list_of_prescription = new ArrayList<>();
        list_of_messages = new ArrayList<>();
    }
    public void Message_create() {}

    public void Message_delete() {}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }

    public String getDescription() { return personal_description; }

    public void setDescription(String description) { this.personal_description = description; }

    public String getPersonal_description() {
        return personal_description;
    }

    public void setPersonal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    public ArrayList<Prescription> getList_of_prescription() {
        return list_of_prescription;
    }

    public void setList_of_prescription(ArrayList<Prescription> list_of_prescription) {
        this.list_of_prescription = list_of_prescription;
    }

    public ArrayList<Message> getList_of_messages() {
        return list_of_messages;
    }

    public void setList_of_messages(ArrayList<Message> list_of_messages) {
        this.list_of_messages = list_of_messages;
    }
}
