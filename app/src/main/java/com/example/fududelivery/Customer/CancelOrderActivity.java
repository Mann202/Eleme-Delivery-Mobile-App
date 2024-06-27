package com.example.fududelivery.Customer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.Customer.MyOrder.MainOrder;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class CancelOrderActivity extends AppCompatActivity {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cancelorder);
        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Orders").document(orderId).update("ResStatus", "Done");
                firebaseFirestore.collection("Orders").document(orderId).update("ShippingStatus", "Cancel").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Dialog dialog = new Dialog(CancelOrderActivity.this);
                        dialog.setContentView(R.layout.item_customer_cancelorderpopup);
                        dialog.show();

                        Button okButton = dialog.findViewById(R.id.save_button);
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent homeIntent = new Intent(CancelOrderActivity.this, MainOrder.class);
                                startActivity(homeIntent);
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}
