package com.example.fududelivery.Home.FastFood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Home.FastFood.ItemFFList.ItemFFList;
import com.example.fududelivery.Home.FastFood.ItemFFList.ViewAdapter_ItemFFList;
import com.example.fududelivery.Home.FastFood.ItemFastFood.ItemFastFood;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class FastFood extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvFFoodList;
    ViewAdapter_ItemFFList viewAdapterFFoodList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_fastfood);
        spinner = findViewById(R.id.spinner_tab);
        rcvFilterList = findViewById(R.id.rcv_filterlist);
        rcvFFoodList = findViewById(R.id.rcv_fflist);
        viewAdapterFFoodList = new ViewAdapter_ItemFFList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFFoodList.setLayoutManager(linearLayoutManager1);
        rcvFFoodList.setAdapter(viewAdapterFFoodList);
        viewAdapterFFoodList.setData(getListFFood());
        rcvFFoodList.setAdapter(viewAdapterFFoodList);
        back_button = findViewById(R.id.back_btn);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FastFood.this, Customer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    private List<FilterbyList> getListFilter() {
        List<FilterbyList> List = new ArrayList<>();
        List<Filterby> listFilter = new ArrayList<>();
        listFilter.add(new Filterby("Categories & Store"));
        listFilter.add(new Filterby("Promotion"));
        List.add(new FilterbyList("Filter by:", listFilter));
        return List;
    }
    private List<ItemFFList> getListFFood() {
        List<ItemFFList> FFoodList = new ArrayList<>();

        List<ItemFastFood> listFFood = new ArrayList<>();
        listFFood.add(new ItemFastFood(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listFFood.add(new ItemFastFood(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listFFood.add(new ItemFastFood(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listFFood.add(new ItemFastFood(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listFFood.add(new ItemFastFood(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listFFood.add(new ItemFastFood(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listFFood.add(new ItemFastFood(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listFFood.add(new ItemFastFood(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listFFood.add(new ItemFastFood(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listFFood.add(new ItemFastFood(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listFFood.add(new ItemFastFood(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listFFood.add(new ItemFastFood(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        FFoodList.add(new ItemFFList("", listFFood));
        return FFoodList;
    }
}
