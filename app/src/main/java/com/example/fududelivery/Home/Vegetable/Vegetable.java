package com.example.fududelivery.Home.Vegetable;


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
import com.example.fududelivery.Home.Vegetable.ItemVegetList.ItemVegetList;
import com.example.fududelivery.Home.Vegetable.ItemVegetList.ViewAdapter_ItemVegetList;
import com.example.fududelivery.Home.Vegetable.ItemVegetable.ItemVegetable;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class Vegetable extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvVegetList;
    ViewAdapter_ItemVegetList viewAdapterVegetList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_vegetable);
        spinner = findViewById(R.id.spinner_tab);
        rcvFilterList = findViewById(R.id.rcv_filterlist);
        rcvVegetList = findViewById(R.id.rcv_vegetlist);
        viewAdapterVegetList = new ViewAdapter_ItemVegetList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvVegetList.setLayoutManager(linearLayoutManager1);
        rcvVegetList.setAdapter(viewAdapterVegetList);
        viewAdapterVegetList.setData(getlistVegetable());
        rcvVegetList.setAdapter(viewAdapterVegetList);
        back_button = findViewById(R.id.back_btn_vg);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vegetable.this, Customer.class);
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
    private List<ItemVegetList> getlistVegetable() {
        List<ItemVegetList> VegetList = new ArrayList<>();

        List<ItemVegetable> listVegetable = new ArrayList<>();
        listVegetable.add(new ItemVegetable(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listVegetable.add(new ItemVegetable(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listVegetable.add(new ItemVegetable(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listVegetable.add(new ItemVegetable(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listVegetable.add(new ItemVegetable(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listVegetable.add(new ItemVegetable(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listVegetable.add(new ItemVegetable(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listVegetable.add(new ItemVegetable(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listVegetable.add(new ItemVegetable(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listVegetable.add(new ItemVegetable(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listVegetable.add(new ItemVegetable(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listVegetable.add(new ItemVegetable(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        VegetList.add(new ItemVegetList("", listVegetable));
        return VegetList;
    }
}
