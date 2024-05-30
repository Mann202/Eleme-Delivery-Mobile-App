package com.example.fududelivery.Home.SeaFood;



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
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.Home.SeaFood.ItemSeaFood.ItemSeaFood;
import com.example.fududelivery.Home.SeaFood.ItemSeaFoodList.ItemSFList;
import com.example.fududelivery.Home.SeaFood.ItemSeaFoodList.ViewAdapter_ItemSFList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class SeaFood extends AppCompatActivity implements View.OnClickListener {
    String[] items = {"Rating", "Near my"};
    ArrayAdapter<String> adapterItems;
    RecyclerView rcvFilterList;
    ViewAdapter_FilterbyList viewAdapterFilterbyList;
    private Button back_button;
    TextView spinner;
    RecyclerView rcvSeaFoodList;
    ViewAdapter_ItemSFList viewAdapterSeaFoodList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_seafood);
        spinner = findViewById(R.id.spinner_tab_sf);
        rcvFilterList = findViewById(R.id.rcv_filterlist_sf);
        rcvSeaFoodList = findViewById(R.id.rcv_sflist);
        viewAdapterSeaFoodList = new ViewAdapter_ItemSFList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSeaFoodList.setLayoutManager(linearLayoutManager1);
        rcvSeaFoodList.setAdapter(viewAdapterSeaFoodList);
        viewAdapterSeaFoodList.setData(getListSeaFood());
        rcvSeaFoodList.setAdapter(viewAdapterSeaFoodList);
        back_button = findViewById(R.id.back_btn_sf);
        rcvFilterList.setLayoutManager(linearLayoutManager);
        viewAdapterFilterbyList = new ViewAdapter_FilterbyList(this);
        viewAdapterFilterbyList.setData(getListFilter());
        rcvFilterList.setAdapter(viewAdapterFilterbyList);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeaFood.this, Customer.class);
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
    private List<ItemSFList> getListSeaFood() {
        List<ItemSFList> SeaFoodList = new ArrayList<>();

        List<ItemSeaFood> listSeaFood = new ArrayList<>();
        listSeaFood.add(new ItemSeaFood(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_16, "Pizza Company","Pizza - Pasta - Salad"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_17, "Lotteria","Burger - Chicken - Cake"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_18, "Spring Rolls Quyen","Burger - Chicken - Cake"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_19, "McDonalds","Burger - Chicken"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_20, "Banh Mi Huynh Hoa","Break"));
        listSeaFood.add(new ItemSeaFood(R.drawable.img_21, "Bun Thit Nuong Ba Sau","Break"));

        SeaFoodList.add(new ItemSFList("", listSeaFood));
        return SeaFoodList;
    }
}
