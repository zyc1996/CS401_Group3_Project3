package cs401.group3.pillpopper.data;

import java.util.ArrayList;

public class Prescription {

    private ArrayList<Doctor> Doctor_Owner_List;
    private ArrayList<Patient> Patient_List;
    private Integer Days;
    private String Time, End_date, Takes_Daily, Time_between_dose, pending_deletion, content, created_by, ls_note;

    public Prescription() {
        Doctor_Owner_List = new ArrayList<>();
        Doctor_Owner_List = new ArrayList<>();
        Days = 0;
        Time = "";
        End_date = "";
        Takes_Daily = "";
        Time_between_dose = "";
        pending_deletion = "";
        content = "";
        created_by = "";
        ls_note = "";
    }

    public Prescription(ArrayList<Doctor> Doctor_Owner_List, ArrayList<Patient> Patient_List, Integer Days, String Time, String End_date, String Takes_Daily, String Time_between_dose, String pending_deletion, String content, String created_by, String ls_note) {
        this.Doctor_Owner_List = Doctor_Owner_List;
        this.Patient_List = Patient_List;
        this.Days = Days;
        this.Time = Time;
        this.End_date = End_date;
        this.Takes_Daily = Takes_Daily;
        this.Time_between_dose = Time_between_dose;
        this.pending_deletion = pending_deletion;
        this.content = content;
        this.created_by = created_by;
        this.ls_note = ls_note;
    }

    public void update_Presciption() {}

    public void notification() {}

    public ArrayList<Doctor> getDoctor_Owner_List() {
        return Doctor_Owner_List;
    }

    public void setDoctor_Owner_List(ArrayList<Doctor> doctor_Owner_List) {
        Doctor_Owner_List = doctor_Owner_List;
    }

    public ArrayList<Patient> getPatient_List() {
        return Patient_List;
    }

    public void setPatient_List(ArrayList<Patient> patient_List) {
        Patient_List = patient_List;
    }

    public Integer getDays() {
        return Days;
    }

    public void setDays(Integer days) {
        Days = days;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getEnd_date() {
        return End_date;
    }

    public void setEnd_date(String end_date) {
        End_date = end_date;
    }

    public String getTakes_Daily() {
        return Takes_Daily;
    }

    public void setTakes_Daily(String takes_Daily) {
        Takes_Daily = takes_Daily;
    }

    public String getTime_between_dose() {
        return Time_between_dose;
    }

    public void setTime_between_dose(String time_between_dose) {
        Time_between_dose = time_between_dose;
    }

    public String getPending_deletion() {
        return pending_deletion;
    }

    public void setPending_deletion(String pending_deletion) {
        this.pending_deletion = pending_deletion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getLs_note() {
        return ls_note;
    }

    public void setLs_note(String ls_note) {
        this.ls_note = ls_note;
    }
}
