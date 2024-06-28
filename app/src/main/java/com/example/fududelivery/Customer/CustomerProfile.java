package com.example.fududelivery.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerProfile extends AppCompatActivity {
    FirebaseFirestore firestoreInstance;
    AppCompatEditText nameField;
    AppCompatEditText phoneField;
    AppCompatEditText emailField;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profileinfomation);

        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getApplicationContext());

        AppCompatButton logoutBtn = findViewById(R.id.logout_button);
        nameField = findViewById(R.id.nameField);
        phoneField = findViewById(R.id.phoneField);
        emailField = findViewById(R.id.emailField);

        String userUid = userSessionManager.getUserInformation();

        nameField.setText(userSessionManager.getUserName());
        phoneField.setText(userSessionManager.getUserPhone());
        emailField.setText(userSessionManager.getUserGmail());

        nameField.setFocusable(false);
        phoneField.setFocusable(false);
        emailField.setFocusable(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.sn_language_customer);
        spinner.setAdapter(adapter);

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
    }

    private ActivityResultLauncher<Intent> changeNameLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    nameField.setText(userSessionManager.getUserName());
                }
            }
    );

    private ActivityResultLauncher<Intent> changePhoneLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    phoneField.setText(userSessionManager.getUserPhone());
                }
            }
    );

    private ActivityResultLauncher<Intent> changeEmailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    emailField.setText(userSessionManager.getUserGmail());
                }
            }
    );
}
