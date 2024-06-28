package com.example.fududelivery.Customer.Checkout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.MyCart.CartDetail;
import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ChangeCurrency;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<CartDetail> cartItem = new ArrayList<>();
    private CheckoutAdapter adapter;
    UserSessionManager userSessionManager;
    private FirebaseFirestore firestoreInstance;
    ImageView btnNavBack;
    AppCompatButton btnFinish;
    TextView tvSubtotal, tvServiceFee, tvDelivery, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        // Initialize Firestore
        firestoreInstance = FirebaseFirestore.getInstance();

        //Initialize user
        userSessionManager = new UserSessionManager(this);
        System.out.println("Users: " + userSessionManager.getUserAddress());

        // Initialize views
        recyclerView = findViewById(R.id.rv_cart_product);
        tvTotal = findViewById(R.id.txt_total_money);
        tvDelivery = findViewById(R.id.txt_deliveryfees_money);
        tvSubtotal = findViewById(R.id.txt_subtotal_money);
        tvServiceFee = findViewById(R.id.txt_servicefees_money);
        btnNavBack = findViewById(R.id.nav_back);
        btnFinish = findViewById(R.id.btn_finish);
//        swipeRefreshLayout = findViewById(R.id.refreshLayoutDoneRestaurant);

        // Setup RecyclerView
        adapter = new CheckoutAdapter(cartItem, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup SwipeRefreshLayout
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
//        swipeRefreshLayout.setOnRefreshListener(this::loadCartItems);

        // Load cart items
        loadCartItems();

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
                for (CartDetail cart : cartItem) {
                    deleteCartItem(cart);
                }
                Intent intent = new Intent(CheckOutActivity.this, Customer.class);
                startActivity(intent);
            }
        });
    }

    private void loadCartItems() {
        // Show refresh indicator
//        swipeRefreshLayout.setRefreshing(true);

        // Get cart items from Firestore
        CollectionReference cartCollection = firestoreInstance.collection("CartDetail");
        cartCollection.whereEqualTo("UserID", userSessionManager.getUserInformation()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Hide refresh indicator
//                        swipeRefreshLayout.setRefreshing(false);

                // Clear the current cart items
                cartItem.clear();

                // Add the new cart items
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    CartDetail cart = documentSnapshot.toObject(CartDetail.class);
                    cart.setCartID(documentSnapshot.getId());
                    System.out.println("Checkout: " + cart);
                    cartItem.add(cart);
                }

                // Notify the adapter of data changes
                adapter.notifyDataSetChanged();
                calculateMoney(cartItem);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Hide refresh indicator
//                        swipeRefreshLayout.setRefreshing(false);
                // Handle the failure
            }
        });
    }

    private float calculateMoney(ArrayList<CartDetail> cartItems) {
        float subtotal = 0;
        for (CartDetail item : cartItems) {
            subtotal += item.getTotalPrice();
        }
        tvSubtotal.setText(ChangeCurrency.formatPrice(subtotal));
        tvTotal.setText(ChangeCurrency.formatPrice(subtotal + 35000));

        return subtotal;

    }

    private void addOrder() {
        CollectionReference orderCollection = firestoreInstance.collection("Orders");

        // Create a sample document
        Map<String, Object> newDocument = new HashMap<>();
        newDocument.put("UserID", userSessionManager.getUserInformation());
        newDocument.put("ShippingStatus", "Ready");
        newDocument.put("ResStatus", "");
        newDocument.put("ResID", "65J8YO0kKOcxFNMLh0p95nlxlgv1");
        newDocument.put("ShipperID", "");
        newDocument.put("ResAddress", "Ngã Tư Thủ Đức, TPHCM");
        newDocument.put("OrderTotal", calculateMoney(cartItem) + 35000);
        newDocument.put("Date", "01/07/2024");
        newDocument.put("ShippingFee", 0);
        newDocument.put("TotalQuantity", String.valueOf(cartItem.size()));
        newDocument.put("address", userSessionManager.getUserAddress());
        newDocument.put("name", userSessionManager.getUserName());
        newDocument.put("serviceFee", 10000);
        newDocument.put("subTotal", calculateMoney(cartItem));


        orderCollection.add(newDocument).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CheckOutActivity.this, getString(R.string.msg_order_sucessfully), Toast.LENGTH_SHORT).show();
                // Reload the cart items to reflect the new addition
                loadCartItems();
            }
        });
    }

    public void deleteCartItem(CartDetail food) {
        String cartID = food.getCartID();
        firestoreInstance.collection("CartDetail").document(cartID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cartItem.remove(food);
            }
        });
    }
}
