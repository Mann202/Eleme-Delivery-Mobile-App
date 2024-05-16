package com.example.fududelivery.Shipper.ShipperOrderDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ShipperMain.ShipperMain;

public class OrderDetail extends AppCompatActivity {
    ImageView navback;
    TextView CusName;
    TextView CusAddress;
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
