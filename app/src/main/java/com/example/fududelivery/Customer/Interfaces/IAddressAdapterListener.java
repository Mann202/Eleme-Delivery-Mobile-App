package com.example.fududelivery.Customer.Interfaces;

import com.example.fududelivery.Customer.Model.Address;

public interface IAddressAdapterListener {
    void onCheckedChanged(Address selectedAddress);
    void onDeleteAddress();
}
