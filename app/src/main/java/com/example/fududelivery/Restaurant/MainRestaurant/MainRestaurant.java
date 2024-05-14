package com.example.fududelivery.Restaurant.MainRestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.History.RestaurantHistory;
import com.example.fududelivery.Restaurant.Profile.RestaurantProfile;
import com.example.fududelivery.Restaurant.RestaurantMenu.RestaurantMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainRestaurant extends AppCompatActivity {
    private DrawerLayout drawer;
    private TextView imageViewMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrestaurant);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        TabsAdapter tabsAdapter = new TabsAdapter(this);
        tabsAdapter.addFragment(new RestaurantMainPreparingFragment(), "Prepairing");
        tabsAdapter.addFragment(new RestaurantMainDoneFragment(), "Done");

        viewPager.setAdapter(tabsAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabsAdapter.getPageTitle(position))
        ).attach();

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
                } else if(item.getItemId() == R.id.history) {
                    Intent historyIntent = new Intent(MainRestaurant.this, RestaurantHistory.class);
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
    }
}
