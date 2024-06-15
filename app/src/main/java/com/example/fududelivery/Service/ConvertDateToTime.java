package com.example.fududelivery.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConvertDateToTime {
    public static String ConvertTimeToDate(String timestamp) {
        long time = Long.parseLong(timestamp);
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());
        return sdf.format(date);
    }
}