package com.example.fududelivery.Customer.MyOrder;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyOrderHistory extends Fragment {
    private RecyclerView recyclerView;
    private MyOrderHistoryAdapter adapter;
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private UserSessionManager userSessionManager;
    private final List<MyOrderInfor> historyorderList = new ArrayList<>();
    ProgressBar progressBar;

    public MyOrderHistory() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer_myorder_recycler, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userSessionManager = new UserSessionManager(requireContext());

        adapter = new MyOrderHistoryAdapter(getActivity(), historyorderList);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.load);

        fetchOrders();

        return view;
    }

    private void fetchOrders() {
        firebaseFirestore.collection("Orders").whereEqualTo("ShippingStatus", "Finish").whereEqualTo("UserID", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    historyorderList.clear();
                    for (final DocumentSnapshot documentSnapshot : task.getResult()) {
                        double subTotal = documentSnapshot.getDouble("subTotal");
                        double serviceFee = documentSnapshot.getDouble("serviceFee");
                        double shippingFee = documentSnapshot.getDouble("ShippingFee");
                        double totalAmount = subTotal + serviceFee + shippingFee;
                        String totalAmountString = String.format("%.2f", totalAmount);

                        firebaseFirestore.collection("Restaurant").whereEqualTo("ResID", documentSnapshot.getString("ResID")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                for (DocumentSnapshot documentSnapshot1 : task1.getResult()) {
                                    historyorderList.add(new MyOrderInfor(documentSnapshot.getId(), documentSnapshot1.getString("ImageID"), documentSnapshot.getString("Date"), documentSnapshot.getString("TotalQuantity") + " items", documentSnapshot1.getString("ResName"), totalAmountString, documentSnapshot.getString("ShippingStatus")));
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
