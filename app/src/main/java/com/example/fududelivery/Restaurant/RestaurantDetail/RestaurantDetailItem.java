package com.example.fududelivery.Restaurant.RestaurantDetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;

public class RestaurantDetailItem extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_restaurantprepairing);

        findViewById(R.id.navigateNextBtn).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetail.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}
