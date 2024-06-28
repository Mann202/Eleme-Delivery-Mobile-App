package com.example.fududelivery.Restaurant.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.ItemDetailRestaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantHistoryAdapter extends RecyclerView.Adapter<RestaurantHistoryAdapter.RestaurantHistoryViewHolder> {

    private final Context context;
    private final List<ItemDetailRestaurant> items;
    private OnItemClickListener listener;

    public RestaurantHistoryAdapter(Context context, List<ItemDetailRestaurant> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RestaurantHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restauranthistory, parent, false);
        return new RestaurantHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHistoryViewHolder holder, int position) {
        ItemDetailRestaurant item = items.get(position);
        Picasso.get().load(item.getImageView()).into(holder.logo);
        holder.nameTextView.setText(item.getNameText());
        holder.addressText.setText(item.getAdressText());
        holder.dateTextHistory.setText(item.getDateText());
        holder.itemCountTextHistory.setText(item.getItemCountText());

        holder.totalPriceTextHistory.setText(item.getTotalPriceText());
        // convert int to String

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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class RestaurantHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressText;
        ImageView logo;

        TextView totalPriceTextHistory;
        TextView dateTextHistory;
        TextView itemCountTextHistory;

        public RestaurantHistoryViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            nameTextView = itemView.findViewById(R.id.nameTextHistory);
            addressText = itemView.findViewById(R.id.adressTextHistory);
            totalPriceTextHistory = itemView.findViewById(R.id.totalPriceTextHistory);
            dateTextHistory = itemView.findViewById(R.id.dateTextHistory);
            itemCountTextHistory = itemView.findViewById(R.id.itemCountTextHistory);
        }
    }
}
