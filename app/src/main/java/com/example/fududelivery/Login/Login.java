package com.example.fududelivery.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.HelperFirebase.FirebaseHelper;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    private SignInWithGoogle signInWithGoogle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        AppCompatButton loginBtn = findViewById(R.id.loginBtn);
        ImageView googleLogin = findViewById(R.id.googleLogo);
        signInWithGoogle = new SignInWithGoogle(this);

        String[] item = {"Customer", "Restaurant", "Shipper"};
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    currentUser.reload();
                }

                TextInputEditText passwordField = findViewById(R.id.passwordField);
                TextInputEditText emailField = findViewById(R.id.emailField);

                String password = passwordField.getText().toString();
                String email = emailField.getText().toString();

                LoginManager loginManager = new LoginManager(Login.this);
                loginManager.signInWithEmailAndPassword(email, password);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SignInWithGoogle.RC_SIGN_IN) {
            // Gọi phương thức onActivityResult() của SignInWithGoogle và truyền kết quả trả về từ Intent đăng nhập
            signInWithGoogle.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}
