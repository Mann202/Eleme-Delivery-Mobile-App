package com.example.fududelivery.Customer.UpdateProfile;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CustomerProfile extends AppCompatActivity {
    FirebaseFirestore firestoreInstance;
    UserSessionManager userSessionManager;

    TextView profileName, profilePhoneNumber, profileEmail;

    ImageView profileAvatar;

    private ActivityResultLauncher<Intent> editProfileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        // Initialize Firebase Firestore and session manager
        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getApplicationContext());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AppCompatButton logoutBtn = findViewById(R.id.logout_button);
        ImageView iv_change = findViewById(R.id.change);
        ImageView iv_back = findViewById(R.id.backIcon);

        profileName = findViewById(R.id.user_name);
        profilePhoneNumber = findViewById(R.id.user_phone_number);
        profileEmail = findViewById(R.id.user_email);
        profileAvatar = findViewById(R.id.user_avatar);

        String userUid = userSessionManager.getUserInformation();

        // Load user information from intent
        loadUserInfo();

        // Initialize the ActivityResultLauncher
        initEditProfileLauncher();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerProfile.this, Customer.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //edit profile click
        iv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
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
    public void loadUserInfo() {
        Intent intent = getIntent();

        String nameUser = intent.getStringExtra("userName");
        String phoneUser = intent.getStringExtra("userPhone");
        String emailUser = intent.getStringExtra("userGmail");
        String avatarUser = intent.getStringExtra("userPhoto");

        profileName.setText(nameUser);
        profilePhoneNumber.setText(phoneUser);
        profileEmail.setText(emailUser);
        if (avatarUser != null && !avatarUser.isEmpty()) {
            Glide.with(this).load(avatarUser).into(profileAvatar);
        }
    }

    public void passUserData() {
        String UID = userSessionManager.getUserInformation();

        firestoreInstance.collection("Users")
                .whereEqualTo("userUid", UID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);

                            String nameFromDB = document.getString("userName");
                            String phoneFromDB = document.getString("userPhone");
                            String emailFromDB = document.getString("userGmail");
                            String avatarfromDB = document.getString("userPhoto");

                            Intent intent = new Intent(CustomerProfile.this, EditProfileActivity.class);

                            intent.putExtra("userName", nameFromDB);
                            intent.putExtra("userPhone", phoneFromDB);
                            intent.putExtra("userGmail", emailFromDB);
                            intent.putExtra("userPhoto", avatarfromDB);

                            startActivity(intent);
                        } else {
                            Toast.makeText(CustomerProfile.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CustomerProfile.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                });
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
