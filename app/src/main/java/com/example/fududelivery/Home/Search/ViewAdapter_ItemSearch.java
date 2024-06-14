package com.example.fududelivery.Home.Search;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ItemSearch extends RecyclerView.Adapter<ViewAdapter_ItemSearch.ItemSearchViewHolder> {
    private List<ItemSearch> mSearchs;
    Context mContext;
    public ViewAdapter_ItemSearch(List<ItemSearch> list, Context mContext) {
        this.mSearchs = list;
        this.mContext = mContext;
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
        public ItemSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSearch = itemView.findViewById(R.id.img_search);
            tvTitleSearch = itemView.findViewById(R.id.tv_titlesearch);
            tvTypeSearch = itemView.findViewById(R.id.tv_type_search);
        }
    }
}