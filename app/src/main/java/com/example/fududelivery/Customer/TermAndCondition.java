package com.example.fududelivery.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.Customer.MyCart.Cart;
import com.example.fududelivery.R;
import com.example.fududelivery.Customer.Address.MyAddressActivity;

public class TermAndCondition extends AppCompatActivity {
    Button btnCart;
    Button btnAddress;

    public final static int MANAGE_ADDRESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termandcondition);

        btnCart = findViewById(R.id.btn_cart);
        btnAddress = findViewById(R.id.btn_address);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(TermAndCondition.this, Cart.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(TermAndCondition.this, MyAddressActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

}
