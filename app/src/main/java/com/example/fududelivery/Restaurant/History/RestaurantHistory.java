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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantHistory extends AppCompatActivity {
    private ArrayList<ItemDetailRestaurant> items;
    private RecyclerView recyclerView;
    private RestaurantHistoryAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firestoreInstance;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restauranthistory);

        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getApplicationContext());

        swipeRefreshLayout = findViewById(R.id.refreshLayoutHistoryRestaurant);

        recyclerView = findViewById(R.id.recyclerViewHistoryRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        initData();
        adapter = new RestaurantHistoryAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        firestoreInstance.collection("Orders").whereEqualTo("ShippingStatus", "Finish").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                items.clear();
                ArrayList<String> restaurantIds = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    restaurantIds.add(document.getId());
                }

                if (!restaurantIds.isEmpty()) {
                    firestoreInstance.collection("Restaurant").whereEqualTo("ResID", userSessionManager.getUserInformation()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot task) {
                            for (DocumentSnapshot doc : task) {
                                for (String orderId : restaurantIds) {
                                    QueryDocumentSnapshot document = getOrderById(queryDocumentSnapshots, orderId);
                                    items.add(new ItemDetailRestaurant(doc.getString("ImageID"), document.getString("Date"), document.getString("TotalQuantity") + " items", document.getString("name"), ChangeCurrency.formatPrice(document.getDouble("ShippingFee") + document.getDouble("serviceFee") + document.getDouble("subTotal")), document.getString("address"), orderId, document.getString("CusID"), document.getString("ShipperID")));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            findViewById(R.id.loadingHistory).setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Debug", "Error getting Restaurant documents.", e);
                        }
                    });
                } else {
                    adapter.notifyDataSetChanged();
                    findViewById(R.id.loadingHistory).setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Debug", "Error getting Orders documents.", e);
            }
        });
    }

    private QueryDocumentSnapshot getOrderById(QuerySnapshot queryDocumentSnapshots, String orderId) {
        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
            if (document.getId().equals(orderId)) {
                return document;
            }
        }
        return null;
    }
}