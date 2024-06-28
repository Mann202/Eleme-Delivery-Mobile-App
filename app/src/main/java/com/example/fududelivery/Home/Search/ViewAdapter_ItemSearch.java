package com.example.fududelivery.Home.Search;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant_Home.Restaurant_Home;
import com.example.fududelivery.Restaurant_Home.Restaurant_Home_Detail;

import java.util.List;
import java.util.Locale;

public class ViewAdapter_ItemSearch extends RecyclerView.Adapter<ViewAdapter_ItemSearch.ItemSearchViewHolder> {
    private List<ItemSearch> mSearchs;
    Context mContext;
    public ViewAdapter_ItemSearch(List<ItemSearch> list, Context mContext) {
        this.mSearchs = list;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
    public void filterList(List<ItemSearch> filteredList) {
        mSearchs = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new ItemSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSearchViewHolder holder, int position) {
        ItemSearch Search = mSearchs.get(position);
        Glide.with(mContext).load(Search.getImageID()).into(holder.imgSearch);
        holder.tvTitleSearch.setText(Search.getResName());
        holder.tvTypeSearch.setText(Search.getDescription());
        holder.tvDistance.setText(String.format(Locale.getDefault(), "%.2f km", Search.getDistance()));
    }
    public void onClickGotoDetail(ItemSearch item) {
        Intent intent = new Intent(mContext, Restaurant_Home_Detail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item", item);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mSearchs != null) {
            return mSearchs.size();
        }
        return 0;
    }

    public class ItemSearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSearch;
        private TextView tvTitleSearch;
        private TextView tvTypeSearch;
        private TextView tvDistance;
        public ItemSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSearch = itemView.findViewById(R.id.img_search);
            tvTitleSearch = itemView.findViewById(R.id.tv_titlesearch);
            tvTypeSearch = itemView.findViewById(R.id.tv_type_search);
            tvDistance = itemView.findViewById(R.id.distance_search);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickGotoDetail(mSearchs.get(getAdapterPosition()));
                }
            });
        }
    }
}