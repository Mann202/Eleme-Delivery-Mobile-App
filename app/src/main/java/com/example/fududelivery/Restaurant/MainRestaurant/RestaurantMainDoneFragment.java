package com.example.fududelivery.Restaurant.MainRestaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Reference.ChangeCurrency;
import com.example.fududelivery.Restaurant.RestaurantDetail.RestaurantDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantMainDoneFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforDoneAdapter adapter;
    private ArrayList<ItemDetailRestaurant> restaurantList;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firestoreInstance;
    UserSessionManager userSessionManager;
    View view;
    private Boolean loadAgain = false;

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
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));

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

        TextView loadBtn = view.findViewById(R.id.load_btn);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                loadAgain = true;
            }
        });

        adapter.setOnItemClickListener(new RestaurantInforDoneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ItemDetailRestaurant item = restaurantList.get(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), RestaurantDetail.class);
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
        firestoreInstance.collection("Orders").whereEqualTo("ResID", userSessionManager.getUserInformation()).whereEqualTo("ResStatus", "Done").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    view.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.INVISIBLE);
                    LinearLayoutCompat loadMoreContainer = view.findViewById(R.id.ll_load_again);
                    TextView noOrdersTextView = view.findViewById(R.id.noOrdersTextView);
                    loadMoreContainer.setVisibility(View.VISIBLE);
                    noOrdersTextView.setText("Don't have any order");
                } else {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String orderId = document.getId();

                        restaurantList.add(new ItemDetailRestaurant(1, document.getString("Date"), document.getString("TotalQuantity") + " items", document.getString("name"), ChangeCurrency.formatPrice(document.getDouble("ShippingFee") + document.getDouble("serviceFee") + document.getDouble("subTotal")), document.getString("address"), orderId, document.getString("CusID"), document.getString("ShipperID")));

                        if (loadAgain != null && loadAgain) {
                            LinearLayoutCompat loadMoreContainer = view.findViewById(R.id.ll_load_again);
                            loadMoreContainer.setVisibility(View.INVISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                        view.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }
}
