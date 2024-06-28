package com.example.fududelivery.Customer.MyCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.Checkout.CheckOutActivity;
import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Shipper.ChangeCurrency;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private ArrayList<CartDetail> cartItem = new ArrayList<>();
    private CartAdapter adapter;
    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout emptyCart;

    private FirebaseFirestore firestoreInstance;
    private TextView totalPrice;
    ImageView btnNavBack;
    CheckBox checkboxSelectAll;
    Button btnDeleteAll;
    Button btnCheckout;
    UserSessionManager userSessionManager;
    String MyCartID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        // Initialize Firestore
        firestoreInstance = FirebaseFirestore.getInstance();

        //Initialize user
        userSessionManager = new UserSessionManager(this);
        System.out.println("Users: " + userSessionManager.getUserInformation());
        getMyCart();

        // Initialize views
        recyclerView = findViewById(R.id.rv_cart_product);
        emptyCart = findViewById(R.id.empty_cart);
        totalPrice = findViewById(R.id.total_price);
        btnNavBack = findViewById(R.id.nav_back);
        checkboxSelectAll = findViewById(R.id.checkbox_select_all);
        btnDeleteAll = findViewById(R.id.button_delete_all);
        btnCheckout = findViewById(R.id.checkout_cart);
//        swipeRefreshLayout = findViewById(R.id.refreshLayoutDoneRestaurant);

        // Setup RecyclerView
        adapter = new CartAdapter(this, cartItem, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup SwipeRefreshLayout
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
//        swipeRefreshLayout.setOnRefreshListener(this::loadCartItems);

        // Load cart items
        loadCartItems();

        btnNavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Customer.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });
        checkboxSelectAll.setOnClickListener(v -> {
            boolean isChecked = checkboxSelectAll.isChecked();
            adapter.selectAll(isChecked);
            calculateTotalPrice(cartItem);
        });
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CartDetail item : cartItem) {
                    String cartID = item.getCartID();
                    firestoreInstance.collection("CartDetail").document(cartID)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Xóa thành công trong Firebase, giờ xóa khỏi danh sách local
                                    cartItem.remove(item);
                                    adapter.notifyDataSetChanged();
                                    calculateTotalPrice(cartItem);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý khi thất bại
                                }
                            });
                }
            }
        });

    }
    private void loadCartItems() {
        // Show refresh indicator
//        swipeRefreshLayout.setRefreshing(true);

        // Get cart items from Firestore
        CollectionReference cartCollection = firestoreInstance.collection("CartDetail");
        cartCollection
                .whereEqualTo("UserID", userSessionManager.getUserInformation())
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
                            CartDetail cart = documentSnapshot.toObject(CartDetail.class);
                            cart.setCartID(documentSnapshot.getId());
                            cartItem.add(cart);
                        }

                        // Notify the adapter of data changes
                        adapter.notifyDataSetChanged();
                        calculateTotalPrice(cartItem);
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
    private void getMyCart(){
        CollectionReference cartCollection = firestoreInstance.collection("Cart");
        cartCollection
                .whereEqualTo("UserID", userSessionManager.getUserInformation())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            CartModel cart = documentSnapshot.toObject(CartModel.class);
                            MyCartID = cart.getCartID();
                            System.out.println("getMyCart: CartID :" + MyCartID);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public void calculateTotalPrice(ArrayList<CartDetail> cart) {
        float total = 0;
        for (CartDetail food : cart) {
            if (food.isSelected()) {  // chỉ tính các món đã chọn
                total += food.getTotalPrice();
            }
        }
        totalPrice.setText(ChangeCurrency.formatPrice(total));
    }
    public void deleteCartItem(CartDetail food) {
        String cartID = food.getCartID();
        firestoreInstance.collection("CartDetail").document(cartID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Xóa món ăn khỏi danh sách
                        cartItem.remove(food);
                        adapter.notifyDataSetChanged(); // Thông báo cho adapter về sự thay đổi dữ liệu
                        calculateTotalPrice(cartItem); // Cập nhật tổng giá trị sau khi xóa
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi thất bại
                    }
                });
    }
    public void updateSelectAllCheckbox(boolean isChecked) {
        // Cập nhật trạng thái checkbox "select all" dựa vào isChecked
        // isChecked = true khi tất cả các mục đều được chọn
        // isChecked = false khi có ít nhất một mục không được chọn

        // Ví dụ:
        checkboxSelectAll.setChecked(isChecked);
    }

}
