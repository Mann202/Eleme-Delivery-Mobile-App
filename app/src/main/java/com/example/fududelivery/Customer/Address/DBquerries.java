package com.example.fududelivery.Customer.Address;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fududelivery.Customer.Model.Address;
import com.example.fududelivery.Customer.MyCart.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DBquerries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<Address> addressesModelList = new ArrayList<>();
    public static int selectedAddress = -1;

    public static void loadAddresses(final Context context, final Dialog loadingDialog, final boolean gotoDeliveryActivity) {
        addressesModelList.clear();

        firebaseFirestore.collection("UserAddress").document("Addresses").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot result = task.getResult();
                            if (result != null && result.exists()) {
                                long listSize = result.getLong("list_size");
                                if (listSize == 0) {
                                    context.startActivity(new Intent(context, AddAddressActivity.class).putExtra("INTENT", "deliveryIntent"));
                                } else {
                                    for (long x = 1; x <= listSize; x++) {
                                        addressesModelList.add(new Address(
                                                result.getString("detail_address_" + x),
                                                result.getString("name_" + x),
                                                result.getString("mobile_no_" + x),
                                                result.getBoolean("selected_" + x)
                                        ));
                                        if (result.getBoolean("selected_" + x)) {
                                            selectedAddress = (int) (x - 1);
                                        }
                                    }
                                    if (gotoDeliveryActivity) {
                                        context.startActivity(new Intent(context, Cart.class));
                                    }
                                }
                            } else {
                                context.startActivity(new Intent(context, AddAddressActivity.class).putExtra("INTENT", "deliveryIntent"));
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
    }

    public static void clearData(){
        addressesModelList.clear();

    }

}
