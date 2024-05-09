package com.example.fududelivery.Home.Vegetable.ItemVegetable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Vegetable.ItemVegetable.ItemVegetable;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemVeget  extends RecyclerView.Adapter<ViewAdapter_ItemVeget.ItemVegetableViewHolder> {
    private List<ItemVegetable> mVegets;

    public void setData(List<ItemVegetable> list) {
        this.mVegets = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public com.example.fududelivery.Home.Vegetable.ItemVegetable.ViewAdapter_ItemVeget.ItemVegetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vegetable, parent, false);
        return new ItemVegetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVegetableViewHolder holder, int position) {
        ItemVegetable veget = mVegets.get(position);
        if (veget==null) {
            return;
        }
        holder.imgVeget.setImageResource(veget.getResourceId());
        holder.tvTitleVeget.setText(veget.getTitle());
        holder.tvTypeVeget.setText(veget.getType());
    }

    @Override
    public int getItemCount() {
        if (mVegets != null) {
            return mVegets.size();
        }
        return 0;
    }

    public class ItemVegetableViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgVeget;
        private TextView tvTitleVeget;
        private TextView tvTypeVeget;
        public ItemVegetableViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVeget = itemView.findViewById(R.id.img_vegetable);
            tvTitleVeget = itemView.findViewById(R.id.tv_titleVeget);
            tvTypeVeget = itemView.findViewById(R.id.tv_type_vg);
        }
    }
}
