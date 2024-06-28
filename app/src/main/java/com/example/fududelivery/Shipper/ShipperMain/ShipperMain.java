package com.example.fududelivery.Shipper.ShipperMain;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fududelivery.GetStarted.ViewAdapter;
import com.example.fududelivery.R;
import com.example.fududelivery.Service.Geocoding;
import com.example.fududelivery.Service.LocationHelper;
import com.example.fududelivery.Service.RestaurantNotificationService;
import com.example.fududelivery.Service.ShipperNotification;
import com.example.fududelivery.Shipper.ShipperAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ShipperMain extends AppCompatActivity {

    ViewPager2 viewPager;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    BottomNavigationView bottomNavigationView;
    private LocationHelper locationHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shippermain);

        Intent serviceIntent = new Intent(this, ShipperNotification.class);
        startService(serviceIntent);

        locationHelper = new LocationHelper(this);
        locationHelper.startLocationUpdates(new LocationHelper.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }
        });

        bottomNavigationView = findViewById(R.id.menubar);

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

    @Override
    protected void onPause() {
        super.onPause();
        locationHelper.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.startLocationUpdates(new LocationHelper.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationHelper.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationHelper.startLocationUpdates(new LocationHelper.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Log.v("Debug", "Location changed" + latitude + " " + longitude);
                    }
                });
            }
        }
    }
}
