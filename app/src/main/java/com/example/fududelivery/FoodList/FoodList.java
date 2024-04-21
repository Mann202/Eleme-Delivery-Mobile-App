package com.example.fududelivery.FoodList;

import com.example.fududelivery.Food.Food;

import java.util.List;

public class FoodList {
    private String nameFoodList;
    private List<Food> foods;

    public FoodList(String nameFoodList, List<Food> foods) {
        this.nameFoodList = nameFoodList;
        this.foods = foods;
    }

    public String getNameFoodList() {
        return nameFoodList;
    }

    public void setNameFoodList(String nameFoodList) {
        this.nameFoodList = nameFoodList;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
