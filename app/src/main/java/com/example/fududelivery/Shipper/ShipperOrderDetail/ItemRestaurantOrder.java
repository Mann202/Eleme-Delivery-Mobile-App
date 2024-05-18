package com.example.fududelivery.Shipper.ShipperOrderDetail;

public class ItemRestaurantOrder {
    private String itemQuantity;
    private String itemName;
    private String itemPrice;
    private String itemDescription;

    @Override
    public String toString() {
        return "ItemRestaurantOrder{" +
                "itemQuantity='" + itemQuantity + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                '}';
    }

    // Constructor
    public ItemRestaurantOrder(String itemQuantity, String itemName, String itemPrice, String itemDescription) {
        this.itemQuantity = itemQuantity;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    // Getter for itemQuantity
    public String getItemQuantity() {
        return itemQuantity;
    }

    // Setter for itemQuantity
    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    // Getter for itemName
    public String getItemName() {
        return itemName;
    }

    // Setter for itemName
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Getter for itemPrice
    public String getItemPrice() {
        return itemPrice;
    }

    // Setter for itemPrice
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    // Getter for itemDescription
    public String getItemDescription() {
        return itemDescription;
    }

    // Setter for itemDescription
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
