package com.example.fududelivery.Home.Restaurant_Item;


public class ItemRestaurant {
    private int resourceId;
    private String title;
    private String star_rate;
    private String distance;

    public String getStar_rate() {
        return star_rate;
    }

    public void setStar_rate(String star_rate) {
        this.star_rate = star_rate;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ItemRestaurant(int resourceId, String title, String distance, String star_rate) {
        this.resourceId=resourceId;
        this.title=title;
        this.star_rate=star_rate;
        this.distance=distance;
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
}

