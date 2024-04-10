package com.example.fududelivery.Login;

import static android.content.ContentValues.TAG;

import static com.example.fududelivery.HelperFirebase.FirebaseHelper.getFirestoreInstance;
import static com.example.fududelivery.Login.PasswordHash.hashPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseFirestore firestoreInstance = getFirestoreInstance(this);

        AppCompatButton loginBtn = findViewById(R.id.loginBtn);

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
                TextInputEditText passwordField = findViewById(R.id.passwordField);
                TextInputEditText phoneNumberField = findViewById(R.id.phoneNumberField);

                String password = passwordField.getText().toString();
                String passwordHashed = hashPassword(passwordField.getText().toString());
                String phoneNumber = phoneNumberField.getText().toString();

                if (isValidPhoneNumber(phoneNumber)) {
                    if (isValidPassword(password)) {
                        Toast.makeText(Login.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Password must contain 8 characters!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Phone number must contain 8 characters!", Toast.LENGTH_SHORT).show();
                }

                firestoreInstance.collection("Users")
                        .whereEqualTo("phone", phoneNumber)
                        .whereEqualTo("password", passwordHashed)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("Login", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d("Login", "Error getting documents: ", task.getException());
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
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "\\d{8}";
        return !TextUtils.isEmpty(phoneNumber) && phoneNumber.matches(phoneNumberRegex);
    }
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return !TextUtils.isEmpty(password) && password.matches(passwordRegex);
    }
}
