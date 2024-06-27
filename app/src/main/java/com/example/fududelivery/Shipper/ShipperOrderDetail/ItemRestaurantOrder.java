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

    public ItemRestaurantOrder(String itemQuantity, String itemName, String itemPrice, String itemDescription) {
        this.itemQuantity = itemQuantity;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
