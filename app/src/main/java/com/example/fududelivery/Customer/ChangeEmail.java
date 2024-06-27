package com.example.fududelivery.Customer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeEmail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_changeemail);
        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());
        FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AppCompatEditText emailField = findViewById(R.id.changeEmailField);

        findViewById(R.id.backward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                setResult(RESULT_CANCELED);
            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                if (!email.isEmpty()) {
                    user.updateEmail(email)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.v("Debug", e.toString());
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Debug", "User email address updated.");
                                        firestoreInstance.collection("Users")
                                                .whereEqualTo("userUid", userSessionManager.getUserInformation())
                                                .get()
                                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                                    if (!queryDocumentSnapshots.isEmpty()) {
                                                        queryDocumentSnapshots.getDocuments().get(0).getReference()
                                                                .update("email", email)
                                                                .addOnSuccessListener(aVoid -> {
                                                                    Toast.makeText(ChangeEmail.this, "User email address updated.", Toast.LENGTH_SHORT).show();
                                                                    userSessionManager.loginUserGmail(email);
                                                                    setResult(RESULT_OK);
                                                                    finish();
                                                                })
                                                                .addOnFailureListener(e -> Toast.makeText(ChangeEmail.this, "Failed to update email", Toast.LENGTH_SHORT).show());
                                                    } else {
                                                        Toast.makeText(ChangeEmail.this, "User not found", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(e -> Toast.makeText(ChangeEmail.this, e.toString(), Toast.LENGTH_SHORT).show());
                                    } else {
                                        Toast.makeText(ChangeEmail.this, task.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ChangeEmail.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
