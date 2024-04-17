package com.example.fududelivery.GetStarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;
import com.example.fududelivery.Shipper.NewOrderHomepage;
import com.example.fududelivery.Shipper.ShipperMain;
import com.example.fududelivery.Shipper.ShipperProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        boolean firstRun = pref.getBoolean("firstRun", true);
        SharedPreferences.Editor editor = pref.edit();
        if (firstRun) {
            Log.i("debug:", "firstRunApp = true");
            editor.putBoolean("firstRun", false);
            editor.apply();
        } else {
            Log.i("debug:", "firstRunApp = false");
            if (user == null) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            } else {
                String userUid = user.getUid();
                firestoreInstance.collection("Users").whereEqualTo("userUid", userUid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String roleID = document.getString("role_id");
                            if (roleID != null) {
                                Log.d("Debug", "Role ID: " + roleID);

                                switch (roleID) {
                                    case "1":
                                        Intent intentRestaurant = new Intent(getApplicationContext(), MainRestaurant.class);
                                        startActivity(intentRestaurant);
                                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                                        return;
                                    case "3":
                                        Intent intentShipper = new Intent(getApplicationContext(), ShipperMain.class);
                                        startActivity(intentShipper);
                                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                                        return;
                                    default:
                                        // Xử lý khi roleID không phù hợp với các trường hợp trên
                                        break;
                                }
                            } else {
                                Log.d("Debug", "roleID is null");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Debug", "Error getting documents.", e);
                    }
                });
            }
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
