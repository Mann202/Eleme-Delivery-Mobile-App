package com.example.fududelivery.Home.Restaurant_Item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class ItemRestaurantAdapter extends RecyclerView.Adapter<ItemRestaurantAdapter.ItemRestaurantViewHolder> {
    private List<ItemRestaurant> mItemRestaurants;

    public ItemRestaurantAdapter(List<ItemRestaurant> mItemRestaurants) {
        this.mItemRestaurants = mItemRestaurants;
    }

    @NonNull
    @Override
    public ItemRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant,parent,false);
        return new ItemRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRestaurantViewHolder holder, int position) {
        ItemRestaurant item_res = mItemRestaurants.get(position);
        if (item_res == null) {
            return;
        }
        holder.imgView.setImageResource(mItemRestaurants.get(position).getResourceId());
        holder.txtView_title.setText(mItemRestaurants.get(position).getTitle());
        holder.txtView_star.setText(mItemRestaurants.get(position).getStar_rate());
        holder.txtView_distance.setText(mItemRestaurants.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        if(mItemRestaurants != null) {
            return mItemRestaurants.size();
        }
        return 0;
    }

    public class ItemRestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView txtView_title;
        TextView txtView_star;
        TextView txtView_distance;
        public ItemRestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_food_sm);
            txtView_title = itemView.findViewById(R.id.tv_title_sm);
            txtView_star = itemView.findViewById(R.id.star_rate_sm);
            txtView_distance = itemView.findViewById(R.id.distance_sm);
        }
    }
}
