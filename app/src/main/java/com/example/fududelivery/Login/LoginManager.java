package com.example.fududelivery.Login;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginManager {
    private Activity mActivity;

    public LoginManager(Activity activity) {
        mActivity = activity;
    }

    public void signInWithEmailAndPassword(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Debug", "signInWithEmail:success");
                    Toast.makeText(mActivity, "Login with google successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Log.w("Debug", "signInWithEmail:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(mActivity, "Email not registered.", Toast.LENGTH_SHORT).show();
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(mActivity, "Password incorrect, double-check again your password.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, "Login failed. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
