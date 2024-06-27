package com.example.fududelivery.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class RestaurantSessionManager {
    private static final String IS_ACTIVE = "isActive";
    private static final String PREF_NAME_RESTAURANT = "RestaurantSession";
    private static final String IS_FIRST_TIME_USED_SERVICE = "isFirstLaunched";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public RestaurantSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME_RESTAURANT, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setIsActive(Boolean isActive) {
        editor.putBoolean(IS_ACTIVE, isActive);
        editor.commit();
    }

    public boolean isActive() {
        return pref.getBoolean(IS_ACTIVE, false);
    }
}
