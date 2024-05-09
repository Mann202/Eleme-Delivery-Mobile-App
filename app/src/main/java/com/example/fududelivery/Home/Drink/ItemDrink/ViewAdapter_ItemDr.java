package com.example.fududelivery.Home.Drink.ItemDrink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.FastFood.ItemFastFood.ItemFastFood;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemDr  extends RecyclerView.Adapter<ViewAdapter_ItemDr.ItemDrinkViewHolder> {
    private List<ItemDrink> mDrinks;

    public void setData(List<ItemDrink> list) {
        this.mDrinks = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new ItemDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDrinkViewHolder holder, int position) {
        ItemDrink drink = mDrinks.get(position);
        if (drink==null) {
            return;
        }
        holder.imgDrink.setImageResource(drink.getResourceId());
        holder.tvTitleDrink.setText(drink.getTitle());
        holder.tvTypeDrink.setText(drink.getType());
    }

    @Override
    public int getItemCount() {
        if (mDrinks != null) {
            return mDrinks.size();
        }
        return 0;
    }

    public class ItemDrinkViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgDrink;
        private TextView tvTitleDrink;
        private TextView tvTypeDrink;
        public ItemDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDrink = itemView.findViewById(R.id.img_drink);
            tvTitleDrink = itemView.findViewById(R.id.tv_titleDrink);
            tvTypeDrink = itemView.findViewById(R.id.tv_type_dr);
        }
    }
}

