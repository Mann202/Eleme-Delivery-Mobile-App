package com.example.fududelivery.Home.Noodle.ItemNoodleList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Noodle.ItemNoodleList.ItemNoodleList;
import com.example.fududelivery.Home.Noodle.ItemNoodle.ViewAdapter_ItemNoodle;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemNoodleList extends RecyclerView.Adapter<com.example.fududelivery.Home.Noodle.ItemNoodleList.ViewAdapter_ItemNoodleList.NoodleListViewHolder> {

    private final Context mContext;
    private List<ItemNoodleList> mListNoodle;
    public ViewAdapter_ItemNoodleList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemNoodleList> list) {
        this.mListNoodle = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public ViewAdapter_ItemNoodleList.NoodleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noodlelist, parent, false);
        return new NoodleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoodleListViewHolder holder, int position) {
        ItemNoodleList noodlelist = mListNoodle.get(position);
        if(noodlelist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder. rcvNoodle.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemNoodle noodleAdapter = new ViewAdapter_ItemNoodle();
        noodleAdapter.setData(noodlelist.getnoodles());
        holder.rcvNoodle.setAdapter(noodleAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListNoodle != null) {
            return mListNoodle.size();
        }
        return 0;
    }

    public class NoodleListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvNoodle;
        public NoodleListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvNoodle = itemView.findViewById(R.id.rcv_noodle);
        }
    }
}

