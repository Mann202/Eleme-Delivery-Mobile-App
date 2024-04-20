package com.example.fududelivery.Home.FastFood.ItemFFList;

import com.example.fududelivery.Home.FastFood.ItemFastFood.ItemFastFood;

import java.util.List;

public class ItemFFList {
    private String nameFFoodList;
    private List<ItemFastFood> ffoods;

    public ItemFFList(String nameFFoodList, List<ItemFastFood> ffoods) {
        this.nameFFoodList = nameFFoodList;
        this.ffoods = ffoods;
    }

    public String getNameFFoodList() {
        return nameFFoodList;
    }

    public void setNameFFoodList(String nameFFoodList) {
        this.nameFFoodList = nameFFoodList;
    }

    public List<ItemFastFood> getFFoods() {
        return ffoods;
    }

    public void setFFoods(List<ItemFastFood> ffoods) {
        this.ffoods = ffoods;
    }
}
