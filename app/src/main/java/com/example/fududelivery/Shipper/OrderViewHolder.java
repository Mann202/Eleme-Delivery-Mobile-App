package com.example.fududelivery.Shipper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

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
        if (order.getCusname()!=null) {
            txtCusName.setText(order.getCusname());
        }  else txtCusName.setText("");

        if (order.getCusaddress()!=null) {
            txtCusAddress.setText(order.getCusaddress());
        }  else txtCusAddress.setText("");

//        if (order.getOrderdate()!=null) {
//            txtDate.setText(order.getOrderdate().toString());
//        }  else txtDate.setText("");
//
//        if (order.getQuantity()!=0) {
//            txtItems.setText(order.getQuantity());
//        }  else txtItems.setText("");
//
//        if (order.getTotal() != 0.0f) {
//            txtTotal.setText(String.valueOf(order.getTotal()));
//        } else txtTotal.setText("");

    }

}
