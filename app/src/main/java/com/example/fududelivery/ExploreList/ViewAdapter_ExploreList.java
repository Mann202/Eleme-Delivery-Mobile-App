package com.example.fududelivery.ExploreList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.ExploreTitle.ViewAdapter_Title;
import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_ExploreList extends RecyclerView.Adapter<ViewAdapter_ExploreList.ExploreListViewHolder> {

    private final Context mContext;
    private List<ExploreList> mListExplore;
    public ViewAdapter_ExploreList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<ExploreList> list) {
        this.mListExplore = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public ExploreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explist, parent, false);
        return new ExploreListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreListViewHolder holder, int position) {
        ExploreList exploreList = mListExplore.get(position);
        if(exploreList == null) {
            return;
        }
        holder.tvExploreMore.setText(exploreList.getNameExploreList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvTitle.setLayoutManager(linearLayoutManager);
        ViewAdapter_Title titleAdapter = new ViewAdapter_Title();
        titleAdapter.setData(exploreList.getTitle());
        holder.rcvTitle.setAdapter(titleAdapter);
    }

    @Override
    public int getItemCount() {
        if(mListExplore != null) {
            return mListExplore.size();
        }
        return 0;
    }

    public class ExploreListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvExploreMore;
        private RecyclerView rcvTitle;
        public ExploreListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExploreMore = itemView.findViewById(R.id.tv_expmore);
            rcvTitle = itemView.findViewById(R.id.rcv_exptitle);
        }
    }
}

