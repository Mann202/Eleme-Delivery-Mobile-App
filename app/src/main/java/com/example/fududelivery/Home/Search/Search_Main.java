package com.example.fududelivery.Home.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.R;

public class Search_Main extends AppCompatActivity {
    AppCompatButton back_btn_search;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        back_btn_search = findViewById(R.id.back_btn_search);

        back_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Main.this, Customer.class);
                startActivity(intent);
            }
        });
    }

}
