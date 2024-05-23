package com.example.fududelivery.GetStarted;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.LoginCaseManager;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;

public class SplashScreen extends AppCompatActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
        LoginCaseManager loginCaseManager = new LoginCaseManager(getApplicationContext());

        if(sessionManager.isLoggedIn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginCaseManager.loginWithRoleID(sessionManager.getUserRole());
                    finish();
                }
            }, 3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
}
