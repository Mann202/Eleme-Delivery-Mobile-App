package com.example.fududelivery.Home.FilterBy;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_FilterbyList extends RecyclerView.Adapter<ViewAdapter_FilterbyList.FilterbyListViewHolder> {

    private final Context mContext;
    private List<FilterbyList> mFilterbyList;
    public ViewAdapter_FilterbyList(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<FilterbyList> list) {
        this.mFilterbyList = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public FilterbyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filterbylist, parent, false);
        return new FilterbyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterbyListViewHolder holder, int position) {
        FilterbyList FilterbyList = mFilterbyList.get(position);
        if(FilterbyList == null) {
            return;
        }
        holder.tv.setText(FilterbyList.getNameFilterbyList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvFilter.setLayoutManager(linearLayoutManager);
        ViewAdapter_Filterby FilterbyAdapter = new ViewAdapter_Filterby();
        FilterbyAdapter.setData(FilterbyList.getFilters());
        holder.rcvFilter.setAdapter(FilterbyAdapter);
    }

    @Override
    public int getItemCount() {
        if(mFilterbyList != null) {
            return mFilterbyList.size();
        }
        return 0;
    }

    public class FilterbyListViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private RecyclerView rcvFilter;
        public FilterbyListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name_filter);
            rcvFilter = itemView.findViewById(R.id.rcv_filters);
        }
    }
}
