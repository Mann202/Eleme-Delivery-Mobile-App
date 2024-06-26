package com.example.fududelivery.Customer.MyCart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.Model.Order;
import com.example.fududelivery.Shipper.ShipperMain.OrderViewHolder;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<CartModel> listItemSelected;
    Context context;

    public CartAdapter(Context context, ArrayList<CartModel> listItemSelected) {
        this.listItemSelected = listItemSelected;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        CartModel cartModel = listItemSelected.get(position);
        holder.bind(cartModel);
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName;
        TextView tvFoodCate;
        TextView tvFoodPrice;
        TextView tvFoodQuantity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvFoodName = itemView.findViewById(R.id.tv_foodname);
            tvFoodCate = itemView.findViewById(R.id.tv_foodcate);
            tvFoodPrice = itemView.findViewById(R.id.tv_foodprice);
            tvFoodQuantity = itemView.findViewById(R.id.tv_foodquantity);
        }

        public void bind(CartModel food) {
            if (food.getFoodName() != null) {
                String foodName = food.getFoodName();
                if (foodName.length() > 15) {
                    foodName = foodName.substring(0, 15) + "...";
                }
                tvFoodName.setText(foodName);
            } else {
                tvFoodName.setText("");
            }

            if (food.getFoodCate()!=null) {
                tvFoodCate.setText(food.getFoodCate());
            }  else tvFoodCate.setText("");
            if (food.getQuantity()!=null) {
                tvFoodQuantity.setText(food.getQuantity());
            }  else tvFoodQuantity.setText("");
            if (food.getTotalPrice()!=0.0f) {
                tvFoodPrice.setText(String.valueOf(food.getTotalPrice()));
            }  else tvFoodPrice.setText("0");


        }
    }
}
