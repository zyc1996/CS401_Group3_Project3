package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;

public class ProfileEditActivity extends AppCompatActivity {

    private EditText mPictureURL; //holds the profile picture URL
    private EditText mDescription; //hold the personal description

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        mPictureURL = findViewById(R.id.profile_pic_url_fill);
        mDescription = findViewById(R.id.patient_description_edit);
    }

    public void confirmChanges(View view){

        String pictureURL = mPictureURL.getText().toString();
        String description = mDescription.getText().toString();

        Log.d("tagP","Picture URL: " + pictureURL);
        Log.d("tagD","Personal Description: "+ description);

        Intent replyIntent = new Intent();
        replyIntent.putExtra("picture_URL", pictureURL);
        replyIntent.putExtra("description", description);

        setResult(RESULT_OK,replyIntent);
        finish();
    }


}
