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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;



public class ChangePhonenumber extends AppCompatActivity {

    private TextView timerTextView;
    private Button resendButton;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = 30000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_changephone);

        TextInputLayout phoneNumberTextInputLayout = findViewById(R.id.phonenumber);
        Button sendButton = findViewById(R.id.save_button);
        TextInputEditText phoneNumberEditText = findViewById(R.id.phonenumbertextinput);
        TextView changephonenumberdescription = findViewById(R.id.changephonenumberdescription);
        LinearLayout verificationCodeContainer = findViewById(R.id.verificationCodeContainer);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberTextInputLayout.setVisibility(View.GONE);

                String phoneNumber = phoneNumberEditText.getText().toString();
                String message = "Verification code sent to: " + phoneNumber;
                changephonenumberdescription.setText(message);

                verificationCodeContainer.setVisibility(View.VISIBLE);

                List<EditText> editTexts = new ArrayList<>();
                editTexts.add(findViewById(R.id.editText1));
                editTexts.add(findViewById(R.id.editText2));
                editTexts.add(findViewById(R.id.editText3));
                editTexts.add(findViewById(R.id.editText4));
                editTexts.add(findViewById(R.id.editText5));
                editTexts.add(findViewById(R.id.editText6));

                for (int i = 0; i < editTexts.size(); i++) {
                    EditText currentEditText = editTexts.get(i);
                    currentEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    currentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                    final int finalI = i;
                    currentEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!TextUtils.isEmpty(s) && s.length() == 1) {
                                if (finalI < editTexts.size() - 1) {
                                    editTexts.get(finalI + 1).requestFocus();
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }

                timerTextView = findViewById(R.id.timerTextView);
                resendButton = findViewById(R.id.resendButton);

                timerTextView.setVisibility(View.VISIBLE);
                timerTextView.setTypeface(null, Typeface.BOLD); // Set text to bold

                startTimer();

                resendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!timerRunning) {
                            startTimer();
                            resendButton.setVisibility(View.GONE); // Hide resend button when clicked
                        }
                    }
                });
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

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