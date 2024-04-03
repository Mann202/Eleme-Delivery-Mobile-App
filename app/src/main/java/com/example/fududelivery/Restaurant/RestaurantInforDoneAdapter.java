package com.example.fududelivery.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class RestaurantInforDoneAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    Context context;
    List<itemRestaurant> items;

    public RestaurantInforDoneAdapter(Context context, List<itemRestaurant> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurantdone,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.nameText.setText(items.get(position).getNameText());
        //holder.imageView.setImageResource(items.get(position).getImageView());
        holder.itemCountText.setText(items.get(position).getItemCountText());
        holder.adressText.setText(items.get(position).getAdressText());
        holder.totalPriceText.setText(items.get(position).getTotalPriceText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
