package com.example.fududelivery.Home.Dessert.ItemDessertList;


import com.example.fududelivery.Home.Dessert.ItemDessert.ItemDessert;

import java.util.List;

public class ItemDessertList {
    private String nameDessertList;
    private List<ItemDessert> desserts;

    public ItemDessertList(String nameDessertList, List<ItemDessert> desserts) {
        this.nameDessertList = nameDessertList;
        this.desserts = desserts;
    }

    public String getnameDessertList() {
        return nameDessertList;
    }

    public void setnameDessertList(String nameDessertList) {
        this.nameDessertList = nameDessertList;
    }

    public List<ItemDessert> getdesserts() {
        return desserts;
    }

    public void setdesserts(List<ItemDessert> desserts) {
        this.desserts = desserts;
    }
}