package com.example.fududelivery.Restaurant.MainRestaurant;

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

public class RestaurantMainDoneFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantInforDoneAdapter adapter;
    private ArrayList<ItemDetailRestaurant> restaurantList;

    public RestaurantMainDoneFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurantmaindone, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        restaurantList = new ArrayList<>();
        restaurantList.add(new ItemDetailRestaurant(1, "28/1", "3" + " items", "Truong Gia Man", "100000", "KTX khu A DHQG TPHCM"));

        adapter = new RestaurantInforDoneAdapter(getActivity(), restaurantList);
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
