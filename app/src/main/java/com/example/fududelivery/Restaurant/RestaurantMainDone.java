package com.example.fududelivery.Restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMainDone extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforDoneAdapter adapter;
    private ArrayList<itemRestaurant> restaurantList;

    public RestaurantMainDone() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurantmaindone, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize restaurantList (you may fetch data from somewhere)
        List<itemRestaurant> restaurantList = new ArrayList<itemRestaurant>();
        restaurantList.add(new itemRestaurant(1, "28/1", "3" + " items", "Truong Gia Man", "100000", "KTX khu A DHQG TPHCM"));
        restaurantList.add(new itemRestaurant(2, "30/1", "4" + " items", "le Xuan Quynh", "20000", "KTX khu B DHQG TPHCM"));
        restaurantList.add(new itemRestaurant(3, "2/2", "8" + " items", "Nguyen Dai Duong", "500000", "KTX khu C DHQG TPHCM"));
        restaurantList.add(new itemRestaurant(4, "4/8", "1" + " items", "Nguyen Dai Duong", "1000000", "KTX khu D DHQG TPHCM"));
        restaurantList.add(new itemRestaurant(5, "30/12", "2" + " items", "le Xuan Quynh", "242353264", "KTX khu E DHQG TPHCM"));

        // Initialize Adapter
        adapter = new RestaurantInforDoneAdapter(getActivity(), restaurantList);
        recyclerView.setAdapter(adapter);

        // Populate restaurantList with data (optional)
        // populateRestaurantList();

        return view;
    }

    // Method to populate restaurantList with data (if needed)
    private void populateRestaurantList() {
        // Fetch data from source and add to restaurantList
        // For example:
        // restaurantList.add(new Restaurant(...));
        // restaurantList.add(new Restaurant(...));
        // adapter.setRestaurantList(restaurantList);
    }
}
