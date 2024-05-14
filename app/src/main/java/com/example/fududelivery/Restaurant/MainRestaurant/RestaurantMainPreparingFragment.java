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
import com.example.fududelivery.Reference.ChangeCurrency;
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

                            restaurantList.add(new ItemDetailRestaurant(
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
                            view.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
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
