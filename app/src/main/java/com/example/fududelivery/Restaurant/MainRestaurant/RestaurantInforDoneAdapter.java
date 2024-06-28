package com.example.fududelivery.Restaurant.MainRestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantInforDoneAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    Context context;
    List<ItemDetailRestaurant> items;
    private OnItemClickListener listener;

    public RestaurantInforDoneAdapter(Context context, List<ItemDetailRestaurant> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurantdone, parent, false));
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Picasso.get().load(items.get(position).getImageView()).into(holder.imageView);
        holder.nameText.setText(items.get(position).getNameText());
        holder.itemCountText.setText(items.get(position).getItemCountText());
        holder.adressText.setText(items.get(position).getAdressText());
        holder.totalPriceText.setText(items.get(position).getTotalPriceText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
