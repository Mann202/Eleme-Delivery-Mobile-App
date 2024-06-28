package com.example.fududelivery.Home.Search;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant_Home.Restaurant_Home;
import com.example.fududelivery.Restaurant_Home.ViewAdapter_RestaurantHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class Search_Main extends AppCompatActivity {
    AppCompatButton back_btn_search;
    RecyclerView rcvSearchList;
    ViewAdapter_ItemSearch viewAdapter_Search;
    ArrayList<ItemSearch> mRes;
    FirebaseFirestore dbroot;
    TextInputEditText txtEdit;
    ChipGroup chipGroup;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        back_btn_search = findViewById(R.id.back_btn_search);
        rcvSearchList = findViewById(R.id.rcv_searchlist);
        txtEdit = findViewById(R.id.finddetailtextinput);
        chipGroup = findViewById(R.id.chip_group);
        mRes = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSearchList.setLayoutManager(linearLayoutManager);
        rcvSearchList.setHasFixedSize(true);
        viewAdapter_Search = new ViewAdapter_ItemSearch(mRes, this);
        rcvSearchList.setAdapter(viewAdapter_Search);
        dbroot = FirebaseFirestore.getInstance();
        EventChangeListner();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Seafood");
        arrayList.add("Fruits");
        arrayList.add("Desserts");
        arrayList.add("Fast Food");
        arrayList.add("Korean");
        arrayList.add("Healthy");
        arrayList.add("Noodle");
        arrayList.add("Drink");
        Random random = new Random();
        for (String option : arrayList) {
            Chip chip = (Chip) LayoutInflater.from(Search_Main.this).inflate(R.layout.chip_layout, null);
            chip.setText(option);
            chip.setId(random.nextInt());
            chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                    if (checkedIds.isEmpty()) {
                        txtEdit.setText("");
                        filter("");
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i : checkedIds) {
                            Chip chip = findViewById(i);
                            stringBuilder.append(chip.getText());
                        }
                        filter(stringBuilder.toString());
                        txtEdit.setText(stringBuilder.toString());
                    }
                }
            });
            chipGroup.addView(chip);
        }
        back_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Main.this, Customer.class);
                startActivity(intent);
            }
        });
//        DocumentReference document_Search = dbroot.collection("Restaurant").document("S9Q4NI6YYDBqmvaD7c8W");
//        document_Search.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    mRes.add(new ItemSearch(documentSnapshot.getString("ImageID"), documentSnapshot.getString("ResID"), documentSnapshot.getString("ResName"), documentSnapshot.getString("Description")));
//                    mRes.add(new ItemSearch(documentSnapshot.getString("ImageID"), documentSnapshot.getString("ResID"), documentSnapshot.getString("ResName"), documentSnapshot.getString("Description")));
//                    mRes.add(new ItemSearch(documentSnapshot.getString("ImageID"), documentSnapshot.getString("ResID"), documentSnapshot.getString("ResName"), documentSnapshot.getString("Description")));
//                    mRes.add(new ItemSearch(documentSnapshot.getString("ImageID"), documentSnapshot.getString("ResID"), documentSnapshot.getString("ResName"), documentSnapshot.getString("Description")));
//                    mRes.add(new ItemSearch(documentSnapshot.getString("ImageID"), documentSnapshot.getString("ResID"), documentSnapshot.getString("ResName"), documentSnapshot.getString("Description")));
//                }
//                viewAdapter_Search.notifyDataSetChanged();
//            }
//        });
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
            } else if (item.getResName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewAdapter_Search.filterList(filteredList);
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
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                mRes.add(dc.getDocument().toObject(ItemSearch.class));
                            }
                            viewAdapter_Search.notifyDataSetChanged();
                        }
                    }
                });
    }
}
