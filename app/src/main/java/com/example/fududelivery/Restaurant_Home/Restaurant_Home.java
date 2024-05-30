package com.example.fududelivery.Restaurant_Home;

import java.io.Serializable;

public class Restaurant_Home implements Serializable {
    private String resourceId;
    private String AddressID;
    private String Des;
    private String ResID;
    private String ResName;

    private String AddressName;

    public Restaurant_Home(String resourceId, String addressID, String des, String resID, String resName, String addressName) {
        this.resourceId = resourceId;
        AddressID = addressID;
        Des = des;
        ResID = resID;
        ResName = resName;
        AddressName = addressName;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAddressID() {
        return AddressID;
    }

    public void setAddressID(String addressID) {
        AddressID = addressID;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getResID() {
        return ResID;
    }

    public void setResID(String resID) {
        ResID = resID;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }
}
