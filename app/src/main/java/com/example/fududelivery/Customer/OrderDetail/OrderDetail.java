package com.example.fududelivery.Customer.OrderDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderDetail extends AppCompatActivity {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orderdetail);

        userSessionManager = new UserSessionManager(this);

        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");

        findViewById(R.id.backIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView subTotalText = findViewById(R.id.subtotalprice);
        TextView serviceText = findViewById(R.id.serviceprice);
        TextView totalText = findViewById(R.id.totalprice);
        TextView orderText = findViewById(R.id.order);
        TextView nameText = findViewById(R.id.name);
        TextView phoneText = findViewById(R.id.phonenumber);
        TextView addressText = findViewById(R.id.address);
        TextView vehicleLicenseText = findViewById(R.id.vehicleLicense);
        TextView vehiclePlatesText = findViewById(R.id.vehiclePlates);
        TextView nameShipperText = findViewById(R.id.nameShipper);
        TextView phoneShipperText = findViewById(R.id.phoneShipper);

        firebaseFirestore.collection("Orders").document(orderId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                subTotalText.setText(documentSnapshot.getDouble("subTotal").toString());
                serviceText.setText(documentSnapshot.getDouble("serviceFee").toString());

                Double total = documentSnapshot.getDouble("subTotal") + documentSnapshot.getDouble("serviceFee");

                totalText.setText(total.toString());
                orderText.setText(orderId);
                nameText.setText(documentSnapshot.getString("name"));
                phoneText.setText(documentSnapshot.getString("Phone"));
                addressText.setText(documentSnapshot.getString("address"));
                firebaseFirestore.collection("Users").whereEqualTo("userUid", documentSnapshot.getString("ShipperID")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                            vehicleLicenseText.setText(documentSnapshot1.getString("vehicleLicense"));
                            vehiclePlatesText.setText(documentSnapshot1.getString("vehiclePlate"));
                            nameShipperText.setText(documentSnapshot1.getString("name"));
                            phoneShipperText.setText(documentSnapshot1.getString("phone"));
                        }
                    }
                });
            }
        });
    }
}
