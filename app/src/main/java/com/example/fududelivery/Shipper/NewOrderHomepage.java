package com.example.fududelivery.Shipper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.Date;

public class NewOrderHomepage extends AppCompatActivity {
    final ArrayList<Order> orders = new ArrayList<Order>();
    private OrderAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder_homepage);

//        orders.add(order);
        for (int i = 0; i < 10; i++) {
            Order order = new Order("Nguyen", "Thu Duc", new Date(), i, (i + 1) * 10.0f);
            orders.add(order);
        }

        recyclerView = findViewById(R.id.rv_orders);
        adapter = new OrderAdapter(this, orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
