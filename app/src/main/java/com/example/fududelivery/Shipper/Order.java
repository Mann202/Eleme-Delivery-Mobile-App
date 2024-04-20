package com.example.fududelivery.Shipper;

import java.util.Date;

public class Order {
    String cusname;
    String cusaddress;
    Date orderdate;
    int quantity;
    float total;

    public Order(String cusname, String cusaddress, Date orderdate, int quantity, float total) {
        this.cusname = cusname;
        this.cusaddress = cusaddress;
        this.orderdate = orderdate;
        this.quantity = quantity;
        this.total = total;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getCusaddress() {
        return cusaddress;
    }

    public void setCusaddress(String cusaddress) {
        this.cusaddress = cusaddress;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
