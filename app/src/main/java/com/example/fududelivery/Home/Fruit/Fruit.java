package com.example.fududelivery.Home.Fruit;



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
import com.example.fududelivery.Home.Fruit.ItemFruitList.ItemFruitList;
import com.example.fududelivery.Home.Fruit.ItemFruitList.ViewAdapter_ItemFruitList;
import com.example.fududelivery.Home.Fruit.ItemFruit.ItemFruit;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class Fruit extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvVegetList;
    ViewAdapter_ItemFruitList viewAdapterFruitList;;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_fruit);
        spinner = findViewById(R.id.spinner_tab_fr);
        rcvFilterList = findViewById(R.id.rcv_filterlist_fr);
        rcvVegetList = findViewById(R.id.rcv_frlist);
        viewAdapterFruitList = new ViewAdapter_ItemFruitList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvVegetList.setLayoutManager(linearLayoutManager1);
        rcvVegetList.setAdapter(viewAdapterFruitList);
        viewAdapterFruitList.setData(getlistFruit());
        rcvVegetList.setAdapter(viewAdapterFruitList);
        back_button = findViewById(R.id.back_btn_fr);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.fududelivery.Home.Fruit.Fruit.this, Customer.class);
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
    private List<ItemFruitList> getlistFruit() {
        List<ItemFruitList> VegetList = new ArrayList<>();

        List<ItemFruit> listFruit = new ArrayList<>();
        listFruit.add(new ItemFruit(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listFruit.add(new ItemFruit(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listFruit.add(new ItemFruit(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listFruit.add(new ItemFruit(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listFruit.add(new ItemFruit(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listFruit.add(new ItemFruit(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listFruit.add(new ItemFruit(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listFruit.add(new ItemFruit(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listFruit.add(new ItemFruit(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listFruit.add(new ItemFruit(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listFruit.add(new ItemFruit(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listFruit.add(new ItemFruit(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        VegetList.add(new ItemFruitList("", listFruit));
        return VegetList;
    }
}
