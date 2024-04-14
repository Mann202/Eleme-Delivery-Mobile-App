package com.example.fududelivery.FoodList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Food.ViewAdapter_Food;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_FoodList extends RecyclerView.Adapter<ViewAdapter_FoodList.FoodListViewHolder> {

    private final Context mContext;
    private List<FoodList> mListFood;
    public ViewAdapter_FoodList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<FoodList> list) {
        this.mListFood = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodlist, parent, false);
        return new FoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {
        FoodList foodlist = mListFood.get(position);
        if(foodlist == null) {
            return;
        }
        holder.tvNameCategory.setText(foodlist.getNameFoodList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvFood.setLayoutManager(linearLayoutManager);
        ViewAdapter_Food foodAdapter = new ViewAdapter_Food();
        foodAdapter.setData(foodlist.getFoods());
        holder.rcvFood.setAdapter(foodAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListFood != null) {
            return mListFood.size();
        }
        return 0;
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameCategory;
        private RecyclerView rcvFood;
        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            rcvFood = itemView.findViewById(R.id.rcv_food);
        }
    }
}
