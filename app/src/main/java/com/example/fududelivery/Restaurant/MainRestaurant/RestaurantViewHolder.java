package com.example.fududelivery.Restaurant.MainRestaurant;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView, navigateNextBtn;
    TextView dateText;
    public TextView itemCountText;
    public TextView nameText;
    public TextView adressText;
    public TextView totalPriceText;
    public AppCompatButton detailBtn;
    AppCompatButton cancelBtn;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.logo);
        navigateNextBtn = itemView.findViewById(R.id.navigateNextBtn);
        dateText = itemView.findViewById(R.id.dateText);
        itemCountText = itemView.findViewById(R.id.itemCountText);
        nameText = itemView.findViewById(R.id.nameText);
        adressText = itemView.findViewById(R.id.adressText);
        totalPriceText = itemView.findViewById(R.id.totalPriceText);
        detailBtn = itemView.findViewById(R.id.detailBtn);
        cancelBtn = itemView.findViewById(R.id.cancelBtn);
    }

}
