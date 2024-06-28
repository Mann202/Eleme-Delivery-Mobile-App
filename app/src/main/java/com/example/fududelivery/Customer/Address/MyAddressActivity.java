package com.example.fududelivery.Customer.Address;

import static com.example.fududelivery.Customer.CheckOutActivity.SELECT_ADDRESS;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MyAddressActivity extends AppCompatActivity {

    private int previousAddress, mode;
    private RecyclerView recyclerView;
    private Button addNewAddressBtn;
    private static AddressAdapter addressesAdapter;
    private Button deliverHere;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_myaddress);

        // Initialize views and loading dialog
        loadingDialog = new Dialog(MyAddressActivity.this);
        loadingDialog.setContentView(R.layout.item_loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_address);
        deliverHere = findViewById(R.id.deliver_here_button);
        addNewAddressBtn = findViewById(R.id.add_button);

        // Get selected address index from DBquerries
        previousAddress = DBquerries.selectedAddress;

        // Set up recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Get mode from intent
        mode = getIntent().getIntExtra("MODE", -1);
        if (mode == SELECT_ADDRESS) {
            deliverHere.setVisibility(View.VISIBLE);
        } else {
            deliverHere.setVisibility(View.GONE);
        }


        // Click listener for deliverHere button
        deliverHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBquerries.selectedAddress != previousAddress) {
                    final int previousAddressIndex = previousAddress;
                    loadingDialog.show();

                    // Update selected address in Firestore
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_" + String.valueOf(previousAddress + 1), false);
                    updateSelection.put("selected_" + String.valueOf(DBquerries.selectedAddress + 1), true);

                    previousAddress = DBquerries.selectedAddress;

                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                            .update(updateSelection)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                    } else {
                                        previousAddress = previousAddressIndex;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(MyAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                } else {
                    finish();
                }
            }
        });

        // Initialize addressesAdapter with data from DBquerries
        addressesAdapter = new AddressAdapter(DBquerries.addressesModelList, mode, loadingDialog);
        addressesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        // Click listener for addNewAddressBtn
        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode != SELECT_ADDRESS){
                    startActivity(new Intent(MyAddressActivity.this,AddAddressActivity.class).putExtra("INTENT","manage"));
                }else {
                    startActivity(new Intent(MyAddressActivity.this,AddAddressActivity.class).putExtra("INTENT","null"));
                }
            }
        });
    }

    // Method to refresh item in addressesAdapter
    public static void refreshItem(int deselect, int select) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    // Handle back button press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            if(mode == SELECT_ADDRESS) {
                if (DBquerries.selectedAddress != previousAddress) {
                    DBquerries.addressesModelList.get(DBquerries.selectedAddress).setSelected(false);
                    DBquerries.addressesModelList.get(previousAddress).setSelected(true);
                    DBquerries.selectedAddress = previousAddress;

                }
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mode == SELECT_ADDRESS) {
            if (DBquerries.selectedAddress != previousAddress) {
                DBquerries.addressesModelList.get(DBquerries.selectedAddress).setSelected(false);
                DBquerries.addressesModelList.get(previousAddress).setSelected(true);
                DBquerries.selectedAddress = previousAddress;

            }
        }
        super.onBackPressed();
    }
}
