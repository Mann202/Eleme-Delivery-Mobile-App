package com.example.fududelivery.Restaurant.RestaurantMenu;

public class Addition {
    private String nameAddition;
    private String priceAddition;

    public Addition(String nameAddition, String priceAddition) {
        this.nameAddition = nameAddition;
        this.priceAddition = priceAddition;
    }

    public String getNameAddition() {
        return nameAddition;
    }

    public String getPriceAddition() {
        return priceAddition;
    }

    public void setNameAddition(String nameAddition) {
        this.nameAddition = nameAddition;
    }

    public void setPriceAddition(String priceAddition) {
        this.priceAddition = priceAddition;
    }
}
