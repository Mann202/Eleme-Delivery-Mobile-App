package com.example.fududelivery.Shipper.ShipperOrderDetail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ChangeCurrency;
import com.example.fududelivery.Shipper.ShipperMain.ShipperMain;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetail extends AppCompatActivity {
    ImageView navback;
    TextView ShipperName;
    ArrayList<ItemRestaurantOrder> item;
    RestaurantItemAdapter adapter;
    TextView SubTotal;
    TextView ServiceFee;
    TextView DeliveryFee;
    TextView Total;
    TextView OrderID;
    TextView OrderName;
    TextView OrderPhone;
    TextView OrderAddress;
    TextView RestaurantName;
    TextView RestaurantAddress;
    TextView Quantity;
    FirebaseFirestore firestoreInstance;
    Button StartButton;
    String orderID, resID, shippingStatus;
    ImageView phone;
    String phoneNumber;

    UserSessionManager userSessionManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(this);

        ListView listView = findViewById(R.id.rv_order_item);

        item = new ArrayList<>();
        adapter = new RestaurantItemAdapter(this, R.layout.item_res_detail, item);
        listView.setAdapter(adapter);

        SubTotal = findViewById(R.id.txt_subtotal_money);
        ServiceFee = findViewById(R.id.txt_servicefees_money);
        DeliveryFee = findViewById(R.id.txt_deliveryfees_money);
        Total = findViewById(R.id.txt_total_money);
        OrderID = findViewById(R.id.tv_orderid);
        OrderName = findViewById(R.id.tv_ordername);
        OrderPhone = findViewById(R.id.tv_orderphone);
        OrderAddress = findViewById(R.id.tv_orderaddress);
        RestaurantName = findViewById(R.id.tv_restaurant_name);
        RestaurantAddress = findViewById(R.id.tv_restaurant_address);
        Quantity = findViewById(R.id.txt_subtotal_item);
        StartButton = findViewById(R.id.start_button);
        phone = findViewById(R.id.call_icon);

        navback = findViewById(R.id.nav_back);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderID = bundle.getString("OrderID");
            resID = bundle.getString("ResID");
            OrderID.setText(String.format("#%s", bundle.getString("OrderID")));
            OrderName.setText(bundle.getString("Name"));
            OrderPhone.setText(bundle.getString("Phone"));
            OrderAddress.setText(bundle.getString("Address"));
            SubTotal.setText(ChangeCurrency.formatPrice(bundle.getFloat("subTotal")));
            ServiceFee.setText(ChangeCurrency.formatPrice(bundle.getFloat("serviceFee")));
            DeliveryFee.setText(ChangeCurrency.formatPrice(bundle.getFloat("ShippingFee")));
            Total.setText(ChangeCurrency.formatPrice(bundle.getFloat("OrderTotal")));
            Quantity.setText(String.format("(%s item)", bundle.getString("TotalQuantity")));
            firestoreInstance.collection("Orders").document(orderID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        System.out.println("OrderDetail: ship status: " + documentSnapshot.getString("ShippingStatus"));
                        shippingStatus = documentSnapshot.getString("ShippingStatus");
                        if (shippingStatus.equals("Ready")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StartButton.setText("Start delivery");
                                    StartButton.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (shippingStatus.equals("Start")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StartButton.setText("Finish");
                                    StartButton.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (shippingStatus.equals("Finish")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StartButton.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }
            });
        }
        firestoreInstance.collection("OrderDetail").whereEqualTo("OrderID", orderID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    ItemRestaurantOrder orderDetail = new ItemRestaurantOrder(document.getString("ItemQuantity") + "x", document.getString("FoodName"), ChangeCurrency.formatPrice(document.getDouble("TotalPrice")), document.getString("Description"));
                    item.add(orderDetail);
                    phoneNumber = document.getString("Phone");
                }
                adapter.notifyDataSetChanged();
            }
        });

        firestoreInstance.collection("Restaurant").document(resID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    RestaurantAddress.setText(documentSnapshot.getString("Address"));
                    RestaurantName.setText(documentSnapshot.getString("ResName"));
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = bundle.getString("Phone");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                if (ContextCompat.checkSelfPermission(OrderDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OrderDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    try {
                        startActivity(callIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(OrderDetail.this, "No app found to handle call intent", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        navback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentRef = firestoreInstance.collection("Orders").document(orderID);

                documentRef.update("ShipperID", userSessionManager.getUserInformation());
                documentRef.update("ResStatus", "Prepare");

                documentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String currentStatus = documentSnapshot.getString("ShippingStatus");

                        if (currentStatus.equals("Ready")) {
                            updateShippingStatus(documentRef, "Start", "Finish");
                        } else if (currentStatus.equals("Start")) {
                            updateShippingStatus(documentRef, "Finish", null);
                        }
                    }
                });
            }
        });

    }

    private void updateShippingStatus(DocumentReference documentRef, String newStatus, String buttonText) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("ShippingStatus", newStatus);

        documentRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (buttonText != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            StartButton.setText(buttonText);
                        }
                    });
                }
                if (newStatus.equals("Finish")) {
                    Intent intent = new Intent(OrderDetail.this, ShipperMain.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    recreate();
                }
            }
        });
    }
}
