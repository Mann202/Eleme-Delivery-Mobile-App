package com.example.fududelivery.Customer.Model;

public class Address {

    private String detailAddress;
    private String receiverName;
    private String receiverPhoneNumber;
    private Boolean selected;

    public Address() {
    }

    public Address(String detailAddress, String receiverName, String receiverPhoneNumber, Boolean selected) {
        this.detailAddress = detailAddress;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.selected = selected;
    }


    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }
    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
