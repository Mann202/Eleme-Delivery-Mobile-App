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

public class SignInWithGoogle {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    protected static final int RC_SIGN_IN = 9001;
    private Activity mActivity;

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
        FirebaseFirestore firestoreInstance = getFirestoreInstance(mActivity);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(mActivity, "Sign in with Google successful", Toast.LENGTH_SHORT).show();

                        String userUid = user.getUid();

                        firestoreInstance.collection("Users").document(userUid)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                //Neu user da ton tai, query role id de chuyen vao intent
                                                firestoreInstance.collection("Users").whereEqualTo("userUid", userUid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                            String roleID = document.getString("role_id");
                                                            if (roleID != null) {
                                                                Log.d("Debug", "Role ID: " + roleID);

                                                                switch (roleID) {
                                                                    case "1":
                                                                        Intent intentRestaurant = new Intent(mActivity, MainRestaurant.class);
                                                                        mActivity.startActivity(intentRestaurant);
                                                                        mActivity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                                                                        break;
                                                                    case "3":
                                                                        Intent intentShipper = new Intent(mActivity, ShipperMain.class);
                                                                        mActivity.startActivity(intentShipper);
                                                                        mActivity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                                                                        break;
                                                                    default:
                                                                        // Xử lý khi roleID không phù hợp với các trường hợp trên
                                                                }
                                                            } else {
                                                                Log.d("Debug", "roleID is null");
                                                            }
                                                            Toast.makeText(mActivity, "Login successfully.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("Debug", "Error getting documents.", e);
                                                    }
                                                });
                                            } else {
                                                //User khong ton tai, them user moi vao firestore
                                                String name = "Sign-in Google";
                                                Map<String, Object> userData = new HashMap<>();
                                                userData.put("userUid", userUid);
                                                userData.put("name", name);
                                                userData.put("roleID", 1);

                                                firestoreInstance.collection("Users").add(userData)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.d("Debug", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("Debug", "Error adding document", e);
                                                            }
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(mActivity, "Sign-in with Google failed. Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        //Thay activity sau khi dang nhap thanh cong
                        Intent intent = new Intent(mActivity, MainRestaurant.class);
                        mActivity.startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Log.d("Debug", "signInWithCredential:failure", task.getException());
                        Toast.makeText(mActivity, "Sign in with Google failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
