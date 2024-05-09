package com.example.fududelivery.Home.Bakery.ItemBakeryList;


import com.example.fududelivery.Home.Bakery.ItemBakery.ItemBakery;

import java.util.List;

public class ItemBakeryList {
    private String nameBakeryList;
    private List<ItemBakery> bakeries;

    public ItemBakeryList(String nameBakeryList, List<ItemBakery> bakeries) {
        this.nameBakeryList = nameBakeryList;
        this.bakeries = bakeries;
    }

    public String getnameBakeryList() {
        return nameBakeryList;
    }

    public void setnameBakeryList(String nameBakeryList) {
        this.nameBakeryList = nameBakeryList;
    }

    public List<ItemBakery> getbakeries() {
        return bakeries;
    }

    public void setbakeries(List<ItemBakery> bakeries) {
        this.bakeries = bakeries;
    }
}
