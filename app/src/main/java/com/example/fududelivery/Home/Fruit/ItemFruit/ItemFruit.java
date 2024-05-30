package com.example.fududelivery.Home.Fruit.ItemFruit;


public class ItemFruit {
    private int resourceId;
    private String title;
    private String type;
    public ItemFruit(int resourceId, String title, String type) {
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