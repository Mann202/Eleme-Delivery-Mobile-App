package com.example.fududelivery.Food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_Food extends RecyclerView.Adapter<ViewAdapter_Food.FoodViewHolder> {
    private List<Food> mFoods;

    public void setData(List<Food> list) {
        this.mFoods = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mFoods.get(position);
        if (food==null) {
            return;
        }
        holder.imgFood.setImageResource(food.getResourceId());
        holder.tvTitle.setText(food.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView tvTitle;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.img_food);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
