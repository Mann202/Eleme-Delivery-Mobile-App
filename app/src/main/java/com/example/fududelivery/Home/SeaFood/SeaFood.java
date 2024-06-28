package com.example.fududelivery.Home.SeaFood;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.Home.FilterBy.Filterby;
import com.example.fududelivery.Home.FilterBy.FilterbyList;
import com.example.fududelivery.Home.FilterBy.ViewAdapter_FilterbyList;
import com.example.fududelivery.Home.Search.ItemSearch;
import com.example.fududelivery.Home.Search.ViewAdapter_ItemSearch;
import com.example.fududelivery.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeaFood extends AppCompatActivity {
    ViewAdapter_ItemSearch viewAdapterFilterbyList;
    private Button back_button;
    ArrayList<ItemSearch> mRes;
    RecyclerView rcvFFoodList;
    FirebaseFirestore dbroot;
    TextInputEditText txtEdit;
    TextView txtTitle;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_fastfood);
        rcvFFoodList = findViewById(R.id.rcv_fflist);
        txtEdit = findViewById(R.id.finddetailitem);
        txtTitle = findViewById(R.id.title_res);
        txtTitle.setText("Seafood");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFFoodList.setLayoutManager(linearLayoutManager);
        rcvFFoodList.setAdapter(viewAdapterFilterbyList);
        back_button = findViewById(R.id.back_btn);
        mRes = new ArrayList<>();
        viewAdapterFilterbyList = new ViewAdapter_ItemSearch(mRes, this);
        rcvFFoodList.setAdapter(viewAdapterFilterbyList);
        dbroot = FirebaseFirestore.getInstance();
        EventChangeListner();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeaFood.this, Customer.class);
                startActivity(intent);
            }
        });
        txtEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchQuery = s.toString();

                // Perform filtering based on the search query
                filter(searchQuery);
            }
        });
    }
    private void filter(String text) {
        ArrayList<ItemSearch> filteredList = new ArrayList<>();
        for (ItemSearch item : mRes) {
            if (item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewAdapterFilterbyList.filterList(filteredList);
    }
    private void EventChangeListner() {
        dbroot.collection("Restaurant").orderBy("ResID", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        mRes.clear();
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                ItemSearch item = dc.getDocument().toObject(ItemSearch.class);
                                // Kiểm tra nếu Description chứa từ "Burger"
                                if (item.getDescription().contains("Sea food")) {
                                    mRes.add(item);
                                }
                            }
                            viewAdapterFilterbyList.notifyDataSetChanged();
                        }
                    }
                });
    }
}