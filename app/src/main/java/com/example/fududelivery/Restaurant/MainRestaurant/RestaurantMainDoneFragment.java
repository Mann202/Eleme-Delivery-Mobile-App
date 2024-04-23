package com.example.fududelivery.Restaurant.MainRestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.RestaurantDetail.RestaurantDetailItem;
import com.example.fududelivery.Shipper.OrderDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class RestaurantMainDoneFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforDoneAdapter adapter;
    private ArrayList<ItemDetailRestaurant> restaurantList;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firestoreInstance;
    UserSessionManager userSessionManager;
    View view;

    public RestaurantMainDoneFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_restaurantmaindone, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDoneRestaurant);
        restaurantList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = view.findViewById(R.id.refreshLayoutDoneRestaurant);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.primary)
        );

        firestoreInstance = FirebaseFirestore.getInstance();

        userSessionManager = new UserSessionManager(getActivity().getApplicationContext());


        adapter = new RestaurantInforDoneAdapter(getActivity(), restaurantList);
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

        adapter.setOnItemClickListener(new RestaurantInforDoneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ItemDetailRestaurant item = restaurantList.get(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), RestaurantDetailItem.class);
                intent.putExtra("OrderID", item.orderID);
                intent.putExtra("Address", item.adressText);
                intent.putExtra("Name", item.nameText);
                intent.putExtra("CustomerID", item.cusID);
                intent.putExtra("ShipperID", item.shipperID);
                startActivity(intent);
            }
        });

        return view;
    }


    private void loadData() {
        firestoreInstance.collection("Orders")
                .whereEqualTo("ResID", userSessionManager.getUserInformation())
                .whereEqualTo("DeliveryStatus", "Done")
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
                                                        document.getString("address"),
                                                        document.getString("OrderID"),
                                                        document.getString("CusID"),
                                                        document.getString("ShipperID")
                                                ));

                                            }
                                            adapter.notifyDataSetChanged();
                                            view.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
                                            swipeRefreshLayout.setVisibility(View.VISIBLE);
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
