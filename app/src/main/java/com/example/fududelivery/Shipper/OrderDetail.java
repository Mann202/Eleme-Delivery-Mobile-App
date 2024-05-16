package com.example.fududelivery.Shipper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fududelivery.GetStarted.GetStartedSecond;
import com.example.fududelivery.R;
import com.google.android.material.navigation.NavigationBarView;

public class OrderDetail extends AppCompatActivity {
    ImageView navback;
    TextView CusName, CusAddress;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        CusName = findViewById(R.id.tv_ordername);
        CusAddress = findViewById(R.id.tv_orderaddress);
        navback = findViewById(R.id.nav_back);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            CusName.setText(bundle.getString("Name"));
            CusAddress.setText(bundle.getString("Address"));
        }

        navback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShipperMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }


}
