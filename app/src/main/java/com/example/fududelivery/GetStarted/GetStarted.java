package com.example.fududelivery.GetStarted;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fududelivery.R;
import com.example.fududelivery.Service.MessageNotification;
import com.example.fududelivery.Service.RestaurantNotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class GetStarted extends AppCompatActivity {

    ViewPager viewPager;
    FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
    WormDotsIndicator dot3;
    ViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // For Splash Screen
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isFirstTime) {
            editor.putBoolean("isFirstTime", false);
            editor.apply();
        } else {
            startActivity(new Intent(GetStarted.this, SplashScreen.class));
            Intent messageService = new Intent(this, MessageNotification.class);
            startService(messageService);
            finish();
        }
        //End splash screen

        viewPager = findViewById(R.id.view_pager);
        dot3 = findViewById(R.id.dot3);

        viewAdapter = new ViewAdapter(this);
        viewPager.setAdapter(viewAdapter);
        dot3.setViewPager(viewPager);

        //Xu li su kien Next button
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < viewAdapter.getCount() - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    Intent intent = new Intent(v.getContext(), GetStartedSecond.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        //Xu li su kien Skip button
        findViewById(R.id.skip_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GetStartedSecond.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
