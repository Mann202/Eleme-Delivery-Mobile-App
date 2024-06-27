package com.example.fududelivery.Customer;

import static com.example.fududelivery.Customer.CheckOutActivity.SELECT_ADDRESS;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
//////////loading dialog

        loadingDialog = new Dialog(MyAddressActivity.this);
        loadingDialog.setContentView(R.layout.item_loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


//////////loading dialog

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My addresses");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_view_address_container);
        deliverHere = findViewById(R.id.deliver_here_button);
        addNewAddressBtn = findViewById(R.id.add_button);

        previousAddress = DBquerries.selectedAddress;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        mode = getIntent().getIntExtra("MODE", -1);
        if (mode == SELECT_ADDRESS) {
            deliverHere.setVisibility(View.VISIBLE);
        } else {
            deliverHere.setVisibility(View.GONE);
        }

        deliverHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBquerries.selectedAddress != previousAddress) {
                    final int previousAddressIndex = previousAddress;
                    loadingDialog.show();
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_" + String.valueOf(previousAddress + 1), false);
                    updateSelection.put("selected_" + String.valueOf(DBquerries.selectedAddress + 1), true);

                    previousAddress = DBquerries.selectedAddress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
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


        addressesAdapter = new AddressAdapter(DBquerries.addressesModelList, mode, loadingDialog);
        addressesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode != SELECT_ADDRESS) {
                    startActivity(new Intent(MyAddressActivity.this, AddAddressActivity.class).putExtra("INTENT", "manage"));
                } else {
                    startActivity(new Intent(MyAddressActivity.this, AddAddressActivity.class).putExtra("INTENT", "null"));
                }
            }
        });


    }


    public static void refreshItem(int deselect, int select) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (mode == SELECT_ADDRESS) {
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
        if (mode == SELECT_ADDRESS) {
            if (DBquerries.selectedAddress != previousAddress) {
                DBquerries.addressesModelList.get(DBquerries.selectedAddress).setSelected(false);
                DBquerries.addressesModelList.get(previousAddress).setSelected(true);
                DBquerries.selectedAddress = previousAddress;

            }
        }
        super.onBackPressed();
    }
}
