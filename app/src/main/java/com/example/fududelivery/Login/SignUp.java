package com.example.fududelivery.Login;

import static com.example.fududelivery.HelperFirebase.FirebaseHelper.getFirestoreInstance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
        AppCompatButton submitNameBtn = findViewById(R.id.submitNameBtn);
        AppCompatButton submitPasswordBtn = findViewById(R.id.submitPassword);

        ImageView backwardBtn = findViewById(R.id.backward);

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                TextInputEditText emailField = findViewById(R.id.emailField);
                String email = emailField.getText().toString();
                String password = ".......";

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task -> {
                    if(task.isSuccessful()) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.v("Debug", user.toString());
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    findViewById(R.id.email).setVisibility(View.GONE);
                                    findViewById(R.id.confirmBtn).setVisibility(View.GONE);

                                    findViewById(R.id.name).setVisibility(View.VISIBLE);
                                    findViewById(R.id.submitNameBtn).setVisibility(View.VISIBLE);

                                    TextView textView = (TextView) findViewById(R.id.signupdescription);
                                    textView.setText("Hello! What is your name?");
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

        submitNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.submitNameBtn).setVisibility(View.GONE);
                findViewById(R.id.submitPassword).setVisibility(View.VISIBLE);
                TextView textView = (TextView) findViewById(R.id.signupdescription);
                textView.setText("Password length is at least 6 characters");
                findViewById(R.id.password).setVisibility(View.VISIBLE);
                findViewById(R.id.repassword).setVisibility(View.VISIBLE);
                findViewById(R.id.name).setVisibility(View.GONE);
            }
        });

        submitPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send Intent
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                TextInputEditText passwordField = findViewById(R.id.passwordField);
                TextInputEditText repasswordField = findViewById(R.id.repasswordField);

                String repassword = repasswordField.getText().toString();
                String password = passwordField.getText().toString();

                if(repassword.equals(password)) {
                    user.updatePassword(password)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Debug", "User password updated.");
                                        Intent intent = new Intent(SignUp.this, VerifyEmail.class);
                                        startActivity(intent);

                                        //Luu Uid vao firestore
                                        String userUid = user.getUid();
                                        firestoreInstance.collection("Users").add(userUid).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error adding document", e);
                                                    }
                                                });
                                    } else {
                                        Log.d("Debug", "Failed to reload user");
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUp.this, "Check your re-enter password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
