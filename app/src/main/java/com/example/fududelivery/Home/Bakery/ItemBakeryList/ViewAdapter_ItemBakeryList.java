package com.example.fududelivery.Home.Bakery.ItemBakeryList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Bakery.ItemBakery.ViewAdapter_ItemBakery;
import com.example.fududelivery.Home.Bakery.ItemBakeryList.ItemBakeryList;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemBakeryList extends RecyclerView.Adapter<ViewAdapter_ItemBakeryList.BakeryListViewHolder> {

    private final Context mContext;
    private List<ItemBakeryList> mListBakery;
    public ViewAdapter_ItemBakeryList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemBakeryList> list) {
        this.mListBakery = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public ViewAdapter_ItemBakeryList.BakeryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bakerylist, parent, false);
        return new ViewAdapter_ItemBakeryList.BakeryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_ItemBakeryList.BakeryListViewHolder holder, int position) {
        ItemBakeryList bakerylist =mListBakery.get(position);
        if(bakerylist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder.rcvBakery.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemBakery bakeryAdapter = new ViewAdapter_ItemBakery();
        bakeryAdapter.setData(bakerylist.getbakeries());
        holder.rcvBakery.setAdapter(bakeryAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListBakery != null) {
            return mListBakery.size();
        }
        return 0;
    }

    public class BakeryListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvBakery;
        public BakeryListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvBakery = itemView.findViewById(R.id.rcv_bakery);
        }
    }
}

