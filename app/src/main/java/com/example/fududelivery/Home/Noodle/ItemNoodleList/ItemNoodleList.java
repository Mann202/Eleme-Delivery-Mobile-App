package com.example.fududelivery.Home.Noodle.ItemNoodleList;

import com.example.fududelivery.Home.Noodle.ItemNoodle.ItemNoodle;

import java.util.List;

public class ItemNoodleList {
    private String nameNoodleList;
    private List<ItemNoodle> noodles;

    public ItemNoodleList(String nameNoodleList, List<ItemNoodle> noodles) {
        this.nameNoodleList = nameNoodleList;
        this.noodles = noodles;
    }

    public String getnameNoodleList() {
        return nameNoodleList;
    }

    public void setnameNoodleList(String nameNoodleList) {
        this.nameNoodleList = nameNoodleList;
    }

    public List<ItemNoodle> getnoodles() {
        return noodles;
    }

    public void setnoodles(List<ItemNoodle> noodles) {
        this.noodles = noodles;
    }
}