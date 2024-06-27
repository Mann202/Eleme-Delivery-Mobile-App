package com.example.fududelivery.Shipper.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Order {
    String OrderID;
    String name;
    String address;
    String Date;
    String ResAddress;
    String TotalQuantity;
    String Phone;
    float OrderTotal;
    float ShippingFee;
    float ServiceFee;
    float SubTotal;
    String ResID;
    String ShippingStatus;

    public Order() {
    }

    public Order(String orderID, String name, String address, String date, String totalQuantity, String phone,
                 float orderTotal, float shippingFee, float serviceFee, float subTotal, String resID, String shippingStatus, String resAddress) {
        OrderID = orderID;
        this.name = name;
        this.address = address;
        Date = date;
        TotalQuantity = totalQuantity;
        Phone = phone;
        OrderTotal = orderTotal;
        ShippingFee = shippingFee;
        ServiceFee = serviceFee;
        SubTotal = subTotal;
        ResID = resID;
        ShippingStatus = shippingStatus;
        ResAddress = resAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderID='" + OrderID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Date='" + Date + '\'' +
                ", TotalQuantity='" + TotalQuantity + '\'' +
                ", Phone='" + Phone + '\'' +
                ", OrderTotal=" + OrderTotal +
                ", ShippingFee=" + ShippingFee +
                ", ServiceFee=" + ServiceFee +
                ", SubTotal=" + SubTotal +
                ", ResID=" + ResID +
                ", ShippingStatus=" + ShippingStatus +
                '}';
    }

    public String getOrderID() {
        return OrderID;
    }
    public String getResAddress() {
        return ResAddress;
    }
    public void setResAddress(String resAddress) {
        ResAddress = resAddress;
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

    public float getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public float getShippingFee() {
        return ShippingFee;
    }

    public void setShippingFee(float shippingFee) {
        ShippingFee = shippingFee;
    }

    public float getServiceFee() {
        return ServiceFee;
    }

    public void setServiceFee(float serviceFee) {
        ServiceFee = serviceFee;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public float getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(float subTotal) {
        SubTotal = subTotal;
    }

    public String getResID() {
        return ResID;
    }

    public void setResID(String resID) {
        ResID = resID;
    }

    public String getShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        ShippingStatus = shippingStatus;
    }
}
