package com.example.fududelivery.Customer.MyOrder;

import androidx.appcompat.widget.AppCompatButton;

public class MyOrderInfor {
    int imageView;
    String dateText;
    String itemCountText;
    String nameText;
    String totalPriceText;
    String statusText;

    AppCompatButton reorderBtn, rateBtn;

    public MyOrderInfor(int imageView, String dateText, String itemCountText, String nameText, String totalPriceText, String statusText) {
        this.imageView = imageView;
        this.dateText = dateText;
        this.itemCountText = itemCountText;
        this.nameText = nameText;
        this.totalPriceText = totalPriceText;
        this.statusText = statusText;
    }
    public int getImageView() {
        return imageView;
    }

    public AppCompatButton getReorderBtn() {
        return reorderBtn;
    }
    public void setReorderBtn(AppCompatButton reorderBtn) {
        this.reorderBtn = reorderBtn;
    }
    public AppCompatButton getRateBtn() {
        return rateBtn;
    }
    public void setRateBtn(AppCompatButton rateBtn) {
        this.rateBtn = rateBtn;
    }

    public void setImageView(int imageView) {
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

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
