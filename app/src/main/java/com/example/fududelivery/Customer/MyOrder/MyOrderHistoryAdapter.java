package com.example.fududelivery.Customer.MyOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.OrderDetail.OrderDetail;
import com.example.fududelivery.R;


import java.util.List;

public class MyOrderHistoryAdapter extends RecyclerView.Adapter<MyOrderViewHolder> {
    Context context;
    List<MyOrderInfor> items;

    public MyOrderHistoryAdapter(Context context, List<MyOrderInfor> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_myorderhistory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        holder.nameText.setText(items.get(position).getNameText());
        holder.itemCountText.setText(items.get(position).getItemCountText());
        holder.statusText.setText(items.get(position).getStatusText());
        holder.totalPriceText.setText(items.get(position).getTotalPriceText());


        holder.navigateNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, OrderDetail.class);
                context.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            private void overridePendingTransition(int slideInRight, int slideOutLeft) {
                ((Activity) context).overridePendingTransition(slideInRight, slideOutLeft);
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
