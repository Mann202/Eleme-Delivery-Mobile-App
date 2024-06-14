package com.example.fududelivery.Home.Fruit.ItemFruitList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Fruit.ItemFruit.ViewAdapter_ItemFruit;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemFruitList extends RecyclerView.Adapter<ViewAdapter_ItemFruitList.FruitListViewHolder> {

    private final Context mContext;
    private List<ItemFruitList> mListFruit;
    public ViewAdapter_ItemFruitList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemFruitList> list) {
        this.mListFruit= list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public ViewAdapter_ItemFruitList.FruitListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruitlist, parent, false);
        return new ViewAdapter_ItemFruitList.FruitListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_ItemFruitList.FruitListViewHolder holder, int position) {
        ItemFruitList Fruitlist = mListFruit.get(position);
        if(Fruitlist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder.rcvFruit.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemFruit FruitAdapter = new ViewAdapter_ItemFruit();
        FruitAdapter.setData(Fruitlist.getFruits());
        holder.rcvFruit.setAdapter(FruitAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListFruit != null) {
            return mListFruit.size();
        }
        return 0;
    }

    public class FruitListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvFruit;
        public FruitListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvFruit = itemView.findViewById(R.id.rcv_fruit);
        }
    }
}

