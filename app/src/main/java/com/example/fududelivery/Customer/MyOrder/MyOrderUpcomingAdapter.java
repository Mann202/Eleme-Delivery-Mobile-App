package com.example.fududelivery.Customer.MyOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.CancelOrderActivity;
import com.example.fududelivery.Customer.OrderDetail.OrderDetail;
import com.example.fududelivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOrderUpcomingAdapter extends RecyclerView.Adapter<MyOrderUpcomingViewHolder> {

    Context context;
    List<MyOrderInfor> items;

    public MyOrderUpcomingAdapter(Context context, List<MyOrderInfor> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyOrderUpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderUpcomingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_myorderupcoming, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderUpcomingViewHolder holder, int position) {
        Picasso.get().load(items.get(position).getImageView()).into(holder.imageView);
        holder.nameText.setText(items.get(position).getNameText());
        holder.itemCountText.setText(items.get(position).getItemCountText());
        holder.statusText.setText(items.get(position).getStatusText());
        holder.totalPriceText.setText(items.get(position).getTotalPriceText());

        if(items.get(position).getStatusText().equals("Cancel")){
            holder.reorderBtn.setVisibility(View.GONE);
        }

        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetail.class);
                context.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            private void overridePendingTransition(int slideInRight, int slideOutLeft) {
                ((Activity) context).overridePendingTransition(slideInRight, slideOutLeft);
            }
        });

        holder.reorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelOrderIntent = new Intent(context, CancelOrderActivity.class);
                cancelOrderIntent.putExtra("orderId", items.get(position).getOrderId());
                context.startActivity(cancelOrderIntent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

