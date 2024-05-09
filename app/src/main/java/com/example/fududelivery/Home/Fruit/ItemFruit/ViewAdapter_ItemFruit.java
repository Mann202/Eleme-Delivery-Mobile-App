package com.example.fududelivery.Home.Fruit.ItemFruit;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemFruit extends RecyclerView.Adapter<ViewAdapter_ItemFruit.ItemFruitViewHolder> {
    private List<ItemFruit> mFruits;

    public void setData(List<ItemFruit> list) {
        this.mFruits = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewAdapter_ItemFruit.ItemFruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);
        return new ViewAdapter_ItemFruit.ItemFruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_ItemFruit.ItemFruitViewHolder holder, int position) {
        ItemFruit fruit = mFruits.get(position);
        if (fruit==null) {
            return;
        }
        holder.imgFruit.setImageResource(fruit.getResourceId());
        holder.tvTitleFruit.setText(fruit.getTitle());
        holder.tvTypeFruit.setText(fruit.getType());
    }

    @Override
    public int getItemCount() {
        if (mFruits != null) {
            return mFruits.size();
        }
        return 0;
    }

    public class ItemFruitViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFruit;
        private TextView tvTitleFruit;
        private TextView tvTypeFruit;
        public ItemFruitViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFruit = itemView.findViewById(R.id.img_fruit);
            tvTitleFruit = itemView.findViewById(R.id.tv_titleFruit);
            tvTypeFruit = itemView.findViewById(R.id.tv_type_fr);
        }
    }
}
