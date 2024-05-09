package com.example.fududelivery.Home.Vegetable.ItemVegetList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Vegetable.ItemVegetList.ItemVegetList;
import com.example.fududelivery.Home.Vegetable.ItemVegetable.ViewAdapter_ItemVeget;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemVegetList extends RecyclerView.Adapter<ViewAdapter_ItemVegetList.VegetableListViewHolder> {

    private final Context mContext;
    private List<ItemVegetList> mListVeget;
    public ViewAdapter_ItemVegetList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemVegetList> list) {
        this.mListVeget= list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public VegetableListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vegetlist, parent, false);
        return new VegetableListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableListViewHolder holder, int position) {
        ItemVegetList Vegetoodlist = mListVeget.get(position);
        if(Vegetoodlist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder.rcvVeget.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemVeget VegetAdapter = new ViewAdapter_ItemVeget();
        VegetAdapter.setData(Vegetoodlist.getVegets());
        holder.rcvVeget.setAdapter(VegetAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListVeget != null) {
            return mListVeget.size();
        }
        return 0;
    }

    public class VegetableListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvVeget;
        public VegetableListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvVeget = itemView.findViewById(R.id.rcv_vegetable);
        }
    }
}

