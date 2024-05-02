package com.example.fududelivery.Restaurant.RestaurantDetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.example.fududelivery.Reference.ChangeCurrency;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantDetail extends AppCompatActivity {
    TextView subTotalPriceText;
    TextView servicePriceText;
    TextView totalPriceText;
    TextView orderIdText;
    TextView nameText;
    TextView addressText;
    TextView phoneText;
    TextView nameShipperText;
    TextView phoneShipperText;
    TextView vehiclePlatesText;
    TextView vehicleLicensesText;
    TextView shipperFeeText;
    FirebaseFirestore firestoreInstance;
    ArrayList<ItemRestaurantOrder> item;
    RestaurantItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdetail);

        firestoreInstance = FirebaseFirestore.getInstance();

        ListView listView = findViewById(R.id.listViewItem);

        // Initialize ArrayList and Adapter
        item = new ArrayList<>();
        adapter = new RestaurantItemAdapter(this, R.layout.item_restaurantdettail, item);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        String orderID = intent.getStringExtra("OrderID");
        String address = intent.getStringExtra("Address");
        String name = intent.getStringExtra("Name");
        String shipperID = intent.getStringExtra("ShipperID");

        subTotalPriceText = findViewById(R.id.subtotalprice);
        servicePriceText = findViewById(R.id.serviceprice);
        totalPriceText = findViewById(R.id.totalprice);
        orderIdText = findViewById(R.id.order);
        nameText = findViewById(R.id.name);
        addressText = findViewById(R.id.address);
        phoneText = findViewById(R.id.phonenumber);
        shipperFeeText = findViewById(R.id.shippingFee);

        nameShipperText = findViewById(R.id.nameShipper);
        phoneShipperText = findViewById(R.id.phoneShipper);
        vehiclePlatesText = findViewById(R.id.vehiclePlates);
        vehicleLicensesText = findViewById(R.id.vehicleLicense);

        orderIdText.setText("#" + orderID);
        addressText.setText(address);
        nameText.setText(name);

        firestoreInstance.collection("Users")
                .whereEqualTo("userUid", shipperID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            nameShipperText.setText(document.getString("name"));
                            phoneShipperText.setText(document.getString("phone"));
                            vehiclePlatesText.setText(document.getString("vehiclePlate"));
                            vehicleLicensesText.setText(document.getString("vehicleLicense"));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Debug", "Error getting Orders documents.", e);
                    }
                });

        firestoreInstance.collection("Orders").document(orderID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                totalPriceText.setText(ChangeCurrency.formatPrice(documentSnapshot.getDouble("subTotal") + documentSnapshot.getDouble("serviceFee") + documentSnapshot.getDouble("ShippingFee")));
                subTotalPriceText.setText(ChangeCurrency.formatPrice(documentSnapshot.getDouble("subTotal")));
                servicePriceText.setText(ChangeCurrency.formatPrice(documentSnapshot.getDouble("serviceFee")));
                shipperFeeText.setText(ChangeCurrency.formatPrice(documentSnapshot.getDouble("ShippingFee")));
            }
        });

        firestoreInstance.collection("OrderDetail")
                .whereEqualTo("OrderID", orderID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            item.add(new ItemRestaurantOrder(document.getString("ItemQuantity" + "x"), document.getString("FoodName"), ChangeCurrency.formatPrice(document.getDouble("TotalPrice")), document.getString("Description")));
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
    }
}
