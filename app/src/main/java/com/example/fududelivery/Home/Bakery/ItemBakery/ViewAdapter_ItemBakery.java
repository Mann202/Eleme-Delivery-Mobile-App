package com.example.fududelivery.Home.Bakery.ItemBakery;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Bakery.ItemBakery.ItemBakery;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemBakery  extends RecyclerView.Adapter<ViewAdapter_ItemBakery.ItemBakeryViewHolder> {
    private List<ItemBakery> mBakeries;

    public void setData(List<ItemBakery> list) {
        this.mBakeries = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemBakeryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bakery, parent, false);
        return new ItemBakeryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemBakeryViewHolder holder, int position) {
        ItemBakery Bakery = mBakeries.get(position);
        if (Bakery==null) {
            return;
        }
        holder.imgBakery.setImageResource(Bakery.getResourceId());
        holder.tvTitleBakery.setText(Bakery.getTitle());
        holder.tvTypeBakery.setText(Bakery.getType());
    }

    @Override
    public int getItemCount() {
        if (mBakeries != null) {
            return mBakeries.size();
        }
        return 0;
    }

    public class ItemBakeryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBakery;
        private TextView tvTitleBakery;
        private TextView tvTypeBakery;
        public ItemBakeryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBakery = itemView.findViewById(R.id.img_bakery);
            tvTitleBakery = itemView.findViewById(R.id.tv_titleBakery);
            tvTypeBakery = itemView.findViewById(R.id.tv_type_bk);
        }
    }
}