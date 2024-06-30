package com.example.fududelivery.Restaurant.MainRestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fududelivery.Login.RestaurantSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.History.RestaurantHistory;
import com.example.fududelivery.Restaurant.Profile.RestaurantProfile;
import com.example.fududelivery.Restaurant.RestaurantMenu.RestaurantMenu;
import com.example.fududelivery.Restaurant.RestaurantStatistic.RestaurantStatistic;
import com.example.fududelivery.Service.RestaurantNotificationService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainRestaurant extends AppCompatActivity {
    private DrawerLayout drawer;
    private TextView imageViewMenu;
    private RestaurantSessionManager restaurantSessionManager;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrestaurant);
        restaurantSessionManager = new RestaurantSessionManager(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        Switch switchButton = findViewById(R.id.switchExample);
        switchButton.setChecked(restaurantSessionManager.isActive());

        Intent serviceIntent = new Intent(this, RestaurantNotificationService.class);
        startService(serviceIntent);

        TabsAdapter tabsAdapter = new TabsAdapter(this);
        tabsAdapter.addFragment(new RestaurantMainPreparingFragment(), getString(R.string.msg_prepairing));
        tabsAdapter.addFragment(new RestaurantMainDoneFragment(), getString(R.string.msg_done));

        viewPager.setAdapter(tabsAdapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabsAdapter.getPageTitle(position))).attach();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu) {
                    Intent menuIntent = new Intent(MainRestaurant.this, RestaurantMenu.class);
                    startActivity(menuIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (item.getItemId() == R.id.profile) {
                    Intent profileIntent = new Intent(MainRestaurant.this, RestaurantProfile.class);
                    startActivity(profileIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (item.getItemId() == R.id.history) {
                    Intent historyIntent = new Intent(MainRestaurant.this, RestaurantHistory.class);
                    startActivity(historyIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (item.getItemId() == R.id.statistic) {
                    Intent historyIntent = new Intent(MainRestaurant.this, RestaurantStatistic.class);
                    startActivity(historyIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });

        imageViewMenu = findViewById(R.id.imageViewMenu);
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String userUid = mAuth.getUid();

                db.collection("Users").whereEqualTo("userUid", userUid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            if (snapshot.exists()) {
                                db.collection("Users").document(snapshot.getId()).update("isGettingNewOrder", isChecked).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        restaurantSessionManager.setIsActive(false);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

    }
}
