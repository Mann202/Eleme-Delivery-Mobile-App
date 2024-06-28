package com.example.fududelivery.Home.FilterBy;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_Filterby extends RecyclerView.Adapter<ViewAdapter_Filterby.FilterbyViewHolder> {

    private List<Filterby> mListFilter;
    public void setData(List<Filterby> list) {
        this.mListFilter = list;
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public FilterbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exptitle, parent, false);
        return new FilterbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterbyViewHolder holder, int position) {
        Filterby filterlist = mListFilter.get(position);
        if(filterlist == null) {
            return;
        }
        holder.tvNameFilter.setText(filterlist.getFilterby());
    }

    @Override
    public int getItemCount() {
        if(mListFilter != null) {
            return mListFilter.size();
        }
        return 0;
    }

    public class FilterbyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameFilter;
        public FilterbyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFilter = itemView.findViewById(R.id.tv_title);
        }
    }
}

