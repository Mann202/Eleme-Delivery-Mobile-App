package com.example.fududelivery.Restaurant_Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Home.Search.Search_Main;
import com.example.fududelivery.R;

public class Restaurant_Home_Detail extends AppCompatActivity {
    TextView tv_restaurant_name;
    TextView tv_des;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Restaurant_Home item = (Restaurant_Home) bundle.get("object_item");
        tv_restaurant_name = findViewById(R.id.restaurant_name);
        tv_restaurant_name.setText(item.getResName());
        tv_des = findViewById(R.id.restaurant_type);
        tv_des.setText(item.getDes());
        back_btn = findViewById(R.id.nav_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Restaurant_Home_Detail.this, Customer.class);
                startActivity(intent);
            }
        });
    }
}