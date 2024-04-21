package com.example.fududelivery.Login;

import static androidx.core.content.ContextCompat.startActivity;

import static com.example.fududelivery.HelperFirebase.FirebaseHelper.getFirestoreInstance;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;
import com.example.fududelivery.Shipper.ShipperMain;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignInWithGoogle {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    protected static final int RC_SIGN_IN = 9001;
    private Activity mActivity;
    private String name;

    public SignInWithGoogle(Activity activity) {
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Debug", "onActivityResult");
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuthWithGoogle(account.getIdToken());
                        name = account.getDisplayName();
                    } catch (ApiException e) {
                        Log.d("Debug", "Google sign in failed", e);
                        Toast.makeText(mActivity, "Google sign in failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        Log.d("Debug", "firebaseAuthWithGoole");
        LoginCaseManager loginCaseManager = new LoginCaseManager(mActivity);
        UserSessionManager userSessionManager = new UserSessionManager(mActivity);
        FirebaseFirestore firestoreInstance = getFirestoreInstance(mActivity);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(mActivity, "Sign in with Google successful", Toast.LENGTH_SHORT).show();

                        String userUid = user.getUid();
                        firestoreInstance.collection("Users").whereEqualTo("userUid", userUid).get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        QuerySnapshot querySnapshot = task1.getResult();
                                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                            Log.v("Debug", "Document exists");
                                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                            String roleId = document.getString("roleId");
                                            if (roleId != null && !roleId.isEmpty()) {
                                                loginCaseManager.loginWithRoleID(roleId);
                                                userSessionManager.loginUserRole(roleId);
                                                userSessionManager.loginUserState();
                                                userSessionManager.loginUserInformation(userUid);
                                            } else {
                                                Toast.makeText(mActivity, "Error log-in with Google. Please contact us for further information!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Log.v("Debug", "Document does not exist");
                                            Map<String, Object> userData = new HashMap<>();
                                            userData.put("userUid", userUid);
                                            userData.put("name", name);
                                            userData.put("roleId", "1");

                                            firestoreInstance.collection("Users").add(userData)
                                                    .addOnSuccessListener(documentReference -> {
                                                        Log.d("Debug", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                        loginCaseManager.loginWithRoleID("1");
                                                        userSessionManager.loginUserRole("1");
                                                        userSessionManager.loginUserState();
                                                        userSessionManager.loginUserInformation(userUid);
                                                    })
                                                    .addOnFailureListener(e1 -> {
                                                        Log.w("Debug", "Error adding document", e1);
                                                    });
                                        }
                                    } else {
                                        Log.d("Debug", "Error getting documents: ", task1.getException());
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.v("Debug", e.toString());
                                });
                    } else {
                        Log.d("Debug", "signInWithCredential:failure", task.getException());
                        Toast.makeText(mActivity, "Sign in with Google failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
