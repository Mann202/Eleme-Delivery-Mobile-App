package com.example.fududelivery.Restaurant_Home;

import java.io.Serializable;

public class Food implements Serializable {
    private String CateID;
    private String FoodName;
    private Long Price;
    private String ResID;
    private String imageId;

    public String getCateID() {
        return CateID;
    }

    public void setCateID(String cateID) {
        CateID = cateID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        this.FoodName = foodName;
    }

    public long getPrice() {
        return Price; // Chuyển đổi từ Long sang double khi cần thiết
    }

    public void setPrice(Long price) {
        this.Price = price;
    }

    public Food() {
        // Constructor không đối số
    }

    public Food(String CateId, String FoodName, Long Price, String ResID, String imageId) {
        this.CateID = CateId;
        this.FoodName = FoodName;
        this.Price = Price;
        this.ResID = ResID;
        this.imageId = imageId;
    }

    public String getResID() {
        return ResID;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setResID(String ResID) {
        this.ResID = ResID;
    }
}