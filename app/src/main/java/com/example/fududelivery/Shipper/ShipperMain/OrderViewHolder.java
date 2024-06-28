package com.example.fududelivery.Shipper.ShipperMain;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.Model.Order;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    ImageView imgOrder;
    TextView txtDate;
    TextView txtItems;
    TextView txtCusName;
    TextView txtCusAddress;
    TextView txtTotal;
    ImageView nav_orderdetail;

    public OrderViewHolder(View itemView){
        super(itemView);
        imgOrder = itemView.findViewById(R.id.img_order);
        txtDate = itemView.findViewById(R.id.txt_date);
        txtItems = itemView.findViewById(R.id.txt_items);
        txtCusName = itemView.findViewById(R.id.txt_cusname_card);
        txtCusAddress = itemView.findViewById(R.id.txt_cusaddress_card);
        txtTotal = itemView.findViewById(R.id.txt_custotal_card);
        nav_orderdetail = itemView.findViewById(R.id.nav_orderdetail);
    }

    public void bind(Order order) {
        if (order.getName()!=null) {
            txtCusName.setText(order.getName());
        }  else txtCusName.setText("");

        if (order.getAddress()!=null) {
            txtCusAddress.setText(order.getAddress());
        }  else txtCusAddress.setText("");

        if (order.getDate()!=null) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            String formatDate = format.format(order.getDate());
//            txtDate.setText(formatDate);
            txtDate.setText(order.getDate().toString());
        }  else txtDate.setText("3 Jun, 09:41");

        if (order.getTotalQuantity()!=null) {
            txtItems.setText(order.getTotalQuantity() + " item");
        }  else txtItems.setText("0");

        if (order.getOrderTotal() != 0.0f) {
            txtTotal.setText(String.valueOf(order.getOrderTotal()));
        } else txtTotal.setText("0");

    }

}
