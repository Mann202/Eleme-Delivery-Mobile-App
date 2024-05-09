package com.example.fududelivery.Home.Fruit.ItemFruitList;

import com.example.fududelivery.Home.Fruit.ItemFruit.ItemFruit;

import java.util.List;

public class ItemFruitList {
    private String nameFruitList;
    private List<ItemFruit> Fruits;

    public ItemFruitList(String nameFruitList, List<ItemFruit> Fruits) {
        this.nameFruitList = nameFruitList;
        this.Fruits = Fruits;
    }

    public String getnameFruitList() {
        return nameFruitList;
    }

    public void setnameFruitList(String nameFruitList) {
        this.nameFruitList = nameFruitList;
    }

    public List<ItemFruit> getFruits() {
        return Fruits;
    }

    public void setFruits(List<ItemFruit> Fruits) {
        this.Fruits = Fruits;
    }
}