package com.example.fududelivery.Customer.MyCart;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private ArrayList<CartModel> cartItem = new ArrayList<>();
    private CartAdapter adapter;
    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout emptyCart;

    private FirebaseFirestore firestoreInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        // Initialize Firestore
        firestoreInstance = FirebaseFirestore.getInstance();

        // Initialize views
        recyclerView = findViewById(R.id.rv_cart_product);
        emptyCart = findViewById(R.id.empty_cart);
//        swipeRefreshLayout = findViewById(R.id.refreshLayoutDoneRestaurant);

        // Setup RecyclerView
        adapter = new CartAdapter(this, cartItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup SwipeRefreshLayout
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
//        swipeRefreshLayout.setOnRefreshListener(this::loadCartItems);

        // Load cart items
        loadCartItems();
    }

    private void loadCartItems() {
        // Show refresh indicator
//        swipeRefreshLayout.setRefreshing(true);

        // Get cart items from Firestore
        CollectionReference cartCollection = firestoreInstance.collection("CartDetail");
        cartCollection
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Hide refresh indicator
//                        swipeRefreshLayout.setRefreshing(false);

                        if (queryDocumentSnapshots.isEmpty()) {
                            emptyCart.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            emptyCart.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        // Clear the current cart items
                        cartItem.clear();

                        // Add the new cart items
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            CartModel cart = documentSnapshot.toObject(CartModel.class);
                            cart.setCartID(documentSnapshot.getId());
                            cartItem.add(cart);
                        }

                        // Notify the adapter of data changes
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Hide refresh indicator
//                        swipeRefreshLayout.setRefreshing(false);
                        // Handle the failure
                    }
                });
    }
}
