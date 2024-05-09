package com.example.fududelivery.Home.Drink;


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
import com.example.fududelivery.Home.Drink.ItemDrink.ItemDrink;
import com.example.fududelivery.Home.Drink.ItemDrinkList.ItemDrinkList;
import com.example.fududelivery.Home.Drink.ItemDrinkList.ViewAdapter_ItemDrList;
import com.example.fududelivery.Home.Drink.ItemDrink.ItemDrink;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class Drink extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvDrinkList;
    ViewAdapter_ItemDrList viewAdapterDrinkList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_drink);
        spinner = findViewById(R.id.spinner_tab);
        rcvFilterList = findViewById(R.id.rcv_filterlist_dr);
        rcvDrinkList = findViewById(R.id.rcv_drinklist);
        viewAdapterDrinkList = new ViewAdapter_ItemDrList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvDrinkList.setLayoutManager(linearLayoutManager1);
        rcvDrinkList.setAdapter(viewAdapterDrinkList);
        viewAdapterDrinkList.setData(getListDrink());
        rcvDrinkList.setAdapter(viewAdapterDrinkList);
        back_button = findViewById(R.id.back_btn_dr);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Drink.this, Customer.class);
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
    private List<ItemDrinkList> getListDrink() {
        List<ItemDrinkList> DrinkList = new ArrayList<>();

        List<ItemDrink> listDrink = new ArrayList<>();
        listDrink.add(new ItemDrink(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listDrink.add(new ItemDrink(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listDrink.add(new ItemDrink(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listDrink.add(new ItemDrink(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listDrink.add(new ItemDrink(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listDrink.add(new ItemDrink(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listDrink.add(new ItemDrink(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listDrink.add(new ItemDrink(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listDrink.add(new ItemDrink(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listDrink.add(new ItemDrink(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listDrink.add(new ItemDrink(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listDrink.add(new ItemDrink(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        DrinkList.add(new ItemDrinkList("", listDrink));
        return DrinkList;
    }
}
