package com.example.fududelivery.Home.FastFood.ItemFFList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Food.ViewAdapter_Food;
import com.example.fududelivery.FoodList.FoodList;
import com.example.fududelivery.Home.FastFood.ItemFastFood.ViewAdapter_ItemFF;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemFFList extends RecyclerView.Adapter<ViewAdapter_ItemFFList.FastFoodListViewHolder> {

    private final Context mContext;
    private List<ItemFFList> mListFFood;
    public ViewAdapter_ItemFFList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemFFList> list) {
        this.mListFFood = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public FastFoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fastfoodlist, parent, false);
        return new FastFoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FastFoodListViewHolder holder, int position) {
        ItemFFList ffoodlist = mListFFood.get(position);
        if(ffoodlist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder. rcvFFood.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemFF ffoodAdapter = new ViewAdapter_ItemFF();
        ffoodAdapter.setData(ffoodlist.getFFoods());
        holder.rcvFFood.setAdapter(ffoodAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListFFood != null) {
            return mListFFood.size();
        }
        return 0;
    }

    public class FastFoodListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvFFood;
        public FastFoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvFFood = itemView.findViewById(R.id.rcv_fastfood);
        }
    }
}

