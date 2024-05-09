package com.example.fududelivery.Home.SeaFood.ItemSeaFoodList;

import com.example.fududelivery.Home.SeaFood.ItemSeaFood.ItemSeaFood;

import java.util.List;

public class ItemSFList {
    private String nameSeaFoodList;
    private List<ItemSeaFood> seafoods;

    public ItemSFList(String nameSeaFoodList, List<ItemSeaFood> seafoods) {
        this.nameSeaFoodList = nameSeaFoodList;
        this.seafoods = seafoods;
    }

    public String getnameSeaFoodList() {
        return nameSeaFoodList;
    }

    public void setnameSeaFoodList(String nameSeaFoodList) {
        this.nameSeaFoodList = nameSeaFoodList;
    }

    public List<ItemSeaFood> getseafoods() {
        return seafoods;
    }

    public void setseafoods(List<ItemSeaFood> seafoods) {
        this.seafoods = seafoods;
    }
}