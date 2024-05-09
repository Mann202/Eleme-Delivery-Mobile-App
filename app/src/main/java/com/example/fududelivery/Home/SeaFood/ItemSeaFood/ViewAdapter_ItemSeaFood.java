package com.example.fududelivery.Home.SeaFood.ItemSeaFood;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.SeaFood.ItemSeaFood.ItemSeaFood;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemSeaFood  extends RecyclerView.Adapter<ViewAdapter_ItemSeaFood.ItemSeaFoodViewHolder> {
    private List<ItemSeaFood> mSeaFoods;

    public void setData(List<ItemSeaFood> list) {
        this.mSeaFoods = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemSeaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seafood, parent, false);
        return new ItemSeaFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSeaFoodViewHolder holder, int position) {
        ItemSeaFood seafood = mSeaFoods.get(position);
        if (seafood==null) {
            return;
        }
        holder.imgSeaFood.setImageResource(seafood.getResourceId());
        holder.tvTitleSeaFood.setText(seafood.getTitle());
        holder.tvTypeSeaFood.setText(seafood.getType());
    }

    @Override
    public int getItemCount() {
        if (mSeaFoods != null) {
            return mSeaFoods.size();
        }
        return 0;
    }

    public class ItemSeaFoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSeaFood;
        private TextView tvTitleSeaFood;
        private TextView tvTypeSeaFood;
        public ItemSeaFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSeaFood = itemView.findViewById(R.id.img_seafood);
            tvTitleSeaFood = itemView.findViewById(R.id.tv_titleSeaFood);
            tvTypeSeaFood = itemView.findViewById(R.id.tv_type_sf);
        }
    }
}