package com.example.fududelivery.Customer.OrderDetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.example.fududelivery.Customer.OrderDetail.MyOrderDetailItem;

public class MyOrderDetailItem extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_customer_myorderhistory);

        findViewById(R.id.navigateNextBtn).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OrderDetail.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}
