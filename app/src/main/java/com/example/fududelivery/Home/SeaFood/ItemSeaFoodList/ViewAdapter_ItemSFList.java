package com.example.fududelivery.Home.SeaFood.ItemSeaFoodList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.SeaFood.ItemSeaFood.ViewAdapter_ItemSeaFood;
import com.example.fududelivery.Home.SeaFood.ItemSeaFoodList.ItemSFList;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemSFList extends RecyclerView.Adapter<ViewAdapter_ItemSFList.SFListViewHolder> {

    private final Context mContext;
    private List<ItemSFList> mListSeaFood;
    public ViewAdapter_ItemSFList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemSFList> list) {
        this.mListSeaFood = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public SFListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seafoodlist, parent, false);
        return new SFListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SFListViewHolder holder, int position) {
        ItemSFList seafoodist =mListSeaFood.get(position);
        if(seafoodist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder.rcvSeaFood.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemSeaFood seafoodAdapter = new ViewAdapter_ItemSeaFood();
        seafoodAdapter.setData(seafoodist.getseafoods());
        holder.rcvSeaFood.setAdapter(seafoodAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListSeaFood != null) {
            return mListSeaFood.size();
        }
        return 0;
    }

    public class SFListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvSeaFood;
        public SFListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvSeaFood = itemView.findViewById(R.id.rcv_seafood);
        }
    }
}

