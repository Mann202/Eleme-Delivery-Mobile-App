package com.example.fududelivery.Home.Dessert.ItemDessert;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Dessert.ItemDessert.ItemDessert;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemDessert  extends RecyclerView.Adapter<ViewAdapter_ItemDessert.ItemDessertViewHolder> {
    private List<ItemDessert> mDesserts;

    public void setData(List<ItemDessert> list) {
        this.mDesserts = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewAdapter_ItemDessert.ItemDessertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dessert, parent, false);
        return new ViewAdapter_ItemDessert.ItemDessertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_ItemDessert.ItemDessertViewHolder holder, int position) {
        ItemDessert dessert = mDesserts.get(position);
        if (dessert==null) {
            return;
        }
        holder.imgDessert.setImageResource(dessert.getResourceId());
        holder.tvTitleDessert.setText(dessert.getTitle());
        holder.tvTypeDessert.setText(dessert.getType());
    }

    @Override
    public int getItemCount() {
        if (mDesserts != null) {
            return mDesserts.size();
        }
        return 0;
    }

    public class ItemDessertViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgDessert;
        private TextView tvTitleDessert;
        private TextView tvTypeDessert;
        public ItemDessertViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDessert = itemView.findViewById(R.id.img_dessert);
            tvTitleDessert = itemView.findViewById(R.id.tv_titleDessert);
            tvTypeDessert = itemView.findViewById(R.id.tv_type_ds);
        }
    }
}
