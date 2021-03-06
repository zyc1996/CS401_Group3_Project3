package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    private static final int REQUEST_PHOTO = 3;
    /**
     * The string to store the user's ID
     */
    private String userID;

    /**
     * The int to tell the account type (Patient = 1, Doctor = 2)
     */
    private int ACCOUNT_TYPE;

    /**
     * the button to trigger camera activity
     */
    private ImageButton mPhotoButton; //the button to take a picture
    /**
     * the image view that holds the incoming picture
     */
    private ImageView mPhotoView;   //display for the user's profile picture
    /**
     * the bitmap that holds the picture value
     */
    private Bitmap mProfilePicture;

    /**
     * EditText hold the personal description
     */
    private EditText mDescription;
    /**
     * hold the user's name
     */
    private TextView mName;

    /**
     * On creation of activity initializes editing of profile activity
     * @param savedInstanceState Bundle for saving instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_edit_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        mName = findViewById(R.id.user_name);
        mDescription = findViewById(R.id.patient_description_edit);

        Intent intent = getIntent();
        if (intent.getExtras().equals(null)) {
            return;
        }
        userID = intent.getExtras().getString("user_ID");
        ACCOUNT_TYPE = intent.getExtras().getInt("account_type");
        mProfilePicture = (Bitmap)intent.getExtras().get("profile_picture");

        mPhotoView = (ImageView) findViewById(R.id.profile_picture);
        mPhotoView.setImageBitmap(mProfilePicture);
        mPhotoButton = (ImageButton) findViewById(R.id.picture_button);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });

        String name = intent.getExtras().getString("name");
        String description = intent.getExtras().getString("description");
        mName.setText(name);
        mDescription.setText(description);

    }

    /**
     * a method that allows camera app access
     * @param view
     */
    public void takePicture(View view) {
        Intent takeImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takeImage.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeImage, REQUEST_PHOTO);
        }
    }

    /**
     * a method that catches the picture from the camera and updates the bitmap value
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mProfilePicture = (Bitmap) extras.get("data");
            mPhotoView.setImageBitmap(mProfilePicture);
        }

    }
    /**
     * Menu icons are inflated just as they were with actionbar
     * @param menu Menu inflated
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    /**
     * finish activity on back button
     * @param back MenuItem for back
     */
    public void onBack(MenuItem back) {
        finish();
    }

    /**
     * For confirming changes in edited profile
     * @param view View for changes
     */
    public void confirmChanges(View view){

        String description = mDescription.getText().toString();

        Log.d("tagD","Personal Description: "+ description);

        Intent replyIntent = new Intent();
        replyIntent.putExtra("description", description);
        replyIntent.putExtra("user_ID",userID);
        replyIntent.putExtra("account_type",ACCOUNT_TYPE);
        replyIntent.putExtra("profile_picture",mProfilePicture);

        setResult(RESULT_OK,replyIntent);
        finish();
    }

}
