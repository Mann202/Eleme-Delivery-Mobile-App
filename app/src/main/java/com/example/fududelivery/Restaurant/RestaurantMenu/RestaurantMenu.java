package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantMenu extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    UserSessionManager userSessionManager;
    ArrayList<Category> item;
    CategoryItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantmenu);

        firebaseFirestore = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(this);

        ListView listView = findViewById(R.id.categoryListView);
        AppCompatButton addMenu = findViewById(R.id.addCategoryButton);
        ImageView backward = findViewById(R.id.backwardButton);

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        item = new ArrayList<>();
        adapter = new CategoryItemAdapter(this, R.layout.list_item_category, item);
        listView.setAdapter(adapter);

        firebaseFirestore.collection("Catelogy").whereEqualTo("userUid", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            item.add(new Category(documentSnapshot.getString("CateName")));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("CategoryDetail", "No categories found");
                    }
                } else {
                    Log.e("CategoryDetail", "Error getting documents: ", task.getException());
                }
            }
        });

        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.add(new Category(""));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
