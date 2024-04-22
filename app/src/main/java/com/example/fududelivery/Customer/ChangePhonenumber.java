package com.example.fududelivery.Customer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;



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
        AppCompatEditText phoneEditText = findViewById(R.id.editPhoneText);
        phoneEditText.setText(userSessionManager.getUserPhone());
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

            }
        });
    }}
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
<<<<<<< Updated upstream

            @Override
            public void onFinish() {
                timerRunning = false;
                timerTextView.setText("00:00");
                resendButton.setEnabled(true);
                resendButton.setVisibility(View.VISIBLE); // Show resend button when timer finishes
            }
        }.start();

        timerRunning = true;
        resendButton.setEnabled(false);
    }

    private void updateCountDownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText("Resend verification code: " + timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
=======
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
    }}
>>>>>>> Stashed changes
