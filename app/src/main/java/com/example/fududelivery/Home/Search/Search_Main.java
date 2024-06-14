package com.example.fududelivery.Home.Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant_Home.Restaurant_Home;
import com.example.fududelivery.Restaurant_Home.ViewAdapter_RestaurantHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Search_Main extends AppCompatActivity {
    AppCompatButton back_btn_search;
    RecyclerView rcvSearchList;
    ViewAdapter_ItemSearch viewAdapter_Search;
    ArrayList<ItemSearch> mRes;
    FirebaseFirestore dbroot;
    TextInputEditText txtEdit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        back_btn_search = findViewById(R.id.back_btn_search);
        rcvSearchList = findViewById(R.id.rcv_searchlist);
        txtEdit = findViewById(R.id.finddetailtextinput);
        mRes = new ArrayList<ItemSearch>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSearchList.setLayoutManager(linearLayoutManager);
        rcvSearchList.setHasFixedSize(true);
        viewAdapter_Search = new ViewAdapter_ItemSearch(mRes, this);
        rcvSearchList.setAdapter(viewAdapter_Search);
        dbroot = FirebaseFirestore.getInstance();
        EventChangeListner();
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

            }
        });
    }
    private void filter(String text) {
        ArrayList<ItemSearch> filteredList = new ArrayList<>();
        for (ItemSearch item : mRes) {
            if (item.getResName().toLowerCase().contains(text.toLowerCase())) {
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
                    Log.e("Firestore error",error.getMessage());
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
