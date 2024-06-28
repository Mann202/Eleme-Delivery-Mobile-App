package com.example.fududelivery.Restaurant_Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_RestaurantHome extends RecyclerView.Adapter<ViewAdapter_RestaurantHome.Restaurant_HomeViewHolder> {
    private List<Restaurant_Home> mFoods;
    Context context;

    public ViewAdapter_RestaurantHome(List<Restaurant_Home> list, Context context) {
        this.mFoods = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Restaurant_HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new Restaurant_HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Restaurant_HomeViewHolder holder, int position) {
        Restaurant_Home item = mFoods.get(position);
        Glide.with(context).load(mFoods.get(position).getResourceId()).into(holder.imgFood);
        holder.tvTitle.setText(mFoods.get(position).getResName());
        holder.tvAddress.setText(mFoods.get(position).getAddressName());
    }

    public void onClickGotoDetail(Restaurant_Home item) {
        Intent intent = new Intent(context, Restaurant_Home_Detail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item", item);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        }
        return 0;
    }

    public class Restaurant_HomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView tvTitle;
        private TextView tvAddress;

        public Restaurant_HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvTitle = itemView.findViewById(R.id.tv_title_food);
            tvAddress = itemView.findViewById(R.id.tv_address_food);
            // Gán sự kiện click cho itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickGotoDetail(mFoods.get(getAdapterPosition()));
                }
            });
        }
    }
}