package com.example.fududelivery.Login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        TextInputEditText emailField = findViewById(R.id.emailField);
        TextView forgetpassworddescription = findViewById(R.id.forgetpassworddescription);
        ImageView backwardBtn = findViewById(R.id.backward);

        AppCompatButton sendEmailBtn = findViewById(R.id.sendEmailBtn);

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            forgetpassworddescription.setText(R.string.msg_your_verification_email_has_been_sent_to_your_email_address_please_check_your_email_and_change_your_new_password_if_you_don_t_receive_any_email_from_us_please_re_check_again_your_email_address_submission);
                            sendEmailBtn.setText(R.string.msg_re_send_email);
                        }
                    }
                });
            }
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendEmailBtn.setText(getString(R.string.msg_send));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
