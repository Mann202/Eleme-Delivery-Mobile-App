package com.example.fududelivery.Login;

import static com.example.fududelivery.HelperFirebase.FirebaseHelper.getFirestoreInstance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseFirestore firestoreInstance = getFirestoreInstance(this);

        AppCompatButton buttonSubmit = findViewById(R.id.confirmBtn);
        AppCompatButton nextBtn = findViewById(R.id.nextBtn);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                TextInputEditText emailField = findViewById(R.id.emailField);
                String email = emailField.getText().toString();
                String password = "manlun0902";

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task -> {
                    if(task.isSuccessful()) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.v("Debug", user.toString());
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Registered successfully. Please verify your email!", Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.confirmBtn).setVisibility(View.GONE);
                                    findViewById(R.id.nextBtn).setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    Log.v("Debug", task.getException().toString());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.v("Debug", task.getException().toString());
                    }
                }));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser userCheck = firebaseAuth.getCurrentUser();
                if (userCheck != null) {
                    userCheck.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (userCheck.isEmailVerified()) {
                                    Log.v("Debug", "Verified");
                                } else {
                                    Toast.makeText(SignUp.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("Debug", "Failed to reload user: " + task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    Log.e("Debug", "No user signed in");
                }
            }
        });

    }
}
