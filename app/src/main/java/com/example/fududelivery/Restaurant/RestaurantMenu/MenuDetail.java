package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.databinding.ActivityMainhomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    Uri selectedImage;
    private static final int PICK_IMAGE = 1;
    String imageUrl;
    ImageView imv;

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
        imv = findViewById(R.id.displayImage);

        Intent intent = getIntent();
        menuID = intent.getStringExtra("menuID");

        itemSize = new ArrayList<>();
        sizeAdapter = new SizeItemAdapter(this, R.layout.item_sizemenurestaurant, itemSize);
        itemAddition = new ArrayList<>();
        additionAdapter = new AdditionItemAdapter(this, R.layout.item_additionitem, itemAddition);

        firebaseFirestore.collection("Food").document(menuID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot name = task.getResult();
                    nameTextField.setText(name.getString("FoodName"));
                    priceTextField.setText(name.getDouble("Price").toString());
                    imageUrl = name.getString("imageId");
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(imv);
                        findViewById(R.id.addImages).setVisibility(View.GONE);
                    }

                } else {
                    nameTextField.setText("");
                    priceTextField.setText("");
                }
            }
        });

        if (!menuID.isEmpty()) {
            firebaseFirestore.collection("Size").whereEqualTo("FoodID", menuID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        itemSize.add(new Size(documentSnapshot.getString("SizeName"), documentSnapshot.getDouble("Price").toString()));
                    }
                    sizeAdapter.notifyDataSetChanged();
                }
            });

            firebaseFirestore.collection("Topping").whereEqualTo("FoodID", menuID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        itemAddition.add(new Addition(documentSnapshot.getString("ToppingName"), documentSnapshot.getDouble("Price").toString()));
                    }
                    additionAdapter.notifyDataSetChanged();
                }
            });
        }

        findViewById(R.id.addImages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        TextInputLayout nameMenuEditText = findViewById(R.id.NameEditText);
        TextInputLayout priceMenuEditText = findViewById(R.id.PriceEditText);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMenuData();
            }
        });

        findViewById(R.id.backwardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MenuDetail.this).setTitle("Discard").setMessage("Do you really want to cancel? All your change will be discard.").setPositiveButton("OK", (dialog, which) -> {
                    // Code to execute when OK is clicked
                    // For example, finish the activity or close the fragment
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).create().show();
            }
        });

        nameTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    saveMenuData();
                }
            }
        });

        priceTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    saveMenuData();
                }
            }
        });
    }

    private void saveMenuData() {
        Map<String, Object> data = new HashMap<>();
        AppCompatEditText nameTextField = findViewById(R.id.nameEditTextField);
        AppCompatEditText priceTextField = findViewById(R.id.priceTextField);

        data.put("Price", Double.parseDouble(String.valueOf(priceTextField.getText())));
        data.put("FoodName", nameTextField.getText().toString());
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imv);
            findViewById(R.id.addImages).setVisibility(View.GONE);
        }

        if (!menuID.isEmpty()) {
            firebaseFirestore.collection("Food").document(menuID).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MenuDetail.this, "Update menu's details successfully.", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            if (selectedImage != null) {
                imv.setImageURI(selectedImage);
                findViewById(R.id.addImages).setVisibility(View.GONE);

                String imageName = UUID.randomUUID().toString();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference imageRef = storageRef.child("images/" + imageName);

                UploadTask uploadTask = imageRef.putFile(selectedImage);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();
                    });
                });
            }
        }
    }
}
