package com.example.fududelivery.Customer.MyCart;

public class CartDetail {
    String CartID;
    String FoodName;
    String UserID;
    String Quantity;
    Float TotalPrice;
    Float Price;
    String imageID;
    boolean Selected;

    public CartDetail() {
    }

    public CartDetail(String cartID, String foodName, String userID, String quantity, Float totalPrice, Float price, String imageID, boolean selected) {
        CartID = cartID;
        FoodName = foodName;
        UserID = userID;
        Quantity = quantity;
        TotalPrice = totalPrice;
        Price = price;
        this.imageID = imageID;
        Selected = selected;
    }

    @Override
    public String toString() {
        return "CartDetail{" + "CartID='" + CartID + '\'' + ", FoodName='" + FoodName + '\'' + ", UserID='" + UserID + '\'' + ", Quantity='" + Quantity + '\'' + ", TotalPrice=" + TotalPrice + ", Price=" + Price + ", imageID='" + imageID + '\'' + ", Selected=" + Selected + '}';
    }

    public String getCartID() {
        return CartID;
    }

    public void setCartID(String cartID) {
        CartID = cartID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
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

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
