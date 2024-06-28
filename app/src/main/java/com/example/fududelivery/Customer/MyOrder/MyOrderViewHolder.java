package com.example.fududelivery.Customer.MyOrder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

public class MyOrderViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView dateText, itemCountText,nameText, statusText, totalPriceText;
    AppCompatButton reorderBtn, rateBtn;
    RelativeLayout rlContainer;

    public MyOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        rlContainer = itemView.findViewById(R.id.rl_container);
        imageView = itemView.findViewById(R.id.logo);
        dateText = itemView.findViewById(R.id.dateText);
        itemCountText = itemView.findViewById(R.id.itemCountText);
        nameText = itemView.findViewById(R.id.nameText);
        statusText = itemView.findViewById(R.id.statusText);
        totalPriceText = itemView.findViewById(R.id.totalPriceText);
        reorderBtn = itemView.findViewById(R.id.reorderBtn);
        rateBtn = itemView.findViewById(R.id.rateBtn);
    }
}

