package com.example.fududelivery.Home.Drink.ItemDrinkList;

import com.example.fududelivery.Home.Drink.ItemDrink.ItemDrink;
import com.example.fududelivery.Home.FastFood.ItemFastFood.ItemFastFood;

import java.util.List;

public class ItemDrinkList {
    private String nameDrinkList;
    private List<ItemDrink> drinks;

    public ItemDrinkList(String nameFFoodList, List<ItemDrink> drinks) {
        this.nameDrinkList = nameFFoodList;
        this.drinks = drinks;
    }

    public String getNameDrinkList() {
        return nameDrinkList;
    }

    public void setNameDrinkList(String nameFFoodList) {
        this.nameDrinkList = nameFFoodList;
    }

    public List<ItemDrink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<ItemDrink> drinks) {
        this.drinks = drinks;
    }
}
