package com.example.fududelivery.Restaurant.MainRestaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMainPreparingFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforPreparingAdapter adapter;
    private ArrayList<ItemDetailRestaurant> restaurantList;
    private FirebaseFirestore firestoreInstance;
    private UserSessionManager userSessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    public RestaurantMainPreparingFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_restaurantmainpreparing, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPreparingRestaurant);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayoutPreparingRestaurant);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.primary)
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getActivity().getApplicationContext());
        restaurantList = new ArrayList<ItemDetailRestaurant>();

        adapter = new RestaurantInforPreparingAdapter(getActivity(), restaurantList);
        recyclerView.setAdapter(adapter);

        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restaurantList.clear();
                adapter.notifyDataSetChanged();
                loadData();
            }
        });

        return view;
    }

    private void loadData() {
        firestoreInstance.collection("Orders")
                .whereEqualTo("ResID", userSessionManager.getUserInformation())
                .whereEqualTo("DeliveryStatus", "Prepare")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String orderId = document.getId();
                            firestoreInstance.collection("OrderDetail")
                                    .whereEqualTo("OrderID", orderId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot orderDetailSnapshots) {
                                            for (QueryDocumentSnapshot orderDetailDoc : orderDetailSnapshots) {
                                                Double quantity = orderDetailDoc.getDouble("Quantity");
                                                Double orderTotal = document.getDouble("OrderTotal");
                                                String formattedOrderTotal = String.format("%,.0f", orderTotal);
                                                String orderTotalWithCurrency = formattedOrderTotal + "VND";

                                                restaurantList.add(new ItemDetailRestaurant(
                                                        1,
                                                        document.getString("Date"),
                                                        quantity.toString() + " items",
                                                        document.getString("name"),
                                                        orderTotalWithCurrency,
                                                        document.getString("address")
                                                ));

                                            }
                                            adapter.notifyDataSetChanged();
                                            view.findViewById(R.id.loadingPreparingRestaurant).setVisibility(View.GONE);
                                            view.findViewById(R.id.refreshLayoutPreparingRestaurant).setVisibility(View.VISIBLE);
                                            swipeRefreshLayout.setRefreshing(false);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Debug", "Error getting OrderDetail documents.", e);
                                        }
                                    });
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
