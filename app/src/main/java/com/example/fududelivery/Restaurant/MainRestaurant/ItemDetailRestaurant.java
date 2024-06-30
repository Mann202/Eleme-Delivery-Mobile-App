package com.example.fududelivery.Restaurant.MainRestaurant;

import androidx.appcompat.widget.AppCompatButton;

public class ItemDetailRestaurant {
    String imageView;
    String dateText;
    String itemCountText;
    String nameText;
    String totalPriceText;
    String adressText;
    String orderID;
    String cusID;
    String shipperID;
    AppCompatButton detailBtn;
    public ItemDetailRestaurant() {
    }

    public ItemDetailRestaurant(String imageView, String dateText, String itemCountText, String nameText, String totalPriceText, String adressText, String orderID, String cusID, String shipperID) {
        this.imageView = imageView;
        this.dateText = dateText;
        this.itemCountText = itemCountText;
        this.nameText = nameText;
        this.totalPriceText = totalPriceText;
        this.adressText = adressText;
        this.orderID = orderID;
        this.cusID = cusID;
        this.shipperID = shipperID;
    }

    public String getImageView() {
        return imageView;
    }
    public String getShipperID() {return shipperID;}
    public void setShipperID(String shipperID) {this.shipperID = shipperID;}

    public String getCusID() {return cusID;}
    public void setCusID(String cusID) {this.cusID = cusID;}

    public void setOrderID(String orderID) {this.orderID = orderID;}
    public String getOrderID() {return orderID;}

    public AppCompatButton getDetailBtn() {
        return detailBtn;
    }
    public void setDetailBtn(AppCompatButton detailBtn) {
        this.detailBtn = detailBtn;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getItemCountText() {
        return itemCountText;
    }

    public void setItemCountText(String itemCountText) {
        this.itemCountText = itemCountText;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getTotalPriceText() {
        return totalPriceText;
    }

    public void setTotalPriceText(String totalPriceText) {
        this.totalPriceText = totalPriceText;
    }

    public String getAdressText() {
        return adressText;
    }

    public void setAdressText(String adressText) {
        this.adressText = adressText;
    }
}
