package com.example.fududelivery.Customer.Address;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fududelivery.Customer.CheckOutActivity;
import com.example.fududelivery.Customer.Model.Address;
import com.example.fududelivery.Customer.MyCart.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if((long)task.getResult().get("list_size") == 0){
                                context.startActivity(new Intent(context, AddAddressActivity.class).putExtra("INTENT","deliveryIntent"));
                            }else {
                                for(long x=1;x<=(long)task.getResult().get("list_size");x++){
                                    addressesModelList.add(new Address(
                                            task.getResult().get("fullName_no"+x).toString()
                                            ,task.getResult().get("phoneNumber_no"+x).toString()
                                            ,task.getResult().get("detailAddress_no"+x).toString()
                                            ,(boolean)task.getResult().get("selected_"+x)

                                    ));
                                    if((boolean)task.getResult().get("selected_"+x)){
                                        selectedAddress=Integer.parseInt(String.valueOf(x-1));
                                    }
                                }
                                if (gotoDeliveryActivity) {
                                    context.startActivity(new Intent(context, CheckOutActivity.class));
                                }
                            }
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
    }

    public static void clearData(){
        addressesModelList.clear();

    }

}
