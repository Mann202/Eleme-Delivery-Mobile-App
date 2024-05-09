package com.example.fududelivery.Home.Dessert;



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
import com.example.fududelivery.Home.Dessert.ItemDessert.ViewAdapter_ItemDessert;
import com.example.fududelivery.Home.Dessert.ItemDessert.ItemDessert;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.Home.Dessert.ItemDessertList.ItemDessertList;
import com.example.fududelivery.Home.Dessert.ItemDessertList.ViewAdapter_ItemDessertList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class Dessert extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvDessertList;
    ViewAdapter_ItemDessertList viewAdapterDessertList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dessert);
        spinner = findViewById(R.id.spinner_tab_ds);
        rcvFilterList = findViewById(R.id.rcv_filterlist_ds);
        rcvDessertList = findViewById(R.id.rcv_dslist);
        viewAdapterDessertList = new ViewAdapter_ItemDessertList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvDessertList.setLayoutManager(linearLayoutManager1);
        rcvDessertList.setAdapter(viewAdapterDessertList);
        viewAdapterDessertList.setData(getListDessert());
        rcvDessertList.setAdapter(viewAdapterDessertList);
        back_button = findViewById(R.id.back_btn_ds);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.fududelivery.Home.Dessert.Dessert.this, Customer.class);
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
    private List<ItemDessertList> getListDessert() {
        List<ItemDessertList> DessertList = new ArrayList<>();

        List<ItemDessert> listDessert = new ArrayList<>();
        listDessert.add(new ItemDessert(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listDessert.add(new ItemDessert(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listDessert.add(new ItemDessert(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listDessert.add(new ItemDessert(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listDessert.add(new ItemDessert(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listDessert.add(new ItemDessert(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listDessert.add(new ItemDessert(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listDessert.add(new ItemDessert(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listDessert.add(new ItemDessert(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listDessert.add(new ItemDessert(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listDessert.add(new ItemDessert(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listDessert.add(new ItemDessert(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        DessertList.add(new ItemDessertList("", listDessert));
        return DessertList;
    }
}

