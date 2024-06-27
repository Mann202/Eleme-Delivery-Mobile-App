package com.example.fududelivery.Restaurant.History;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Reference.ChangeCurrency;
import com.example.fududelivery.Restaurant.MainRestaurant.ItemDetailRestaurant;
import com.example.fududelivery.Restaurant.RestaurantDetail.ItemRestaurantOrder;
import com.example.fududelivery.Restaurant.RestaurantDetail.RestaurantItemAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class RestaurantHistory extends AppCompatActivity {
    private ArrayList<ItemDetailRestaurant> items;
    private RecyclerView recyclerView;
    private RestaurantHistoryAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firestoreInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restauranthistory);

        firestoreInstance = FirebaseFirestore.getInstance();

        swipeRefreshLayout = findViewById(R.id.refreshLayoutHistoryRestaurant);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerViewHistoryRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo dữ liệu giả
        initData();

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new RestaurantHistoryAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        items = new ArrayList<>();
        firestoreInstance.collection("Orders")
                .whereEqualTo("DeliveryStatus", "Done")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String orderId = document.getId();

                            items.add(new ItemDetailRestaurant(
                                    1,
                                    document.getString("Date"),
                                    document.getString("TotalQuantity") + " items",
                                    document.getString("name"),
                                    ChangeCurrency.formatPrice(document.getDouble("ShippingFee") + document.getDouble("serviceFee") + document.getDouble("subTotal")),
                                    document.getString("address"),
                                    orderId,
                                    document.getString("CusID"),
                                    document.getString("ShipperID")
                            ));

                            adapter.notifyDataSetChanged();
                            findViewById(R.id.loadingHistory).setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Debug", "Error getting Orders documents.", e);
                    }
                });
    }
}
