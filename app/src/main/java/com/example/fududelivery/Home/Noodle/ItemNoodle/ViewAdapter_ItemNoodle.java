package com.example.fududelivery.Home.Noodle.ItemNoodle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Noodle.ItemNoodle.ItemNoodle;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemNoodle  extends RecyclerView.Adapter<ViewAdapter_ItemNoodle.ItemNoodleViewHolder> {
    private List<ItemNoodle> mNoodles;

    public void setData(List<ItemNoodle> list) {
        this.mNoodles = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemNoodleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noodle, parent, false);
        return new ItemNoodleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNoodleViewHolder holder, int position) {
        ItemNoodle noodle = mNoodles.get(position);
        if (noodle==null) {
            return;
        }
        holder.imgNoodle.setImageResource(noodle.getResourceId());
        holder.tvTitleNoodle.setText(noodle.getTitle());
        holder.tvTypeNoodle.setText(noodle.getType());
    }

    @Override
    public int getItemCount() {
        if (mNoodles != null) {
            return mNoodles.size();
        }
        return 0;
    }

    public class ItemNoodleViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgNoodle;
        private TextView tvTitleNoodle;
        private TextView tvTypeNoodle;
        public ItemNoodleViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNoodle = itemView.findViewById(R.id.img_noodle);
            tvTitleNoodle = itemView.findViewById(R.id.tv_titleNoodle);
            tvTypeNoodle = itemView.findViewById(R.id.tv_type_nd);
        }
    }
}