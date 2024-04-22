package com.example.fududelivery.Shipper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.Profile.RestaurantProfile;

public class ShipperProfile extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_account);

        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());

        AppCompatButton logOutBtn = findViewById(R.id.logoutButton);
        // Spinner language
        String[] languages = {"English", "Vietnamese"};
        Spinner spinnerLanguage = findViewById(R.id.sn_language_shipper);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(languageAdapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (selectedLanguage.equals("English")) {
                } else if (selectedLanguage.equals("Vietnamese")) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Spinner theme
        String[] themes = {"Light", "Dark"};
        Spinner spinnerTheme = findViewById(R.id.sn_theme_shipper);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, themes);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(themeAdapter);

        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTheme = parent.getItemAtPosition(position).toString();
                // Thực hiện logic tương ứng với việc chọn chủ đề
                if (selectedTheme.equals("Light")) {
                    // Chọn chủ đề sáng
                } else if (selectedTheme.equals("Dark")) {
                    // Chọn chủ đề tối
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the logout confirmation popup
                userSessionManager.logoutUser();
                Intent loginIntent = new Intent(ShipperProfile.this, Login.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finishAffinity();
            }
        });
    }
}
