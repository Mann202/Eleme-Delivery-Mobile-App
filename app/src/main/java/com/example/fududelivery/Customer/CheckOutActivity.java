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

import com.example.fududelivery.R;

public class CheckOutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView OrderItemsRecyclerView;
    private Button changeORaddNewAddressBtn, placeOrderBtn;
    public final static int SELECT_ADDRESS = 0;
    private TextView totalAmount, fullname, fullAddress, phonenumber, orderId;
    public static Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        loadingDialog = new Dialog(CheckOutActivity.this);
        loadingDialog.setContentView(R.layout.item_loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        totalAmount = findViewById(R.id.txt_total_money);
        fullname = findViewById(R.id.receiver_name);
        fullAddress = findViewById(R.id.detail_address);
        phonenumber = findViewById(R.id.receiver_phone_number);
        placeOrderBtn = findViewById(R.id.place_order_btn);


        OrderItemsRecyclerView = findViewById(R.id.rv_order_item);
        changeORaddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        OrderItemsRecyclerView.setLayoutManager(linearLayoutManager);

        changeORaddNewAddressBtn.setVisibility(View.VISIBLE);
        changeORaddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this, MyAddressActivity.class).putExtra("MODE", SELECT_ADDRESS));
            }
        });

        ///////accessing quantity
        String fullname = DBquerries.addressesModelList.get(DBquerries.selectedAddress).getReceiverName();
        String phonenumber = DBquerries.addressesModelList.get(DBquerries.selectedAddress).getReceiverPhoneNumber();
        String fullAddress = DBquerries.addressesModelList.get(DBquerries.selectedAddress).getDetailAddress();


    }
}
