package com.example.fududelivery.Customer.MyCart;

public class CartModel {
    String UserID;
    String CartID;

    public CartModel() {
    }

    public CartModel(String userID, String cartID) {
        UserID = userID;
        CartID = cartID;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "UserID='" + UserID + '\'' +
                ", CartID='" + CartID + '\'' +
                '}';
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCartID() {
        return CartID;
    }

    public void setCartID(String cartID) {
        CartID = cartID;
    }
}
