package com.example.fududelivery.Customer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fududelivery.HelperFirebase.FirebaseHelper;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeName extends AppCompatActivity {
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_changename);

        FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());

        AppCompatEditText nameEditText = findViewById(R.id.editNameField);
        nameEditText.setText(userSessionManager.getUserName());
        saveButton = findViewById(R.id.save_button);
        ImageView backwardBtn = findViewById(R.id.backward);

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEditText.getText().toString().trim();
                firestoreInstance.collection("Users")
                        .whereEqualTo("userUid", userSessionManager.getUserInformation())
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                queryDocumentSnapshots.getDocuments().get(0).getReference()
                                        .update("name", newName)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(ChangeName.this, "Name saved: " + newName, Toast.LENGTH_SHORT).show();
                                            userSessionManager.loginUserName(newName);
                                            setResult(RESULT_OK);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(ChangeName.this, "Failed to update name", Toast.LENGTH_SHORT).show());
                            } else {
                                Toast.makeText(ChangeName.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(ChangeName.this, e.toString(), Toast.LENGTH_SHORT).show());

            }
        });
    }
}
