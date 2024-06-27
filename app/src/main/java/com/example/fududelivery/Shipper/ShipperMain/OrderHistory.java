package com.example.fududelivery.Shipper.ShipperMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.UserSessionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.Model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    final ArrayList<Order> orders = new ArrayList<Order>();
    private OrderAdapter adapter;
    RecyclerView recyclerView;
    UserSessionManager userSessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    static FirebaseFirestore firestoreInstance;
    RelativeLayout NoOrder;
    public OrderHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderHistory newInstance(String param1, String param2) {
        OrderHistory fragment = new OrderHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(requireContext());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_history, container, false);
        userSessionManager = new UserSessionManager(getActivity().getApplicationContext());

        CollectionReference orderCollection = firestoreInstance.collection("Orders");
        orderCollection
                .whereEqualTo("ShippingStatus", "Finish")
                .whereEqualTo("ShipperID", userSessionManager.getUserInformation())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        NoOrder = rootView.findViewById(R.id.no_orders);
                        if (queryDocumentSnapshots.isEmpty()) {
                            rootView.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                            NoOrder.setVisibility(View.VISIBLE);
                        }

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Order order = documentSnapshot.toObject(Order.class);
                            String documentId = documentSnapshot.getId();
//                            SimpleDateFormat format = new SimpleDateFormat("d MMM, HH:mm");
//                            String formatDate = format.format(order.getDate());
//                            Order saveOrder = new Order(documentId, order.getName(), order.getAddress(), order.getDate(), order.getTotalQuantity(), order.getOrderTotal());
                            order.setOrderID(documentId);
                            System.out.println("Query Order: " + order);

                            // TODO: Xử lý dữ liệu Order ở đây
                            orders.add(order);

                            recyclerView = rootView.findViewById(R.id.rv_orders);
                            adapter = new OrderAdapter(requireActivity(), orders);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            recyclerView.setAdapter(adapter);

                            rootView.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi truy vấn thất bại
                    }
                });

        for (Order order : orders) {
            System.out.println("Order:" + order);
        }

        recyclerView = rootView.findViewById(R.id.rv_orders);
        adapter = new OrderAdapter(requireActivity(), orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = rootView.findViewById(R.id.refreshLayoutDoneRestaurant);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.primary)
        );

        NoOrder = rootView.findViewById(R.id.no_orders);
        if (orders.isEmpty()) {
            NoOrder.setVisibility(View.GONE);
        }

        return rootView;
//        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }
}