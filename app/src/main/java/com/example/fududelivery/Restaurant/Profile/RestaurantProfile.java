package com.example.fududelivery.Restaurant.Profile;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class RestaurantProfile extends AppCompatActivity {
    ImageView informationProfilePicturel;
    StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivity_restaurantprofile);

        FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());

        String imagePath = "IMG_1070.jpeg";
        informationProfilePicturel = findViewById(R.id.informationProfilePicture);
        loadImageFromFirestore(imagePath);

        Button logoutBtn = findViewById(R.id.logoutBtn);
        ImageView backward = findViewById(R.id.backIcon);
        AppCompatEditText emailField = findViewById(R.id.emailFieldRestaurant);
        emailField.setText(userSessionManager.getUserGmail());
        emailField.setEnabled(false);

        AppCompatEditText nameField = findViewById(R.id.nameFieldRestaurant);
        nameField.setText(userSessionManager.getUserName());

        AppCompatEditText addressField = findViewById(R.id.addressField);
        addressField.setText(userSessionManager.getUserAddress());
        addressField.setEnabled(false);

        AppCompatEditText startingDateField = findViewById(R.id.startingDateField);
        startingDateField.setText(userSessionManager.getUserStartingDate());
        startingDateField.setEnabled(false);

        AppCompatButton confirmBtn = findViewById(R.id.confirmRestaurantBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.sn_language_customer);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                switch (selectedLanguage) {
                    case "Chinese":
                        setLocale("zh");
                        break;
                    case "Vietnamese":
                        setLocale("vn");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameField.getText().toString().trim();

                firestoreInstance.collection("Users")
                        .whereEqualTo("userUid", userSessionManager.getUserInformation())
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                queryDocumentSnapshots.getDocuments().get(0).getReference()
                                        .update("name", newName)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(RestaurantProfile.this, getString(R.string.msg_name_saved) + newName, Toast.LENGTH_SHORT).show();
                                            userSessionManager.loginUserName(newName);
                                            setResult(RESULT_OK);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(RestaurantProfile.this, getString(R.string.msg_failed_to_update_name), Toast.LENGTH_SHORT).show());
                            } else {
                                Toast.makeText(RestaurantProfile.this, R.string.msg_user_not_found, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(RestaurantProfile.this, e.toString(), Toast.LENGTH_SHORT).show());
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSessionManager.logoutUser();
                Intent loginIntent = new Intent(RestaurantProfile.this, Login.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finishAffinity();
            }
        });

    }

    private void loadImageFromFirestore(String path) {
        StorageReference imageRef = storageReference.child(path);

        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri).into(informationProfilePicturel);
        }).addOnFailureListener(exception -> {
            exception.printStackTrace();
        });
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        Intent intent = new Intent(this, MainRestaurant.class);
        startActivity(intent);
        this.finish();
    }
}
