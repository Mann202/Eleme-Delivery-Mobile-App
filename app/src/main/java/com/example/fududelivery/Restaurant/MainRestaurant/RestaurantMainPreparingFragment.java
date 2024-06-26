package com.example.fududelivery.Restaurant.MainRestaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Reference.ChangeCurrency;
import com.example.fududelivery.Restaurant.RestaurantDetail.RestaurantDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantMainPreparingFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforPreparingAdapter adapter;
    private ArrayList<ItemDetailRestaurant> restaurantList;
    private FirebaseFirestore firestoreInstance;
    private UserSessionManager userSessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private Boolean loadAgain = false;

    public RestaurantMainPreparingFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_restaurantmainpreparing, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPreparingRestaurant);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayoutPreparingRestaurant);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getActivity().getApplicationContext());
        restaurantList = new ArrayList<ItemDetailRestaurant>();

        adapter = new RestaurantInforPreparingAdapter(getActivity(), restaurantList);
        recyclerView.setAdapter(adapter);

        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
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
                Intent intent = new Intent(getActivity().getApplicationContext(), RestaurantDetail.class);
                intent.putExtra("OrderID", item.orderID);
                intent.putExtra("Address", item.adressText);
                intent.putExtra("Name", item.nameText);
                intent.putExtra("CustomerID", item.cusID);
                intent.putExtra("ShipperID", item.shipperID);
                startActivity(intent);
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

        return view;
    }

    private void loadData() {
        firestoreInstance.collection("Orders").whereEqualTo("ResID", userSessionManager.getUserInformation()).whereEqualTo("ResStatus", "Prepare").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    view.findViewById(R.id.loadingPreparingRestaurant).setVisibility(View.INVISIBLE);
                    LinearLayoutCompat loadMoreContainer = view.findViewById(R.id.ll_load_again);
                    TextView noOrdersTextView = view.findViewById(R.id.noOrdersTextView);
                    loadMoreContainer.setVisibility(View.VISIBLE);
                    noOrdersTextView.setText(R.string.msg_don_t_have_any_order);
                } else {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String orderId = document.getId();
                        firestoreInstance.collection("Restaurant").whereEqualTo("ResID", userSessionManager.getUserInformation()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots.getDocuments()) {
                                    restaurantList.add(new ItemDetailRestaurant(documentSnapshot1.getString("ImageID"), document.getString("Date"), document.getString("TotalQuantity") + " items", document.getString("name"), ChangeCurrency.formatPrice(document.getDouble("ShippingFee") + document.getDouble("serviceFee") + document.getDouble("subTotal")), document.getString("address"), orderId, document.getString("CusID"), document.getString("ShipperID")));
                                    adapter.notifyDataSetChanged();
                                    view.findViewById(R.id.loadingPreparingRestaurant).setVisibility(View.GONE);
                                    if (loadAgain != null && loadAgain) {
                                        LinearLayoutCompat loadMoreContainer = view.findViewById(R.id.ll_load_again);
                                        loadMoreContainer.setVisibility(View.INVISIBLE);
                                    }
                                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}