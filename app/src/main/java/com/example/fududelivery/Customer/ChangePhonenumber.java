package com.example.fududelivery.Customer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePhonenumber extends AppCompatActivity {
    private AppCompatButton saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_changephone);

        FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());

        AppCompatEditText phoneEditText = findViewById(R.id.editPhoneText);
        phoneEditText.setText(userSessionManager.getUserName());
        saveButton = findViewById(R.id.save_button);
        ImageView backwardBtn = findViewById(R.id.backward);

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                setResult(RESULT_CANCELED);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhone = phoneEditText.getText().toString().trim();
                firestoreInstance.collection("Users")
                        .whereEqualTo("userUid", userSessionManager.getUserInformation())
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                queryDocumentSnapshots.getDocuments().get(0).getReference()
                                        .update("phone", newPhone)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(ChangePhonenumber.this, "Phone number saved: " + newPhone, Toast.LENGTH_SHORT).show();
                                            userSessionManager.loginUserName(newPhone);
                                            setResult(RESULT_OK);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(ChangePhonenumber.this, "Failed to update phone", Toast.LENGTH_SHORT).show());
                            } else {
                                Toast.makeText(ChangePhonenumber.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(ChangePhonenumber.this, e.toString(), Toast.LENGTH_SHORT).show());

            }
        });
    }
}
