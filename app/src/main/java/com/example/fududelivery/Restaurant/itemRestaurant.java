package com.example.fududelivery.Restaurant;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import org.w3c.dom.Text;

public class itemRestaurant {
    int imageView;
    String dateText;
    String itemCountText;
    String nameText;
    String totalPriceText;
    String adressText;

    AppCompatButton detailBtn;

    public itemRestaurant(int imageView, String dateText, String itemCountText, String nameText, String totalPriceText, String adressText) {
        this.imageView = imageView;
        this.dateText = dateText;
        this.itemCountText = itemCountText;
        this.nameText = nameText;
        this.totalPriceText = totalPriceText;
        this.adressText = adressText;
    }

    public int getImageView() {
        return imageView;
    }

    public AppCompatButton getDetailBtn() {
        return detailBtn;
    }
    public void setDetailBtn(AppCompatButton detailBtn) {
        this.detailBtn = detailBtn;
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

    public String getAdressText() {
        return adressText;
    }

    public void setAdressText(String adressText) {
        this.adressText = adressText;
    }
}
