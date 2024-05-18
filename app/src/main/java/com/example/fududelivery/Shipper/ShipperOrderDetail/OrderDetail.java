package com.example.fududelivery.Shipper.ShipperOrderDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ChangeCurrency;
import com.example.fududelivery.Shipper.Model.Order;
import com.example.fududelivery.Shipper.ShipperMain.ShipperMain;
import com.google.android.gms.tasks.OnFailureListener;
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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        firestoreInstance = FirebaseFirestore.getInstance();

        ListView listView = findViewById(R.id.rv_order_item);

        // Initialize ArrayList and Adapter
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
//            shippingStatus = bundle.getString("ShippingStatus");
            firestoreInstance.collection("Orders")
                    .document(orderID)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                            } else {
                                // Không tìm thấy tài liệu với ID cụ thể
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi có lỗi xảy ra
                        }
                    });
        }
        firestoreInstance.collection("OrderDetail")
                .whereEqualTo("OrderID", orderID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ItemRestaurantOrder orderDetail = new ItemRestaurantOrder(
                                    document.getString("ItemQuantity") + "x",
                                    document.getString("FoodName"),
                                    ChangeCurrency.formatPrice(document.getDouble("TotalPrice")),
                                    document.getString("Description"));
                            System.out.println("Order Detail:" + orderDetail);

                            item.add(orderDetail);
                        }
                        // Notify adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Debug", "Error getting Orders documents.", e);
                    }
                });

        firestoreInstance.collection("Restaurant")
                .document(resID)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            RestaurantAddress.setText(documentSnapshot.getString("Address"));
                            RestaurantName.setText(documentSnapshot.getString("ResName"));
                        } else {
                            // Không tìm thấy tài liệu với ID cụ thể
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });

        navback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShipperMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
//        StartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DocumentReference documentRef = firestoreInstance.collection("Orders").document(orderID);
//
//                Map<String, Object> updates = new HashMap<>();
//                updates.put("ShippingStatus", "Start");
//
//                documentRef.update(updates)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                StartButton.setText("Finish");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Xử lý khi có lỗi xảy ra
//                            }
//                        });
//            }
//        });
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentRef = firestoreInstance.collection("Orders").document(orderID);

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
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });

    }

    private void updateShippingStatus(DocumentReference documentRef, String newStatus, String buttonText) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("ShippingStatus", newStatus);

        documentRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        }else{
                            recreate(); // Hàm để tải lại chi tiết đơn hàng

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
    }

}
