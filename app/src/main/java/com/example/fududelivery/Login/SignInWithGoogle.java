package com.example.fududelivery.Login;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fududelivery.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInWithGoogle {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    protected static final int RC_SIGN_IN = 9001;
    private Activity mActivity;

    public SignInWithGoogle(Activity activity) {
        Log.d("Debug", "Sign in with google step");
        mAuth = FirebaseAuth.getInstance();
        mActivity = activity;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        Log.d("Debug", "Sign in with google");
    }

    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("Debug", "Sign in with google next");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Debug", "onActivityResult");
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.d("Debug", "Google sign in failed", e);
                Toast.makeText(mActivity, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        Log.d("Debug", "firebaseAuthWithGoole");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(mActivity, "Sign in with Google successful", Toast.LENGTH_SHORT).show();

                        //Thay activity sau khi dang nhap thanh cong
                        Intent intent = new Intent(mActivity, VerifyEmail.class);
                        mActivity.startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Log.d("Debug", "signInWithCredential:failure", task.getException());
                        Toast.makeText(mActivity, "Sign in with Google failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
