package com.example.fududelivery.Home.Drink.ItemDrink;
public class ItemDrink {
    private int resourceId;
    private String title;
    private String type;
    public ItemDrink(int resourceId, String title, String type) {
        this.resourceId=resourceId;
        this.title=title;
        this.type=type;
    }
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}