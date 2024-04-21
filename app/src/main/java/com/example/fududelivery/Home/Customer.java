package com.example.fududelivery.Home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fududelivery.Customer.CustomerProfile;
import com.example.fududelivery.Customer.MainOrder;
import com.example.fududelivery.Customer.MyFavorite;
import com.example.fududelivery.Customer.SavedPlaces;
import com.example.fududelivery.Customer.TermAndCondition;
import com.example.fududelivery.ExploreList.ExploreList;
import com.example.fududelivery.ExploreList.ViewAdapter_ExploreList;
import com.example.fududelivery.ExploreTitle.Title;
import com.example.fududelivery.Food.Food;
import com.example.fududelivery.FoodList.FoodList;
import com.example.fududelivery.FoodList.ViewAdapter_FoodList;
import com.example.fududelivery.Home.FastFood.FastFood;
import com.example.fududelivery.R;
import com.google.android.material.navigation.NavigationView;

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
    private DrawerLayout drawer;
    RecyclerView expRCV;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        AppCompatButton settingButton = findViewById(R.id.settingButton);

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
        drawer = findViewById(R.id.drawer_layout);
        viewAdapterFoodList.setData(getListFood());
        rcvFoodList.setAdapter(viewAdapterFoodList);
        rcvExploreList.setLayoutManager(linearLayoutManager1);
        viewAdapterExploreList.setData(getListExplore());
        rcvExploreList.setAdapter(viewAdapterExploreList);
        mainGrid = findViewById(R.id.category_list);
        setSingleEvent(mainGrid);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int transitionIn = R.anim.slide_in_right;
                int transitionOut = R.anim.slide_out_left;

                int itemId = item.getItemId();

                if (itemId == R.id.myOrder) {
                    intent = new Intent(Customer.this, MainOrder.class);
                } else if (itemId == R.id.termAndCondition) {
                    intent = new Intent(Customer.this, TermAndCondition.class);
                } else if (itemId == R.id.myFavorites) {
                    intent = new Intent(Customer.this, MyFavorite.class);
                } else if (itemId == R.id.savePlaces) {
                    intent = new Intent(Customer.this, SavedPlaces.class);
                } else if (itemId == R.id.paymentManagement) {
                    intent = new Intent(Customer.this, MainOrder.class);
                } else if (itemId == R.id.myAccount) {
                    intent = new Intent(Customer.this, CustomerProfile.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    overridePendingTransition(transitionIn, transitionOut);
                }

                drawer.closeDrawer(GravityCompat.END);
                return true;
            }

        });
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

        FoodList.add(new FoodList("", listFood));
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

