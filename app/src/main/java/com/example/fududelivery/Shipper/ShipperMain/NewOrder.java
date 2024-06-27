package com.example.fududelivery.Shipper.ShipperMain;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fududelivery.R;
import com.example.fududelivery.Service.GeocodeCallback;
import com.example.fududelivery.Service.GeocodeTask;
import com.example.fududelivery.Service.Geocoding;
import com.example.fududelivery.Service.LocationHelper;
import com.example.fududelivery.Service.RestaurantNotificationService;
import com.example.fududelivery.Shipper.Model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewOrder extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    final ArrayList<Order> orders = new ArrayList<>();
    private OrderAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    static FirebaseFirestore firestoreInstance;
    private RelativeLayout NoOrder;
    private LocationHelper locationHelper;

    private double latitude;
    private double longitude;

    public NewOrder() {
    }

    public static NewOrder newInstance(String param1, String param2) {
        NewOrder fragment = new NewOrder();
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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_order, container, false);

        recyclerView = rootView.findViewById(R.id.rv_orders);
        adapter = new OrderAdapter(requireActivity(), orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = rootView.findViewById(R.id.refreshLayoutShipperOrder);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchOrders(rootView);
            }
        });

        NoOrder = rootView.findViewById(R.id.no_orders);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        fetchOrders(rootView);

        return rootView;
    }

    private void fetchOrders(View rootView) {
        CollectionReference orderCollection = firestoreInstance.collection("Orders");
        List<String> statusList = Arrays.asList("Ready", "Start", "Wait");
        orderCollection.whereIn("ShippingStatus", statusList).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                NoOrder = rootView.findViewById(R.id.no_orders);
                orders.clear();
                adapter.notifyDataSetChanged(); // Xóa dữ liệu cũ

                if (queryDocumentSnapshots.isEmpty()) {
                    rootView.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    NoOrder.setVisibility(View.VISIBLE);
                } else {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Order order = documentSnapshot.toObject(Order.class);
                        String documentId = documentSnapshot.getId();
                        order.setOrderID(documentId);

                        processOrder(order, new double[]{latitude, longitude});
                    }

                    adapter.notifyDataSetChanged();

                    rootView.findViewById(R.id.loadingDoneRestaurant).setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    NoOrder.setVisibility(orders.isEmpty() ? View.VISIBLE : View.GONE);
                }
            }
        });
    }

    private void startLocationUpdates() {
        locationHelper = new LocationHelper(getContext());
        locationHelper.startLocationUpdates(new LocationHelper.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationHelper.stopLocationUpdates();
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            startLocationUpdates();
        } else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    });

    public void processOrder(final Order order, final double[] shipperLocation) {
        String restaurantAddress = order.getResAddress();
        new GeocodeTask(new GeocodeCallback() {
            @Override
            public void onGeocodeResult(double[] coordinates) {
                if (coordinates != null) {
                    double latitude = coordinates[0];
                    double longitude = coordinates[1];
                    double distance = Geocoding.calculateDistance(shipperLocation[0], shipperLocation[1], latitude, longitude);
                    Log.v("Debug", String.valueOf(distance));
                    if (distance < 10.0) {
                        orders.add(order);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.v("Debug", "Order is too far");
                    }
                } else {
                    Log.v("Debug", "Failed to get coordinates");
                }
            }
        }).execute(restaurantAddress);
    }
}
