package com.example.fududelivery.Restaurant.RestaurantMenu;

public class Size {
    private String nameSize;
    private String priceSize;

    public Size(String nameSize, String priceSize) {
        this.nameSize = nameSize;
        this.priceSize = priceSize;
    }

    public String getNameSize() {
        return nameSize;
    }

    public String getPriceSize() {
        return priceSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public void setPriceSize(String priceSize) {
        this.priceSize = priceSize;
    }
}
