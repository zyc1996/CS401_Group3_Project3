package cs401.group3.pillpopper.adapter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;

public class patientAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_patient);
    }
}