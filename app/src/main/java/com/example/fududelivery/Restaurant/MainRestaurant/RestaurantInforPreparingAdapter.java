package com.example.fududelivery.Restaurant.MainRestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.RestaurantDetail.RestaurantDetail;

import java.util.List;

public class RestaurantInforPreparingAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    Context context;
    List<ItemDetailRestaurant> items;

    public RestaurantInforPreparingAdapter(Context context, List<ItemDetailRestaurant> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurantprepairing,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.nameText.setText(items.get(position).getNameText());
        holder.itemCountText.setText(items.get(position).getItemCountText());
        holder.adressText.setText(items.get(position).getAdressText());
        holder.totalPriceText.setText(items.get(position).getTotalPriceText());
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, RestaurantDetail.class);
                context.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            private void overridePendingTransition(int slideInRight, int slideOutLeft) {
                ((Activity) context).overridePendingTransition(slideInRight, slideOutLeft);
            }
        });

        holder.navigateNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, RestaurantDetail.class);
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
