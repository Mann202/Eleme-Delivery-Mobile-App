package com.example.fududelivery.Home.Bakery;

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

import com.example.fududelivery.Home.Bakery.ItemBakeryList.ItemBakeryList;
import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.Home.Bakery.ItemBakery.ItemBakery;
import com.example.fududelivery.Home.Bakery.ItemBakeryList.ItemBakeryList;
import com.example.fududelivery.Home.Bakery.ItemBakeryList.ViewAdapter_ItemBakeryList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class Bakery extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvBakeryList;
    ViewAdapter_ItemBakeryList viewAdapterBakeryList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_bakery);
        spinner = findViewById(R.id.spinner_tab_bk);
        rcvFilterList = findViewById(R.id.rcv_filterlist_bk);
        rcvBakeryList = findViewById(R.id.rcv_bklist);
        viewAdapterBakeryList = new ViewAdapter_ItemBakeryList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvBakeryList.setLayoutManager(linearLayoutManager1);
        rcvBakeryList.setAdapter(viewAdapterBakeryList);
        viewAdapterBakeryList.setData(getListBakery());
        rcvBakeryList.setAdapter(viewAdapterBakeryList);
        back_button = findViewById(R.id.back_btn_bk);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.fududelivery.Home.Bakery.Bakery.this, Customer.class);
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
    private List<ItemBakeryList> getListBakery() {
        List<ItemBakeryList> BakeryList = new ArrayList<>();

        List<ItemBakery> listBakery = new ArrayList<>();
        listBakery.add(new ItemBakery(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listBakery.add(new ItemBakery(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listBakery.add(new ItemBakery(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listBakery.add(new ItemBakery(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listBakery.add(new ItemBakery(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listBakery.add(new ItemBakery(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listBakery.add(new ItemBakery(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listBakery.add(new ItemBakery(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listBakery.add(new ItemBakery(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listBakery.add(new ItemBakery(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listBakery.add(new ItemBakery(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listBakery.add(new ItemBakery(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        BakeryList.add(new ItemBakeryList("", listBakery));
        return BakeryList;
    }
}

