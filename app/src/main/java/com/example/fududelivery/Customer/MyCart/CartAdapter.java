package com.example.fududelivery.Customer.MyCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ChangeCurrency;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<CartDetail> listItemSelected;
    Context context;
    private Cart cartActivity;
    private boolean isSelectAllChecked = false;

    public CartAdapter(Context context, ArrayList<CartDetail> listItemSelected, Cart cartActivity) {
        this.listItemSelected = listItemSelected;
        this.context = context;
        this.cartActivity = cartActivity; // Lưu tham chiếu đến CartActivity
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        CartDetail food = listItemSelected.get(position);
        holder.bind(food);
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvFoodQuantity.getText().toString());
                quantity += 1;
                food.setQuantity(String.valueOf(quantity));
                food.setTotalPrice(quantity * food.getPrice());

                holder.tvFoodQuantity.setText(String.valueOf(quantity));
                holder.tvFoodPrice.setText(ChangeCurrency.formatPrice(food.getTotalPrice()));
                cartActivity.calculateTotalPrice(listItemSelected);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvFoodQuantity.getText().toString());
                if (quantity > 1) {
                    quantity -= 1;
                    food.setQuantity(String.valueOf(quantity));
                    food.setTotalPrice(quantity * food.getPrice());

                    holder.tvFoodQuantity.setText(String.valueOf(quantity));
                    holder.tvFoodPrice.setText(ChangeCurrency.formatPrice(food.getTotalPrice()));
                    cartActivity.calculateTotalPrice(listItemSelected);
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartActivity.deleteCartItem(food);
            }
        });
//        holder.cbxSelect.setOnClickListener(v -> {
//            food.setSelected(holder.cbxSelect.isChecked());
//            cartActivity.calculateTotalPrice(listItemSelected);
//        });

        holder.cbxSelect.setOnCheckedChangeListener(null); // Tránh lặp đăng ký sự kiện
        holder.cbxSelect.setChecked(food.isSelected());

        holder.cbxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                food.setSelected(isChecked);
                cartActivity.calculateTotalPrice(getSelectedItems());
                updateSelectAllCheckbox();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }
    public void selectAll(boolean isSelected) {
        for (CartDetail item : listItemSelected) {
            item.setSelected(isSelected);
        }
        notifyDataSetChanged();
    }

    public ArrayList<CartDetail> getSelectedItems() {
        ArrayList<CartDetail> selectedItems = new ArrayList<>();
        for (CartDetail item : listItemSelected) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
    public void updateSelectAllCheckbox() {
        // Kiểm tra xem tất cả các mục đã chọn hay chưa
        boolean allSelected = true;
        for (CartDetail item : listItemSelected) {
            if (!item.isSelected()) {
                allSelected = false;
                break;
            }
        }

        // Cập nhật trạng thái của checkbox "select all"
        isSelectAllChecked = allSelected;
        cartActivity.updateSelectAllCheckbox(isSelectAllChecked);
    }

    public boolean isSelectAllChecked() {
        return isSelectAllChecked;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName;
        TextView tvFoodPrice;
        TextView tvFoodQuantity;
        TextView btnMinus;
        TextView btnPlus;
        RelativeLayout btnDelete;
        CheckBox cbxSelect;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvFoodName = itemView.findViewById(R.id.tv_foodname);
            tvFoodPrice = itemView.findViewById(R.id.tv_foodprice);
            tvFoodQuantity = itemView.findViewById(R.id.tv_foodquantity);
            btnMinus = itemView.findViewById(R.id.tv_minus);
            btnPlus = itemView.findViewById(R.id.tv_plus);
            imgFood = itemView.findViewById(R.id.img_food);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            cbxSelect = itemView.findViewById(R.id.checkbox_select);
        }

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
                tvFoodQuantity.setText(food.getQuantity());
            }  else tvFoodQuantity.setText("");
            if (food.getTotalPrice()!=0.0f) {
                tvFoodPrice.setText(String.valueOf(food.getTotalPrice()));
            }  else tvFoodPrice.setText("0 VND");

            Picasso.get().load(food.getImageID()).into(imgFood);
            cbxSelect.setChecked(food.isSelected());
        }
    }
}
