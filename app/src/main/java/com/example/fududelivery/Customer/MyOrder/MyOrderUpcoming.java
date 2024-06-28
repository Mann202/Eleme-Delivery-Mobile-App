package com.example.fududelivery.Customer.MyOrder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.RestaurantInforPreparingAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyOrderUpcoming extends Fragment {
    private RecyclerView recyclerView;
    private MyOrderUpcomingAdapter adapter;
    private final List<MyOrderInfor> upcommingList = new ArrayList<>();
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private UserSessionManager userSessionManager;
    ProgressBar progressBar;

    public MyOrderUpcoming() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer_myorder_recycler, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userSessionManager = new UserSessionManager(requireContext());

        adapter = new MyOrderUpcomingAdapter(getActivity(), upcommingList);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.load);

        fetchOrders();

        return view;
    }

    private void fetchOrders() {
        firebaseFirestore.collection("Orders").whereEqualTo("UserID", userSessionManager.getUserInformation()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                upcommingList.clear();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    String shippingStatus = documentSnapshot.getString("ShippingStatus");
                    String resStatus = documentSnapshot.getString("ResStatus");
                    if ("Finish".equalsIgnoreCase(shippingStatus)) {
                        continue;
                    }

                    String status;

                    if (resStatus.equals("") && !shippingStatus.equals("Ready")) {
                        status = "Picking shipper...";
                    } else if (shippingStatus.equals("Start") && resStatus.equals("Prepare")) {
                        status = "Restaurant is preparing";
                    } else if (resStatus.equals("Done") && shippingStatus.equals("Start")) {
                        status = "By the way";
                    } else if (resStatus.equals("Done") && shippingStatus.equals("Cancel")) {
                        status = "Cancel";
                    } else {
                        status = "Finish";
                    }


                    double subTotal = documentSnapshot.getDouble("subTotal");
                    double serviceFee = documentSnapshot.getDouble("serviceFee");
                    double shippingFee = documentSnapshot.getDouble("ShippingFee");
                    double totalAmount = subTotal + serviceFee + shippingFee;
                    String totalAmountString = String.format("%.2f", totalAmount);


                    firebaseFirestore.collection("Restaurant").document(documentSnapshot.getString("ResID")).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            DocumentSnapshot documentSnapshot1 = task1.getResult();
                            upcommingList.add(new MyOrderInfor(documentSnapshot.getId(), documentSnapshot1.getString("ImageID"), documentSnapshot.getString("Date"), documentSnapshot.getString("TotalQuantity") + " items", documentSnapshot1.getString("ResName"), totalAmountString, status));
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

}
