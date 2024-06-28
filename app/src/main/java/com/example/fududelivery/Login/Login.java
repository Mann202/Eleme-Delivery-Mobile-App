package com.example.fududelivery.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.fududelivery.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Objects;

public class Login extends AppCompatActivity {

    private SignInWithGoogle signInWithGoogle;
    AppCompatButton loginButton;
    TextInputEditText passwordField;
    TextInputEditText emailField;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private String email;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
        LoginCaseManager loginCaseManager = new LoginCaseManager(getApplicationContext());
        RestaurantSessionManager restaurantSessionManager = new RestaurantSessionManager(getApplicationContext());

        FacebookSdk.setApplicationId("283740247620840");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mCallbackManager = CallbackManager.Factory.create();
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        AppCompatButton loginBtn = findViewById(R.id.loginBtn);
        ImageView googleLogin = findViewById(R.id.googleLogo);
        ImageView facebookLogin = findViewById(R.id.facebookLogo);
        ImageView xLogin = findViewById(R.id.XLogo);
        ImageView phoneLogin = findViewById(R.id.phoneLogo);
        CheckBox rememberMeBox = findViewById(R.id.checkbox_remember_me);

        passwordField = findViewById(R.id.passwordField);
        emailField = findViewById(R.id.emailField);
        loginButton = findViewById(R.id.loginBtn);
        emailField.addTextChangedListener(textWatcher);
        passwordField.addTextChangedListener(textWatcher);

        signInWithGoogle = new SignInWithGoogle(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordField.getText().toString();
                email = emailField.getText().toString();


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            String Uid = user.getUid();

                            sessionManager.loginUserState();
                            sessionManager.loginUserInformation(Uid);

                            firestoreInstance.collection("Users").whereEqualTo("userUid", Uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        String roleID = document.getString("roleId");
                                        String name = document.getString("name");
                                        String email = document.getString("email");
                                        String phone = document.getString("phone");
                                        String address = document.getString("address");

                                        sessionManager.loginUserGmail(email);
                                        sessionManager.loginUserName(name);
                                        sessionManager.loginUserPhone(phone);
                                        sessionManager.setAddress(address);

                                        if (Objects.equals(roleID, "2")) {
                                            String startingDate = document.getString("startingDate");
                                            Boolean isActive = document.getBoolean("isGettingNewOrder");

                                            restaurantSessionManager.setIsActive(isActive);
                                            sessionManager.setStartingDate(startingDate);
                                            sessionManager.setAddress(address);
                                        }

                                        if (Objects.equals(roleID, "3")) {
                                            String vehicleInformation = document.getString("vehicleInformation");

                                            sessionManager.setVehicleInformation(vehicleInformation);
                                        }

                                        if (roleID != null) {
                                            Log.d("Debug", "Role ID: " + roleID);
                                            sessionManager.loginUserRole(roleID);
                                            loginCaseManager.loginWithRoleID(roleID);
                                        } else {
                                            Log.d("Debug", "roleID is null");
                                        }
                                        Toast.makeText(Login.this, getString(R.string.msg_login_successfully), Toast.LENGTH_SHORT).show();
                                    }
                                    finishAffinity();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Debug", "Error getting documents.", e);
                                }
                            });
                        } else {
                            Toast.makeText(Login.this, R.string.msg_authentication_failed_please_check_your_password_or_email_again, Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
            }
        });

        findViewById(R.id.forgetpassword).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ForgetPassword.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        findViewById(R.id.signuptext).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SignUp.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle.signInWithGoogle();
            }
        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, getString(R.string.msg_facebook_login_canceled), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("Facebook login error:", exception.getMessage());
                    }
                });
            }
        });

        phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for phone login
            }
        });

        xLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    pendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            Log.v("Debug", authResult.getAdditionalUserInfo().getProfile().toString());
                            // The OAuth access token can also be retrieved:
                            // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                            // The OAuth secret can be retrieved by calling:
                            // ((OAuthCredential)authResult.getCredential()).getSecret().
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("Debug", "failed sign-in with X");
                        }
                    });
                } else {
                    mAuth.startActivityForSignInWithProvider(/* activity= */ Login.this, provider.build()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            // User is signed in.
                            // IdP data available in
                            Log.v("Debug", authResult.getAdditionalUserInfo().getProfile().toString());
                            // The OAuth access token can also be retrieved:
                            // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                            // The OAuth secret can be retrieved by calling:
                            // ((OAuthCredential)authResult.getCredential()).getSecret().
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("Debug", "Sign in X failed");
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SignInWithGoogle.RC_SIGN_IN) {
            signInWithGoogle.onActivityResult(requestCode, resultCode, data);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Unused
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean isEmailValid = emailField.getText().toString().length() > 10;
            boolean isPasswordValid = passwordField.getText().toString().length() >= 8;

            loginButton.setEnabled(isEmailValid && isPasswordValid);
            if (loginButton.isEnabled()) {
                loginButton.setTextColor(ContextCompat.getColor(Login.this, R.color.white));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Unused
        }
    };

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookLogin", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("FacebookLogin", "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.w("FacebookLogin", "signInWithCredential:failure", task.getException());
                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
        } else {
            // User is signed out
        }
    }
}
