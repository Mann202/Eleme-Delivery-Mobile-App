package com.example.fududelivery.Restaurant.MainRestaurant;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantInforPreparingAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    Context context;
    List<ItemDetailRestaurant> items;
    FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
    private RestaurantInforDoneAdapter.OnItemClickListener listener;

    public RestaurantInforPreparingAdapter(Context context, List<ItemDetailRestaurant> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurantprepairing, parent, false));
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RestaurantInforDoneAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Picasso.get().load(items.get(position).getImageView()).into(holder.imageView);
        holder.nameText.setText(items.get(position).getNameText());
        holder.itemCountText.setText(items.get(position).getItemCountText());
        holder.adressText.setText(items.get(position).getAdressText());
        holder.dateText.setText(items.get(position).getDateText());
        holder.totalPriceText.setText(items.get(position).getTotalPriceText());
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("ResStatus", "Done");

                firestoreInstance.collection("Orders").document(items.get(position).orderID).update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        items.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(view.getContext(), R.string.msg_order_completed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showCancelDialog(int position, String orderID) {

        new AlertDialog.Builder(context).setTitle("Cancel").setMessage(R.string.msg_do_you_really_want_to_cancel).setPositiveButton(context.getString(R.string.msg_ok), (dialog, which) -> {
            // Code to execute when OK is clicked
            // For example, finish the activity or close the fragment
            firestoreInstance.collection("Orders").document(orderID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Debug", "DocumentSnapshot successfully deleted!");
                    removeItem(position);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Debug", "Error deleting document", e);
                }
            });
        }).setNegativeButton(context.getString(R.string.msg_cancel), (dialog, which) -> dialog.dismiss()).create().show();
    }

    private void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }
}
