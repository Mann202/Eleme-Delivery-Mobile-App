package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryDetail extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    UserSessionManager userSessionManager;
    ArrayList<Menu> item;
    MenuItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorydetail);
        Intent intent = getIntent();
        String nameMenu = intent.getStringExtra("nameMenu");
        String cateID = intent.getStringExtra("cateID");

        firebaseFirestore = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(this);

        ListView listView = findViewById(R.id.menuListView);
        AppCompatButton addMenu = findViewById(R.id.addMenu);

        item = new ArrayList<>();
        adapter = new MenuItemAdapter(this, R.layout.list_item_menu, item);
        listView.setAdapter(adapter);

        firebaseFirestore.collection("Food")
                .whereEqualTo("CateID", cateID)
                .whereEqualTo("ResID", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            item.add(new Menu(documentSnapshot.getString("FoodName")));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        TextView CategoryTitle = findViewById(R.id.CategoryTitle);
        CategoryTitle.setText(nameMenu);

        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.add(new Menu("New Menu."));
                adapter.notifyDataSetChanged();
                Map<String, Object> data = new HashMap<>();
                data.put("FoodName", "New Menu.");
                data.put("ResID", userSessionManager.getUserInformation());
                data.put("Price", 0);
                data.put("CateID", cateID);
                firebaseFirestore.collection("Food").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Intent menuDetailItent = new Intent(getApplicationContext(), MenuDetail.class);
                        String menuID = task.getResult().getId();
                        menuDetailItent.putExtra("menuID", menuID);
                        startActivity(menuDetailItent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }
        });
    }
}
