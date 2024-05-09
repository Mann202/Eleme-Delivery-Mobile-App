package com.example.fududelivery.Home.Drink.ItemDrinkList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Drink.ItemDrink.ViewAdapter_ItemDr;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemDrList extends RecyclerView.Adapter<ViewAdapter_ItemDrList.DrinkListViewHolder> {

    private final Context mContext;
    private List<ItemDrinkList> mListDrink;
    public ViewAdapter_ItemDrList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ItemDrinkList> list) {
        this.mListDrink = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public DrinkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drinklist, parent, false);
        return new ViewAdapter_ItemDrList.DrinkListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkListViewHolder holder, int position) {
        ItemDrinkList drinklist = mListDrink.get(position);
        if(drinklist == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.VERTICAL, false);
        holder.rcvDrink.setLayoutManager(linearLayoutManager);
        ViewAdapter_ItemDr drinkAdapter = new ViewAdapter_ItemDr();
        drinkAdapter.setData(drinklist.getDrinks());
        holder.rcvDrink.setAdapter(drinkAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListDrink != null) {
            return mListDrink.size();
        }
        return 0;
    }

    public class DrinkListViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvDrink;
        public DrinkListViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvDrink = itemView.findViewById(R.id.rcv_drink);
        }
    }
}

