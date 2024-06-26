package com.example.fududelivery.Customer.Address;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.fududelivery.R;

public class MyAddressActivity extends AppCompatActivity {

    private int previousAddress, mode;
    private RecyclerView recyclerView;
    private Button addNewAddressBtn;
//    private static AddressAdapter addressesAdapter;
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
        getSupportActionBar().setTitle("My addresses");
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
//        if (mode == SELECT_ADDRESS) {
//            deliverHere.setVisibility(View.VISIBLE);
//        } else {
//            deliverHere.setVisibility(View.GONE);
//        }


        // Click listener for deliverHere button
        deliverHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Just finish the activity without updating addresses
            }
        });

        // Initialize addressesAdapter with data from DBquerries
//        addressesAdapter = new AddressAdapter(DBquerries.addressesModelList, mode, loadingDialog);
//        recyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);


        // Click listener for addNewAddressBtn
        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAddressActivity.this, AddAddressActivity.class).putExtra("INTENT", "manage"));
            }
        });
    }

    // Method to refresh item in addressesAdapter
    public static void refreshItem(int deselect, int select) {
//        addressesAdapter.notifyItemChanged(deselect);
//        addressesAdapter.notifyItemChanged(select);
    }

    // Handle back button press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle back button press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
