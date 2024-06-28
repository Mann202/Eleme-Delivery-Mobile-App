package com.example.fududelivery.Login;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fududelivery.R;
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
                    Toast.makeText(mActivity, R.string.msg_login_with_google_successfully, Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Log.w("Debug", "signInWithEmail:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(mActivity, R.string.msg_email_not_registered, Toast.LENGTH_SHORT).show();
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(mActivity, R.string.msg_password_incorrect_double_check_again_your_password, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, R.string.msg_login_failed_please_try_again_later, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
