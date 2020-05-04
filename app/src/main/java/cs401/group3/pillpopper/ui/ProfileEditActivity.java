package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs401.group3.pillpopper.R;
/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The profile editing activity for changing profile information
 */
public class ProfileEditActivity extends AppCompatActivity {

    private String userID;
    private int ACCOUNT_TYPE;

    private EditText mPictureURL; //holds the profile picture URL
    private EditText mDescription; //hold the personal description
    private TextView mName; //hold the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileEditToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mName = findViewById(R.id.user_name);
        mPictureURL = findViewById(R.id.profile_pic_url_fill);
        mDescription = findViewById(R.id.patient_description_edit);

        Intent intent = getIntent();
        if (intent.getExtras().equals(null)) {
            return;
        }
        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");

        String name = intent.getExtras().getString("name");
        String description = intent.getExtras().getString("description");
        mName.setText(name);
        mDescription.setText(description);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    //Re write the back button handling plz, state was never fixed
    public void onBack(MenuItem back) {
        Intent intent;
        if(ACCOUNT_TYPE == 2){
            intent = new Intent(this, DoctorProfileActivity.class);
            startActivity(intent);
        }
        else if(ACCOUNT_TYPE == 1) {
            intent = new Intent(this, PatientProfileActivity.class);
            startActivity(intent);
        }else{
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(ProfileEditActivity.this,"ERROR ACCOUNT TYPE, APP LOGOUT",Toast.LENGTH_LONG).show();
                        }
                    },
                    10000);
            intent = new Intent(this, LoginStartActivity.class);
            startActivity(intent);
        }
    }

    public void confirmChanges(View view){

        String pictureURL = mPictureURL.getText().toString();
        String description = mDescription.getText().toString();

        Log.d("tagP","Picture URL: " + pictureURL);
        Log.d("tagD","Personal Description: "+ description);

        Intent replyIntent = new Intent();
        replyIntent.putExtra("picture_URL", pictureURL);
        replyIntent.putExtra("description", description);
        replyIntent.putExtra("user_ID",userID);
        replyIntent.putExtra("account_type",ACCOUNT_TYPE);

        setResult(RESULT_OK,replyIntent);
        finish();
    }


}
