package com.example.fududelivery.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;

public class CustomerProfile extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profileinfomation);

        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());
        AppCompatButton logoutBtn;
        logoutBtn = findViewById(R.id.logout_button);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSessionManager.logoutUser();
                Intent loginIntent = new Intent(CustomerProfile.this, Login.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finishAffinity();
            }
        });
<<<<<<< Updated upstream
=======

        nameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangeName = new Intent(CustomerProfile.this, ChangeName.class);
                changeNameLauncher.launch(intentChangeName);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        phoneField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePhone = new Intent(CustomerProfile.this, ChangePhonenumber.class);
                changePhoneLauncher.launch(intentChangePhone);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        emailField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangeEmail = new Intent(CustomerProfile.this, ChangeEmail.class);
                changeEmailLauncher.launch(intentChangeEmail);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.changepassword_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePassword = new Intent(CustomerProfile.this, ChangePassword.class);
                startActivity(intentChangePassword);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
>>>>>>> Stashed changes
    }
}
