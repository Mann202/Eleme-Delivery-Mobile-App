package com.example.fududelivery.Shipper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Order {
    String OrderID;
    String name;
    String address;
    String Date;
    String TotalQuantity;
    String OrderTotal;

    public Order() {
    }

    public Order(String id, String cusname, String cusaddress, String orderdate, String quantity, String total) {
        this.OrderID = id;
        this.name = cusname;
        this.address = cusaddress;
        this.Date = orderdate;
        this.TotalQuantity = quantity;
        this.OrderTotal = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + OrderID + '\'' +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", date='" + Date + '\'' +
                ", quantity=" + TotalQuantity + '\'' +
                ", total=" + OrderTotal + '\'' +
                '}';
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public Date getDate() {
//        Date result = null;
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            result = format.parse(Date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public void setDate(Date date) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////        SimpleDateFormat format = new SimpleDateFormat("d MMM, HH:mm");
//        this.Date = format.format(date);
//    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }
}
