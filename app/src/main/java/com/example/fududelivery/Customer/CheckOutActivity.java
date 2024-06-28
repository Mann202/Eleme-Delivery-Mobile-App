package com.example.fududelivery.Customer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.MyCart.CartAdapter;
import com.example.fududelivery.Customer.MyCart.CartDetail;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView OrderItemsRecyclerView;
    private Button changeORaddNewAddressBtn, placeOrderBtn;
    public static ArrayList<CartDetail> cartItems;
    private TextView fullname, fullAddress, phonenumber;
    private TextView subtotalTextView;
    private TextView deliveryFeeTextView;
    private TextView totalAmountTextView;
    public static Dialog loadingDialog;
    UserSessionManager userSessionManager;
    private static final int DELIVERY_FEE = 5000;
    public final static int SELECT_ADDRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        // Initialize UserSessionManager and get user details
        userSessionManager = new UserSessionManager(this);
        String deliveryAddress = userSessionManager.getUserAddress();
        String name = userSessionManager.getUserName();
        String phoneNumber = userSessionManager.getUserPhone();

        // Initialize loading dialog
        loadingDialog = new Dialog(CheckOutActivity.this);
        loadingDialog.setContentView(R.layout.item_loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Set up toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        subtotalTextView = findViewById(R.id.txt_subtotal_money);
        deliveryFeeTextView = findViewById(R.id.txt_deliveryfees_money);
        totalAmountTextView = findViewById(R.id.txt_total_money);
        fullname = findViewById(R.id.receiver_name);
        fullAddress = findViewById(R.id.detail_address);
        phonenumber = findViewById(R.id.receiver_phone_number);
        placeOrderBtn = findViewById(R.id.place_order_btn);
        OrderItemsRecyclerView = findViewById(R.id.rv_order_item);
        changeORaddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);

        // Set user information
        fullname.setText(name);
        fullAddress.setText(deliveryAddress);
        phonenumber.setText(phoneNumber);

        // Get cart items from intent
        cartItems = (ArrayList<CartDetail>) getIntent().getSerializableExtra("cartItems");

        // Set up RecyclerView
        CartAdapter cartAdapter = new CartAdapter(this, cartItems, null);
        OrderItemsRecyclerView.setAdapter(cartAdapter);
        OrderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calculate and display costs
        double subtotal = calculateSubtotal(cartItems);
        double total = subtotal + DELIVERY_FEE;

        subtotalTextView.setText(String.format("$%.2f", subtotal));
        deliveryFeeTextView.setText(String.format("$%.2f", DELIVERY_FEE));
        totalAmountTextView.setText(String.format("$%.2f", total));

        // Set up change or add new address button
        changeORaddNewAddressBtn.setVisibility(View.VISIBLE);
        changeORaddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this, MyAddressActivity.class).putExtra("MODE", SELECT_ADDRESS));
            }
        });

        // Set up place order button
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement place order functionality here
            }
        });

        // Accessing quantity
        if (DBquerries.addressesModelList.size() > 0 && DBquerries.selectedAddress >= 0) {
            String receiverName = DBquerries.addressesModelList.get(DBquerries.selectedAddress).getReceiverName();
            String receiverPhoneNumber = DBquerries.addressesModelList.get(DBquerries.selectedAddress).getReceiverPhoneNumber();
            String detailAddress = DBquerries.addressesModelList.get(DBquerries.selectedAddress).getDetailAddress();

            fullname.setText(receiverName);
            phonenumber.setText(receiverPhoneNumber);
            fullAddress.setText(detailAddress);
        }
    }

    private double calculateSubtotal(ArrayList<CartDetail> cartItems) {
        double subtotal = 0.0;
        for (CartDetail item : cartItems) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }
}
