package cs401.group3.pillpopper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cs401.group3.pillpopper.R;

public class LoginActivity extends AppCompatActivity {
    private Button mRegisterButton;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);

        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegisterActivity();
            }
        });

        mLoginButton = findViewById(R.id.signInButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomePageActivity();
            }
        });
    }

    private void launchRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void launchHomePageActivity() {
        Intent intent = new Intent(this, PatientHomepageActivity.class);
        startActivity(intent);
    }
}
