package com.example.fududelivery.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    // Tên của SharedPreferences
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_STARTING_DATE = "UserStartingDate";
    private static final String USER_ADDRESS = "UserAddress";
    public static final String USER_ROLE = "UserRole";
    private static final String USER_INFOR = "UserInformation";
    private static final String USER_NAME = "UserName";
    private static final String USER_PHONE = "UserPhone";
    private static final String USER_GMAIL = "UserGmail";
    private static final String USER_VEHICLE = "UserVehicle";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Method to save user's state logging
    public void loginUserState() {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    // Method to save user's uid
    public void loginUserInformation(String Uid) {
        editor.putString(USER_INFOR, Uid);
        editor.commit();
    }

    //Method to save user's role
    public void loginUserRole(String roleID) {
        editor.putString(USER_ROLE, roleID);
        editor.commit();
    }

    public void loginUserPhone(String phone) {
        String phoneValue = (phone != null) ? phone : "";
        editor.putString(USER_PHONE, phoneValue);
        editor.commit();
    }

    public void setStartingDate(String startingDate) {
        String startingValue = (startingDate != null) ? startingDate : "";
        editor.putString(USER_STARTING_DATE, startingValue);
        editor.commit();
    }

    public void setAddress(String address) {
        String addressValue = (address != null) ? address : "";
        editor.putString(USER_ADDRESS, addressValue);
        editor.commit();
    }

    public void loginUserName(String name) {
        String nameValue = (name != null) ? name : "";
        editor.putString(USER_NAME, nameValue);
        editor.commit();
    }

    public void loginUserGmail(String gmail) {
        String gmailValue = (gmail != null) ? gmail : "";
        editor.putString(USER_GMAIL, gmailValue);
        editor.commit();
    }

    public void setVehicleInformation(String vehicleInformation) {
        String vehicleValue = (vehicleInformation != null) ? vehicleInformation : "";
        editor.putString(USER_VEHICLE, vehicleValue);
        editor.commit();
    }


    // Method check user's logging
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserInformation() {
        return pref.getString(USER_INFOR, "");
    }

    public String getUserRole() {
        return pref.getString(USER_ROLE, "");
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "");
    }

    public String getUserGmail() {
        return pref.getString(USER_GMAIL, "");
    }

    public String getUserPhone() {
        return pref.getString(USER_PHONE, "");
    }

    public String getUserAddress() {
        return pref.getString(USER_ADDRESS, "");
    }

    public String getUserStartingDate() {
        return pref.getString(USER_STARTING_DATE, "");
    }

    // Method logout
    public void logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.putString(USER_ROLE, "");
        editor.putString(USER_INFOR, "");
        editor.putInt(USER_PHONE, 0);
        editor.putString(USER_NAME, "");
        editor.putString(USER_GMAIL, "");
        editor.putString(USER_ADDRESS, "");
        editor.putString(USER_STARTING_DATE, "");
        editor.putString(USER_VEHICLE, "");

        editor.commit();
    }

    public String getUserVehicle() {
        return pref.getString(USER_VEHICLE, "");
    }
}
