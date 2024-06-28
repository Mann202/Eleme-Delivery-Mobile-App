package com.example.fududelivery.Customer;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Customer.Model.Address;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.fududelivery.Customer.CheckOutActivity.SELECT_ADDRESS;
import static com.example.fududelivery.Customer.MyAddressActivity.refreshItem;
import static com.example.fududelivery.Customer.TermAndCondition.MANAGE_ADDRESS;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<Address> addressesModelList;
    private int MODE, preSelectedPos;
    private Boolean refresh = false;
    private Dialog loadingDialog;
    String docID;

    public AddressAdapter(List<Address> addressesModelList, int MODE, Dialog loadingDialog) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
        preSelectedPos = DBquerries.selectedAddress;
        this.loadingDialog = loadingDialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = addressesModelList.get(position).getReceiverName();
        String mobileNo = addressesModelList.get(position).getReceiverPhoneNumber();
        String detailAddress = addressesModelList.get(position).getDetailAddress();
        Boolean selected = addressesModelList.get(position).getSelected();
        holder.setData(name, mobileNo, detailAddress, selected, position);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullname, address, phonenumber;
        private ImageView icon;
        private LinearLayout optionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            phonenumber = itemView.findViewById(R.id.phone_number);
            address = itemView.findViewById(R.id.address);
            icon = itemView.findViewById(R.id.icon_view);
//            optionContainer = itemView.findViewById(R.id.option_container);
        }

        private void setData(String name, String mobileNo, String detailAddress, Boolean selected, final int position) {
            fullname.setText(name);
            phonenumber.setText(mobileNo);
            address.setText(detailAddress);

            if (MODE == SELECT_ADDRESS) {
                icon.setImageResource(R.drawable.check);
                if (selected) {
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPos = position;
                } else {
                    icon.setVisibility(View.GONE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (preSelectedPos != position) {
                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preSelectedPos).setSelected(false);
                            refreshItem(preSelectedPos, position);
                            preSelectedPos = position;
                            DBquerries.selectedAddress = position;
                        }
                    }
                });
//            } else if (MODE == MANAGE_ADDRESS) {
//                optionContainer.setVisibility(View.GONE);
//                optionContainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        itemView.getContext().startActivity(new Intent(itemView.getContext(), AddAddressActivity.class).putExtra("INTENT", "update_address").putExtra("Position", position));
//                        refresh = false;
//                    }
//                });
//                optionContainer.getChildAt(1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        loadingDialog.show();
//                        Map<String, Object> addresses = new HashMap<>();
//                        int x = 0, selected = -1;
//                        for (int i = 0; i < addressesModelList.size(); i++) {
//                            if (i != position) {
//                                x++;
//                                addresses.put("detail_address_" + x, addressesModelList.get(i).getDetailAddress());
//                                addresses.put("name_" + x, addressesModelList.get(i).getReceiverName());
//                                addresses.put("mobile_no_" + x, addressesModelList.get(i).getReceiverPhoneNumber());
//                                addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
//                                if (addressesModelList.get(position).getSelected()) {
//                                    if (position - 1 >= 0) {
//                                        if (x == position) {
//                                            addresses.put("selected_" + x, true);
//                                            selected = x;
//                                        } else {
//                                            addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
//                                        }
//                                    } else {
//                                        if (x == 1) {
//                                            addresses.put("selected_" + x, true);
//                                            selected = x;
//                                        } else {
//                                            addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
//                                        }
//                                    }
//                                } else {
//                                    addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
//                                    if (addressesModelList.get(i).getSelected()) {
//                                        selected = x;
//                                    }
//                                }
//                            }
//                        }
//                        addresses.put("list_size", x);
//                        final int finalSelected = selected;
//                        FirebaseFirestore.getInstance().collection("UserAddress").document("Addresses")
//                                .set(addresses).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            DBquerries.addressesModelList.remove(position);
//                                            if (finalSelected != -1) {
//                                                DBquerries.selectedAddress = finalSelected - 1;
//                                                DBquerries.addressesModelList.get(finalSelected - 1).setSelected(true);
//                                            } else if (DBquerries.addressesModelList.size() == 0) {
//                                                DBquerries.selectedAddress = -1;
//                                            }
//                                            notifyDataSetChanged();
//                                        } else {
//                                            String error = task.getException().getMessage();
//                                            Toast.makeText(itemView.getContext(), error, Toast.LENGTH_SHORT).show();
//                                        }
//                                        loadingDialog.dismiss();
//                                    }
//                                });
//                        refresh = false;
//                    }
//                });
//                icon.setImageResource(R.drawable.menu);
//                icon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        optionContainer.setVisibility(View.VISIBLE);
//                        if (refresh) {
//                            refreshItem(preSelectedPos, preSelectedPos);
//                        } else {
//                            refresh = true;
//                        }
//                        preSelectedPos = position;
//                    }
//                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItem(preSelectedPos, preSelectedPos);
                        preSelectedPos = -1;
                    }
                });
            }
        }
    }
}
