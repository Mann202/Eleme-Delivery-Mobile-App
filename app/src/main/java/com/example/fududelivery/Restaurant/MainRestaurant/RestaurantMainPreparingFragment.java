package com.example.fududelivery.Restaurant.MainRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMainPreparingFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforPreparingAdapter adapter;
    private ArrayList<ItemDetailRestaurant> restaurantList;
    public RestaurantMainPreparingFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurantmaindone, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ItemDetailRestaurant> restaurantList = new ArrayList<ItemDetailRestaurant>();
        restaurantList.add(new ItemDetailRestaurant(1, "28/1", "3" + " items", "Truong Gia Man", "100000", "KTX khu A DHQG TPHCM"));
        restaurantList.add(new ItemDetailRestaurant(2, "30/1", "4" + " items", "le Xuan Quynh", "20000", "KTX khu B DHQG TPHCM"));
        restaurantList.add(new ItemDetailRestaurant(3, "2/2", "8" + " items", "Nguyen Dai Duong", "500000", "KTX khu C DHQG TPHCM"));
        restaurantList.add(new ItemDetailRestaurant(4, "4/8", "1" + " items", "Nguyen Dai Duong", "1000000", "KTX khu D DHQG TPHCM"));
        restaurantList.add(new ItemDetailRestaurant(5, "30/12", "2" + " items", "le Xuan Quynh", "242353264", "KTX khu E DHQG TPHCM"));

        adapter = new RestaurantInforPreparingAdapter(getActivity(), restaurantList);
        recyclerView.setAdapter(adapter);

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