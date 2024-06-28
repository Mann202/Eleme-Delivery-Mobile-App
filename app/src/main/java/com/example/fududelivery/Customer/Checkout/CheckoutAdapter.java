package com.example.fududelivery.Customer.Checkout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.MyCart.Cart;
import com.example.fududelivery.Customer.MyCart.CartAdapter;
import com.example.fududelivery.Customer.MyCart.CartDetail;
import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ChangeCurrency;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.Viewholder> {
    ArrayList<CartDetail> listItemSelected;
    Context context;

    public CheckoutAdapter(ArrayList<CartDetail> listItemSelected, Context context) {
        this.listItemSelected = listItemSelected;
        this.context = context;
    }

    @NonNull
    @Override
    public CheckoutAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cartcheckout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.Viewholder holder, int position) {
        CartDetail food = listItemSelected.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName;
        TextView tvFoodPrice;
        TextView tvFoodQuantity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvFoodName = itemView.findViewById(R.id.tv_foodname);
            tvFoodPrice = itemView.findViewById(R.id.tv_foodprice);
            tvFoodQuantity = itemView.findViewById(R.id.tv_foodquantity);
            imgFood = itemView.findViewById(R.id.img_food);
        }

        @SuppressLint("SetTextI18n")
        public void bind(CartDetail food) {
            if (food.getFoodName() != null) {
                String foodName = food.getFoodName();
                if (foodName.length() > 15) {
                    foodName = foodName.substring(0, 15) + "...";
                }
                tvFoodName.setText(foodName);
            } else {
                tvFoodName.setText("");
            }
            if (food.getQuantity()!=null) {
                tvFoodQuantity.setText("x" + food.getQuantity());
            }  else tvFoodQuantity.setText("");
            if (food.getTotalPrice()!=0.0f) {
                tvFoodPrice.setText(ChangeCurrency.formatPrice(food.getTotalPrice()));
            }  else tvFoodPrice.setText("0 VND");

            Picasso.get().load(food.getImageID()).into(imgFood);
        }
    }
}
