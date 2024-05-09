package com.example.fududelivery.Home.Dessert.ItemDessertList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Dessert.ItemDessertList.ItemDessertList;
import com.example.fududelivery.Home.Dessert.ItemDessert.ViewAdapter_ItemDessert;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemDessertList extends RecyclerView.Adapter<ViewAdapter_ItemDessertList.DessertListViewHolder> {

    private final Context mContext;
    private List<ItemDessertList> mListDessert;
    public ViewAdapter_ItemDessertList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemDessertList> list) {
        this.mListDessert = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public ViewAdapter_ItemDessertList.DessertListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dessertlist, parent, false);
        return new ViewAdapter_ItemDessertList.DessertListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_ItemDessertList.DessertListViewHolder holder, int position) {
        ItemDessertList dessertlist = mListDessert.get(position);
        if(dessertlist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder. rcvDessert.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemDessert dessertAdapter = new ViewAdapter_ItemDessert();
        dessertAdapter.setData(dessertlist.getdesserts());
        holder.rcvDessert.setAdapter(dessertAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListDessert != null) {
            return mListDessert.size();
        }
        return 0;
    }

    public class DessertListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvDessert;
        public DessertListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvDessert = itemView.findViewById(R.id.rcv_dessert);
        }
    }
}

