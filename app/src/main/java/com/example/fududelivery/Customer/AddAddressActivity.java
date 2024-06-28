package com.example.fududelivery.Customer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fududelivery.Customer.Model.Address;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private Button saveBtn;
    private EditText detailAddress, fullName, phoneNumber;
    private Dialog loadingDialog;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_addnewaddress);

        // Initialize views and loading dialog
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add a new address");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveBtn = findViewById(R.id.save_button);
        fullName = findViewById(R.id.full_name);
        phoneNumber = findViewById(R.id.phone_number);
        detailAddress = findViewById(R.id.detail_address);

        //////////loading dialog
        loadingDialog = new Dialog(AddAddressActivity.this);
        loadingDialog.setContentView(R.layout.item_loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //////////loading dialog

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(detailAddress.getText())
                        && !TextUtils.isEmpty(fullName.getText())
                        && !TextUtils.isEmpty(phoneNumber.getText())
                        && phoneNumber.getText().length() == 10) {

                    loadingDialog.show();

                    Map<String, Object> addAddress = new HashMap<>();
                    addAddress.put("mobile_no_" + (DBquerries.addressesModelList.size() + 1), phoneNumber.getText().toString());
                    addAddress.put("name_" + (DBquerries.addressesModelList.size() + 1), fullName.getText().toString());
                    addAddress.put("detail_address_" + (DBquerries.addressesModelList.size() + 1), detailAddress.getText().toString());
                    addAddress.put("list_size", (long) (DBquerries.addressesModelList.size() + 1));
                    addAddress.put("selected_" + (DBquerries.addressesModelList.size() + 1), true);

                    FirebaseFirestore.getInstance()
                            .collection("UserAddress").document("Addresses")
                            .update(addAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        DBquerries.addressesModelList.add(new Address(
                                                detailAddress.getText().toString(),
                                                fullName.getText().toString(),
                                                phoneNumber.getText().toString(),
                                                true
                                        ));
                                        DBquerries.selectedAddress = DBquerries.addressesModelList.size() - 1;

                                        if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                            Intent deliveryIntent = new Intent(AddAddressActivity.this, CheckOutActivity.class);
                                            startActivity(deliveryIntent);
                                        } else {
                                            MyAddressActivity.refreshItem(DBquerries.selectedAddress, DBquerries.addressesModelList.size() - 1);
                                        }
                                        finish();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                } else {
                    Toast.makeText(AddAddressActivity.this, "Please provide valid details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
