package com.example.fududelivery.Restaurant_Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Home.Search.ItemSearch;
import com.example.fududelivery.Home.Search.Search_Main;
import com.example.fududelivery.Home.Search.ViewAdapter_ItemSearch;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Restaurant_Home_Detail extends AppCompatActivity {
    TextView tv_restaurant_name;
    RecyclerView rcvFood;
    TextView tv_des;
    TextView tv_distance;
    ViewAdapter_Food viewAdapter_Food;
    ImageView back_btn;
    ArrayList<Food> foodItemList;
    ChipGroup chipGroup;
    FirebaseFirestore dbroot;
    TextInputEditText txtSearch;
    String RestaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        ItemSearch item = (ItemSearch) bundle.get("object_item");
        dbroot = FirebaseFirestore.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFood = findViewById(R.id.food_menu);
        txtSearch = findViewById(R.id.finddishtextinput);
        RestaurantID = item.getResID();
        Log.e("Firestore error", RestaurantID);
        foodItemList = new ArrayList<>();
        viewAdapter_Food = new ViewAdapter_Food(foodItemList, this);
        rcvFood.setLayoutManager(linearLayoutManager);
        rcvFood.setHasFixedSize(true);
        rcvFood.setAdapter(viewAdapter_Food);
        tv_restaurant_name = findViewById(R.id.restaurant_name);
        tv_restaurant_name.setText(item.getResName());
        tv_des = findViewById(R.id.restaurant_type);
        tv_des.setText(item.getDescription());
        tv_distance = findViewById(R.id.dish_distance);
        tv_distance.setText(String.format(Locale.getDefault(), "%.2f km", item.getDistance()));
        back_btn = findViewById(R.id.nav_back);
        loadRestaurantData();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Restaurant_Home_Detail.this, Customer.class);
                startActivity(intent);
            }
        });
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchQuery = s.toString();

                // Perform filtering based on the search query
                filter(searchQuery);
            }
        });
    }
    private void loadRestaurantData() {
        CollectionReference FoodCollection = dbroot.collection("Food");
        FoodCollection.whereEqualTo("ResID", RestaurantID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Food foodItem = documentSnapshot.toObject(Food.class);
                    foodItemList.add(foodItem);
                }
                viewAdapter_Food.notifyDataSetChanged();
            }
        });
    }
    private void filter(String text) {
        ArrayList<Food> filteredList = new ArrayList<>();
        for (Food item : foodItemList) {
            if (item.getFoodName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewAdapter_Food.filterList(filteredList);
    }

}