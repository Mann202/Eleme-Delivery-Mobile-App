package com.example.fududelivery.Customer.UpdateProfile;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerProfile extends AppCompatActivity {
    FirebaseFirestore firestoreInstance;
    UserSessionManager userSessionManager;

    TextView nameField;
    TextView phoneField;
    TextView emailField;

    private ActivityResultLauncher<Intent> editProfileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        // Initialize Firebase Firestore and session manager
        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getApplicationContext());

        AppCompatButton logoutBtn = findViewById(R.id.logout_button);
        ImageView iv_change = findViewById(R.id.change);
        ImageView iv_back = findViewById(R.id.backIcon);

        nameField = findViewById(R.id.user_name);
        phoneField = findViewById(R.id.user_phone_number);
        emailField = findViewById(R.id.user_email);

        // Load user information
        loadUserInfo();

        nameField.setFocusable(false);
        phoneField.setFocusable(false);
        emailField.setFocusable(false);

        // Initialize the ActivityResultLauncher
        initEditProfileLauncher();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.sn_language_customer);
        spinner.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerProfile.this, Customer.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //edit profile click
//        iv_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CustomerProfile.this, EditProfileActivity.class);
//                editProfileLauncher.launch(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });

        findViewById(R.id.changepassword_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePassword = new Intent(CustomerProfile.this, ChangePassword.class);
                startActivity(intentChangePassword);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
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


    }

    //load User info
    private void loadUserInfo() {
        String userUid = userSessionManager.getUserInformation();

        nameField.setText(userSessionManager.getUserName());
        phoneField.setText(userSessionManager.getUserPhone());
        emailField.setText(userSessionManager.getUserGmail());

    }

    // Edit information Result launcher
    private void initEditProfileLauncher() {
        editProfileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Reload user information after a successful profile edit
                loadUserInfo();
            }
        });
    }
}
