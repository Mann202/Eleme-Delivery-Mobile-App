package com.example.fududelivery.Home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fududelivery.ExploreList.ExploreList;
import com.example.fududelivery.ExploreList.ViewAdapter_ExploreList;
import com.example.fududelivery.ExploreTitle.Title;
import com.example.fududelivery.Food.Food;
import com.example.fududelivery.FoodList.FoodList;
import com.example.fududelivery.FoodList.ViewAdapter_FoodList;
import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class Customer extends AppCompatActivity {
    RecyclerView rcvFoodList;
    ViewAdapter_FoodList viewAdapterFoodList;
    ViewPager viewPager;
    ViewAdapter_Customer viewAdapter;
    RecyclerView rcvExploreList;
    ViewAdapter_ExploreList viewAdapterExploreList;

    GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_customer);

        viewPager = findViewById(R.id.banner);
        viewAdapter = new ViewAdapter_Customer(this);
        viewPager.setAdapter(viewAdapter);
        rcvFoodList = findViewById(R.id.rcv_foodlist);
        viewAdapterFoodList = new ViewAdapter_FoodList(this);
        rcvExploreList = findViewById(R.id.rcv_titlelist);
        viewAdapterExploreList = new ViewAdapter_ExploreList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFoodList.setLayoutManager(linearLayoutManager);
        viewAdapterFoodList.setData(getListFood());
        rcvFoodList.setAdapter(viewAdapterFoodList);
        rcvExploreList.setLayoutManager(linearLayoutManager1);
        viewAdapterExploreList.setData(getListExplore());
        rcvExploreList.setAdapter(viewAdapterExploreList);
        mainGrid = findViewById(R.id.category_list);
        setSingleEvent(mainGrid);
    }
    private void setSingleEvent (GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0) {
                        Intent intent = new Intent(Customer.this, FastFood.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Customer.this, "No items", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private List<FoodList> getListFood() {
        List<FoodList> FoodList = new ArrayList<>();

        List<Food> listFood = new ArrayList<>();
        listFood.add(new Food(R.drawable.dishes_01, "Food 01"));
        listFood.add(new Food(R.drawable.dishes_02, "Food 02"));
        listFood.add(new Food(R.drawable.dishes_03, "Food 03"));
        listFood.add(new Food(R.drawable.dishes_04, "Food 04"));

        FoodList.add(new FoodList("Popular in your area", listFood));
        return FoodList;
    }
private List<ExploreList> getListExplore() {
    List<ExploreList> ExploreList = new ArrayList<>();

    List<Title> listTitle = new ArrayList<>();
    listTitle.add(new Title("Food 01"));
    listTitle.add(new Title("Food 02"));
    listTitle.add(new Title("Food 04"));
    listTitle.add(new Title("Food 05"));
    listTitle.add(new Title("Food 01"));
    listTitle.add(new Title("Food 02"));
    listTitle.add(new Title("Food 04"));
    listTitle.add(new Title("Food 05"));
    ExploreList.add(new ExploreList("Explore more ", listTitle));
    return ExploreList;
}

}

