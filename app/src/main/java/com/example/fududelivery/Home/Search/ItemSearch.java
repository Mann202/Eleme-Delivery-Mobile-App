package com.example.fududelivery.Home.Search;

import java.io.Serializable;

public class ItemSearch implements Serializable {
    private String ImageID;
    private String Phone;
    private String AddressID;
    private String ResID;
    private String ResName;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddressID() {
        return AddressID;
    }

    public void setAddressID(String addressID) {
        AddressID = addressID;
    }

    private String Description;
    public ItemSearch() {
        // Constructor không đối số
    }
    public ItemSearch(String imageId, String ResID, String ResName, String Description) {
        this.ImageID = imageId;
        this.ResID=ResID;
        this.ResName=ResName;
        this.Description=Description;
    }
    public String getResID() {
        return ResID;
    }

    public String getImageID() {
        return ImageID;
    }

    public void setImageID(String imageId) {
        this.ImageID = imageId;
    }

    public void setResID(String ResID) {
        this.ResID = ResID;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String ResName) {
        this.ResName = ResName;
    }
    public String getDescription() {return Description;}
    public void setDescription(String Description) {this.Description = Description;}
}
