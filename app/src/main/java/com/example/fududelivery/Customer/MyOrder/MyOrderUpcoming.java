package com.example.fududelivery.Customer.MyOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.RestaurantInforPreparingAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyOrderUpcoming extends Fragment {
    private RecyclerView recyclerView;
    private MyOrderUpcomingAdapter adapter;
    private ArrayList<MyOrderInfor> myOrderInfors;

    public MyOrderUpcoming(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer_myorder_recycler, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<MyOrderInfor> upcomingOrderList = new ArrayList<MyOrderInfor>();
        upcomingOrderList.add(new MyOrderInfor(1, "28/1", "3" + " items", "Starbucks", "153.000", "On the way"));
        upcomingOrderList.add(new MyOrderInfor(2, "30/1", "4" + " items", "KFC", "153.000", "On the way"));
        upcomingOrderList.add(new MyOrderInfor(3, "2/2", "8" + " items", "Highlands", "50.000", "On the way"));
        upcomingOrderList.add(new MyOrderInfor(4, "4/8", "1" + " items", "McDonald's", "125.000", "On the way"));
        upcomingOrderList.add(new MyOrderInfor(5, "30/12", "2" + " items", "KFC", "80.000", "On the way"));

        adapter = new MyOrderUpcomingAdapter(getActivity(), upcomingOrderList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
