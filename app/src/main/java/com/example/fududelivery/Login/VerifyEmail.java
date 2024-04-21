package com.example.fududelivery.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmail extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private final int FIVE_SECONDS = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyemail);

        AppCompatButton resendBtn = findViewById(R.id.resendBtn);
        AppCompatButton continueBtn = findViewById(R.id.continueBtn);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null && user.isEmailVerified()) {
            resendBtn.setVisibility(View.GONE);
            continueBtn.setVisibility(View.VISIBLE);
        }

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(VerifyEmail.this, "Confirmation email has been sent! Please check your email.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(VerifyEmail.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.v("Debug", task.getException().toString());
                            }
                        }
                    });
                }
            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful() && user.isEmailVerified()) {
                                resendBtn.setVisibility(View.GONE);
                                continueBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(VerifyEmail.this, "Your account has been verified!", Toast.LENGTH_SHORT).show();
                                handler.removeCallbacks(runnable);
                            }
                        }
                    });
                }
                handler.postDelayed(this, FIVE_SECONDS);
            }
        };
        handler.post(runnable);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyEmail.this, Customer.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
    }
}
