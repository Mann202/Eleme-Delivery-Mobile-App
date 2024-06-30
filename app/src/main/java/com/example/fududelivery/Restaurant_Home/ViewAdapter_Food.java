package com.example.fududelivery.Restaurant_Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAdapter_Food extends RecyclerView.Adapter<ViewAdapter_Food.ItemFoodHolder>{
    private List<Food> mFoods;
    FirebaseFirestore dbroot;
    UserSessionManager userSessionManager;
    Context mContext;
    public ViewAdapter_Food(List<Food> list, Context mContext) {
        this.mFoods = list;
        this.mContext = mContext;
        dbroot = FirebaseFirestore.getInstance();
        notifyDataSetChanged();
    }
    public void filterList(List<Food> filteredList) {
        mFoods = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu_food, parent, false);
        userSessionManager = new UserSessionManager(mContext);
        return new ItemFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFoodHolder holder, int position) {
        Food Foods = mFoods.get(position);
        Glide.with(mContext).load(Foods.getImageId()).into(holder.imgFood);
        holder.tvFoodName.setText(Foods.getFoodName());
        holder.tvPrice.setText(String.valueOf(Foods.getPrice()));
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag",Foods.toString());
                addOrder(Foods);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        }
        return 0;
    }
    private void addOrder(Food cart) {
        CollectionReference orderCollection = dbroot.collection("CartDetail");
        // Create a sample document
        Map<String, Object> newDocument = new HashMap<>();
        newDocument.put("FoodName", cart.getFoodName());
        newDocument.put("Price",cart.getPrice());
        newDocument.put("Quantity", "1");
        newDocument.put("ResID", cart.getResID());
        newDocument.put("Selected", true);
        newDocument.put("TotalPrice", cart.getPrice());
        newDocument.put("UserID", userSessionManager.getUserInformation());
        newDocument.put("imageID", cart.getImageId());
        orderCollection
                .add(newDocument)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(mContext, mContext.getString(R.string.msg_add_to_cart_successfully), Toast.LENGTH_SHORT).show();
                        // Reload the cart items to reflect the new addition
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Error adding document", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public class ItemFoodHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView tvFoodName;
        private TextView tvPrice;
        private ImageView btnAdd;
        public ItemFoodHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_menu_food);
            tvFoodName = itemView.findViewById(R.id.tv_titleFood);
            tvPrice = itemView.findViewById(R.id.price_food);
            btnAdd = itemView.findViewById(R.id.add_food);
        }
    }

}
