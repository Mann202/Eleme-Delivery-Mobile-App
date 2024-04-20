package com.example.fududelivery.Home.FastFood.ItemFastFood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Food.Food;
import com.example.fududelivery.R;

import java.util.List;


public class ViewAdapter_ItemFF  extends RecyclerView.Adapter<ViewAdapter_ItemFF.ItemFastFoodViewHolder> {
    private List<ItemFastFood> mFFoods;

    public void setData(List<ItemFastFood> list) {
        this.mFFoods = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemFastFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fastfood, parent, false);
        return new ItemFastFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFastFoodViewHolder holder, int position) {
        ItemFastFood ffood = mFFoods.get(position);
        if (ffood==null) {
            return;
        }
        holder.imgFFood.setImageResource(ffood.getResourceId());
        holder.tvTitleFF.setText(ffood.getTitle());
        holder.tvTypeFF.setText(ffood.getType());
    }

    @Override
    public int getItemCount() {
        if (mFFoods != null) {
            return mFFoods.size();
        }
        return 0;
    }

    public class ItemFastFoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFFood;
        private TextView tvTitleFF;
        private TextView tvTypeFF;
        public ItemFastFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFFood = itemView.findViewById(R.id.img_ffood);
            tvTitleFF = itemView.findViewById(R.id.tv_titleFF);
            tvTypeFF = itemView.findViewById(R.id.tv_type);
        }
    }
}

