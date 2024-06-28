package com.example.fududelivery.Shipper.ShipperMain;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.Model.Order;
import com.example.fududelivery.Shipper.ShipperOrderDetail.OrderDetail;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    Activity context;
    ArrayList<Order> orders;
    private OnItemClickListener itemClickListener;

    public OrderAdapter(Activity context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false));
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetail.class);


                intent.putExtra("OrderID", order.getOrderID());
                intent.putExtra("OrderTotal", order.getOrderTotal());
                intent.putExtra("ShippingFee", order.getShippingFee());
                intent.putExtra("serviceFee", order.getServiceFee());
                intent.putExtra("TotalQuantity", order.getTotalQuantity());
                intent.putExtra("subTotal", order.getSubTotal());
                intent.putExtra("Address", order.getAddress());
                intent.putExtra("Name", order.getName());
                intent.putExtra("Phone", order.getPhone());
                intent.putExtra("ResID", order.getResID());
                intent.putExtra("ShippingStatus", order.getShippingStatus());

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
        return orders.size();
    }
}
