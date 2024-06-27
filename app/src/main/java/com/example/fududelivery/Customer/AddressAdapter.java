package com.example.fududelivery.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.CustomMessageBox.CustomAlertDialog;
import com.example.fududelivery.CustomMessageBox.FailToast;
import com.example.fududelivery.Customer.GlobalConfig.GlobalConfig;
import com.example.fududelivery.Customer.Interfaces.IAddressAdapterListener;
import com.example.fududelivery.Customer.Model.Address;
import com.example.fududelivery.databinding.ItemAddressBinding;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private Context mContext;
    private List<Address> mAddresses;
    private RadioButton checkedRadioButton;
    private IAddressAdapterListener addressAdapterListener;
    private String userId;
    static private final int UPDATE_ADDRESS_REQUEST_CODE = 100;

    public AddressAdapter(Context mContext, List<Address> mAddresses, String id) {
        this.mContext = mContext;
        this.mAddresses = mAddresses;
        this.userId = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAddressBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = mAddresses.get(position);

        if (address.getAddressId().equals(GlobalConfig.choseAddressId) && address.getState().equals("default")) {
            holder.binding.choose.setChecked(true);
            holder.binding.defaultText.setVisibility(View.VISIBLE);
            checkedRadioButton = holder.binding.choose;
        }
        else if (address.getAddressId().equals(GlobalConfig.choseAddressId)) {
            holder.binding.choose.setChecked(true);
            holder.binding.defaultText.setVisibility(View.INVISIBLE);
            checkedRadioButton = holder.binding.choose;
        }
        else if (address.getState().equals("default")) {
            holder.binding.defaultText.setVisibility(View.VISIBLE);
            holder.binding.choose.setChecked(false);
        }
        else {
            holder.binding.choose.setChecked(false);
            holder.binding.defaultText.setVisibility(View.INVISIBLE);
        }
        holder.binding.receiverName.setText(address.getReceiverName());
        holder.binding.receiverPhoneNumber.setText(address.getReceiverPhoneNumber());
        holder.binding.detailAddress.setText(address.getDetailAddress());

        holder.binding.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.choose.isChecked()) {
                    checkedRadioButton.setChecked(false);

                    if (addressAdapterListener != null) {
                        addressAdapterListener.onCheckedChanged(address);
                    }

                    checkedRadioButton = holder.binding.choose;
                }
            }
        });

        holder.binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalConfig.updateAddressId = address.getAddressId();
                Intent intent = new Intent(mContext, UpdateAddAddressActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("userId",userId);
                ((Activity)mContext).startActivityForResult(intent, UPDATE_ADDRESS_REQUEST_CODE);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new CustomAlertDialog(mContext,"Delete this address?");
                CustomAlertDialog.binding.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (address.getState().equals("default")) {
                            new FailToast(mContext, "You cannot delete the default address!").showToast();
                            CustomAlertDialog.alertDialog.dismiss();
                        }
                        else {
//                            FirebaseFirestore.getInstance().getReference().child("Address").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(address.getAddressId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        new SuccessfulToast(mContext, "Delete address successfully!").showToast();
//                                        CustomAlertDialog.alertDialog.dismiss();
//                                        if (addressAdapterListener != null) {
//                                            addressAdapterListener.onDeleteAddress();
//                                        }
//                                    }
//                                }
//                            });
                        }
                    }
                });
                CustomAlertDialog.binding.btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomAlertDialog.alertDialog.dismiss();
                    }
                });
                CustomAlertDialog.showAlertDialog();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddresses == null ? 0 : mAddresses.size();
    }

    public void setAddressAdapterListener(IAddressAdapterListener addressAdapterListener) {
        this.addressAdapterListener = addressAdapterListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAddressBinding binding;

        public ViewHolder(@NonNull ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
