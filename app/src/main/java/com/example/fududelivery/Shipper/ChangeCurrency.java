package com.example.fududelivery.Shipper;

import java.text.NumberFormat;
import java.util.Locale;

public class ChangeCurrency {
    public static String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(0);

        String formattedPrice = formatter.format(price);

        formattedPrice += "VND";

        return formattedPrice;
    }
}
