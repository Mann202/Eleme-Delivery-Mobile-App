package com.example.fududelivery.Shipper.ShipperMain;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fududelivery.GetStarted.ViewAdapter;
import com.example.fududelivery.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ShipperMain extends AppCompatActivity {

    ViewPager2 viewPager;
    ViewAdapter viewAdapter;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shippermain);

        bottomNavigationView = findViewById(R.id.menubar);
//        replaceFragment(new NewOrder());

        viewPager = findViewById(R.id.view_pager_shipper);
        fragmentArrayList.add(new NewOrder());
        fragmentArrayList.add(new OrderHistory());
        fragmentArrayList.add(new ShipperIncome());
        fragmentArrayList.add(new ShipperAccount());

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
        viewPager.setAdapter(adapterViewPager);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                         bottomNavigationView.setSelectedItemId(R.id.mnneworder);
                         break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.mnorderhistory);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.mnincome);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.mnaccount);
                        break;
                }

//                bottomNavigationView.setSelectedItemId(R.id.neworder);
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.mnneworder){
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.mnorderhistory) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.mnincome) {
                    viewPager.setCurrentItem(2);
                } else if (id == R.id.mnaccount) {
                    viewPager.setCurrentItem(3);
                }

                return true;
            }
        });

    }

}
