package com.example.fududelivery.ExploreList;

import com.example.fududelivery.ExploreTitle.Title;
import com.example.fududelivery.Food.Food;

import java.util.List;

public class ExploreList {
    private String nameExploreList;
    private List<Title> titles;

    public ExploreList(String nameExploreList, List<Title> titles) {
        this.nameExploreList = nameExploreList;
        this.titles = titles;
    }

    public String getNameExploreList() {
        return nameExploreList;
    }

    public void setNameExploreList(String nameExploreList) {
        this.nameExploreList = nameExploreList;
    }

    public List<Title> getTitle() {
        return titles;
    }

    public void setTitles(List<Title> foods) {
        this.titles = titles;
    }
}