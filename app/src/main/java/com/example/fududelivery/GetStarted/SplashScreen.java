package com.example.fududelivery.GetStarted;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;
import com.example.fududelivery.Shipper.ShipperMain;

public class SplashScreen extends AppCompatActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());

        if(sessionManager.isLoggedIn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (sessionManager.getUserRole()){
                        case "1":
                            Intent intentCustomer = new Intent(SplashScreen.this, Customer.class);
                            startActivity(intentCustomer);
                            break;
                        case "2":
                            Intent intentRestaurant = new Intent(SplashScreen.this, MainRestaurant.class);
                            startActivity(intentRestaurant);
                            break;
                        case "3":
                            Intent intentShipper = new Intent(SplashScreen.this, ShipperMain.class);
                            startActivity(intentShipper);
                            break;
                        default:
                    }
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
