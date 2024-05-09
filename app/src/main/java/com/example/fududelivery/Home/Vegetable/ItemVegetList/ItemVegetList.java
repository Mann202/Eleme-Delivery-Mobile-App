package com.example.fududelivery.Home.Vegetable.ItemVegetList;

import com.example.fududelivery.Home.FastFood.ItemFastFood.ItemFastFood;
import com.example.fududelivery.Home.Vegetable.ItemVegetable.ItemVegetable;

import java.util.List;

public class ItemVegetList {
    private String nameVegetList;
    private List<ItemVegetable> Vegets;

    public ItemVegetList(String nameVegetList, List<ItemVegetable> Vegets) {
        this.nameVegetList = nameVegetList;
        this.Vegets = Vegets;
    }

    public String getNameVegetList() {
        return nameVegetList;
    }

    public void setNameVegetList(String nameVegetList) {
        this.nameVegetList = nameVegetList;
    }

    public List<ItemVegetable> getVegets() {
        return Vegets;
    }

    public void setVegets(List<ItemVegetable> Vegets) {
        this.Vegets = Vegets;
    }
}