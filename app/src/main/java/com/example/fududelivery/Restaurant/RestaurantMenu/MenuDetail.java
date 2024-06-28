package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.databinding.ActivityMainhomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuDetail extends AppCompatActivity {
    String menuID;
    ArrayList<Size> itemSize;
    ArrayList<Addition> itemAddition;
    ActivityMainhomeBinding binding;
    FirebaseFirestore firebaseFirestore;
    UserSessionManager userSessionManager;
    AppCompatButton confirmButton;
    SizeItemAdapter sizeAdapter;
    AdditionItemAdapter additionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        binding = ActivityMainhomeBinding.inflate(getLayoutInflater());

        firebaseFirestore = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(this);

        confirmButton = findViewById(R.id.confirmButtonMenu);
        AppCompatEditText nameTextField = findViewById(R.id.nameEditTextField);
        AppCompatEditText priceTextField = findViewById(R.id.priceTextField);
        AppCompatButton addSizeBtn = findViewById(R.id.addSizeBtn);
        AppCompatButton addToppingBtn = findViewById(R.id.addToppingBtn);

        Intent intent = getIntent();
        menuID = intent.getStringExtra("menuID");

        ListView sizeListView = findViewById(R.id.sizeListView);
        ListView additionListView = findViewById(R.id.additionListView);

        itemSize = new ArrayList<>();
        sizeAdapter = new SizeItemAdapter(this, R.layout.item_sizemenurestaurant, itemSize);
        sizeListView.setAdapter(sizeAdapter);

        itemAddition = new ArrayList<>();
        additionAdapter = new AdditionItemAdapter(this, R.layout.item_additionitem, itemAddition);
        additionListView.setAdapter(additionAdapter);

        firebaseFirestore.collection("Food").document(menuID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot name = task.getResult();
                    nameTextField.setText(name.getString("FoodName"));
                    priceTextField.setText(name.getDouble("Price").toString());
                } else {
                    nameTextField.setText("");
                    priceTextField.setText("");
                }
            }
        });

        if(!menuID.isEmpty()) {
            firebaseFirestore.collection("Size")
                    .whereEqualTo("FoodID", menuID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                itemSize.add(new Size(documentSnapshot.getString("SizeName"), documentSnapshot.getDouble("Price").toString()));
                            }
                            sizeAdapter.notifyDataSetChanged();
                        }
                    });

            firebaseFirestore.collection("Topping")
                    .whereEqualTo("FoodID", menuID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                itemAddition.add(new Addition(documentSnapshot.getString("ToppingName"), documentSnapshot.getDouble("Price").toString()));
                            }
                            additionAdapter.notifyDataSetChanged();
                        }
                    });
        }

        TextInputLayout nameMenuEditText = findViewById(R.id.NameEditText);
        TextInputLayout priceMenuEditText = findViewById(R.id.PriceEditText);

        addSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSize.add(new Size("", ""));
                sizeAdapter.notifyDataSetChanged();
            }
        });

        addToppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAddition.add(new Addition("", ""));
                additionAdapter.notifyDataSetChanged();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("Price", priceTextField.getText().toString());
                data.put("FoodName", nameTextField.getText().toString());
                if(!menuID.isEmpty()) {
                    firebaseFirestore.collection("Food").document(menuID).update(data)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MenuDetail.this, "Update menu's details successfullly.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MenuDetail.this, "Update menu's details failed. Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    firebaseFirestore.collection("Food").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(MenuDetail.this, "Update menu's details for new menu successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MenuDetail.this, "Update menu's details for new menu failed. Please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        findViewById(R.id.backwardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MenuDetail.this)
                        .setTitle("Discard")
                        .setMessage("Do you really want to cancel? All your change will be discard.")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Code to execute when OK is clicked
                            // For example, finish the activity or close the fragment
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        });
    }
}
