package com.example.fududelivery.Restaurant_Home;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fududelivery.Home.Search.ItemSearch;
import com.example.fududelivery.Home.Search.ViewAdapter_ItemSearch;
import com.example.fududelivery.R;

import java.util.List;
import java.util.Locale;

public class ViewAdapter_Food extends RecyclerView.Adapter<ViewAdapter_Food.ItemFoodHolder>{
    private List<Food> mFoods;
    Context mContext;
    public ViewAdapter_Food(List<Food> list, Context mContext) {
        this.mFoods = list;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu_food, parent, false);
        return new ItemFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFoodHolder holder, int position) {
        Food Foods = mFoods.get(position);
        Glide.with(mContext).load(Foods.getImageId()).into(holder.imgFood);
        holder.tvFoodName.setText(Foods.getFoodName());
        holder.tvPrice.setText(String.valueOf(Foods.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        }
        return 0;
    }
    public class ItemFoodHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView tvFoodName;
        private TextView tvPrice;
        public ItemFoodHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_menu_food);
            tvFoodName = itemView.findViewById(R.id.tv_titleFood);
            tvPrice = itemView.findViewById(R.id.price_food);
        }
    }
}
