package com.example.fududelivery.Home.Seemore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;

import java.util.ArrayList;

public class ViewAdapter_Seemore extends RecyclerView.Adapter<ViewAdapter_Seemore.SeemoreViewHolder> {
    private ArrayList<Seemore> mSeemores;
    private Context context;

    public ViewAdapter_Seemore(ArrayList<Seemore> mSeemores, Context context) {
        this.mSeemores = mSeemores;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapter_Seemore.SeemoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seemore,parent,false);
        return new SeemoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_Seemore.SeemoreViewHolder holder, int position) {
        holder.imgView.setImageResource(mSeemores.get(position).getResourceId());
        holder.txtView_title.setText(mSeemores.get(position).getTitle());
        holder.txtView_star.setText(mSeemores.get(position).getStar_rate());
        holder.txtView_distance.setText(mSeemores.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return mSeemores.size();
    }

    public class SeemoreViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView txtView_title;
        TextView txtView_star;
        TextView txtView_distance;
        public SeemoreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_food_sm);
            txtView_title = itemView.findViewById(R.id.tv_title_sm);
            txtView_star = itemView.findViewById(R.id.star_rate_sm);
            txtView_distance = itemView.findViewById(R.id.distance_sm);
        }
    }
}
