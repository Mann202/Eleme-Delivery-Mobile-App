package com.example.fududelivery.ExploreTitle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.List;

public class ViewAdapter_Title extends RecyclerView.Adapter<ViewAdapter_Title.TitleViewHolder> {
    private List<Title> mTitles;

    public void setData(List<Title> list) {
        this.mTitles = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exptitle, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        Title title = mTitles.get(position);
        if (title==null) {
            return;
        }
        holder.tvTitle.setText(title.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mTitles != null) {
            return mTitles.size();
        }
        return 0;
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
