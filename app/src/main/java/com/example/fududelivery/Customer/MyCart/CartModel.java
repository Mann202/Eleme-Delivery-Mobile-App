package com.example.fududelivery.Customer.MyCart;

public class CartModel {
    String CartID;
    String FoodID;
    String FoodName;
    String FoodCate;
    String Quantity;
    Float TotalPrice;

    public CartModel() {
    }

    public CartModel(String cartID, String foodID, String foodName, String foodCate, String quantity, Float totalPrice) {
        CartID = cartID;
        FoodID = foodID;
        FoodName = foodName;
        FoodCate = foodCate;
        Quantity = quantity;
        TotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "CartID='" + CartID + '\'' +
                ", FoodID='" + FoodID + '\'' +
                ", FoodName='" + FoodName + '\'' +
                ", FoodCate='" + FoodCate + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", TotalPrice='" + TotalPrice + '\'' +
                '}';
    }

    public String getCartID() {
        return CartID;
    }

    public void setCartID(String cartID) {
        CartID = cartID;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodCate() {
        return FoodCate;
    }

    public void setFoodCate(String foodCate) {
        FoodCate = foodCate;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public Float getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        TotalPrice = totalPrice;
    }
}
