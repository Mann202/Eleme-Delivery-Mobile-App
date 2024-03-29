package com.example.fududelivery.Restaurant;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fududelivery.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainRestaurant extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrestaurant);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        TabsAdapter tabsAdapter = new TabsAdapter(this);
        tabsAdapter.addFragment(new RestaurantMainPreparing(), "Prepairing");
        tabsAdapter.addFragment(new RestaurantMainDone(), "Done");

        viewPager.setAdapter(tabsAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabsAdapter.getPageTitle(position))
        ).attach();
    }
}
