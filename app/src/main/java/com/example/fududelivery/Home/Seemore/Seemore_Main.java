package com.example.fududelivery.Home.Seemore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.R;

import java.util.ArrayList;

public class Seemore_Main extends AppCompatActivity {
    ArrayList<Seemore> mSeemores;
    RecyclerView rcvSeemore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seemore_popular);

        AppCompatButton back_btn = findViewById(R.id.back_btn_seemore);
        rcvSeemore = findViewById(R.id.rcv_seemore);
        rcvSeemore.setHasFixedSize(true);
        rcvSeemore.setLayoutManager(new GridLayoutManager(this, 2));

        mSeemores = new ArrayList<Seemore>();
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        mSeemores.add(new Seemore(R.drawable.dishes_01, "Shaking Steak Tri-Tips", "4.8(113)", "13km"));
        ViewAdapter_Seemore viewadapter_seemore;
        viewadapter_seemore = new ViewAdapter_Seemore(mSeemores, this);
        rcvSeemore.setAdapter(viewadapter_seemore);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seemore_Main.this, Customer.class);
                startActivity(intent);
            }
        });
    }
}
