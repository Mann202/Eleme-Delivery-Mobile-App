package com.example.fududelivery.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    // Tên của SharedPreferences
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ROLE = "UserRole";
    private static final String USER_INFOR = "UserInformation";
    private static final String USER_NAME = "UserName";
    private static final String USER_PHONE = "UserPhone";
    private static final String USER_GMAIL = "UserGmail";
    private static final String USER_GMAIL_REMEMBER = "UserGmailRemember";
    private static final String USER_PASSWORD_REMEMBER = "UserPasswordRemember";
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
    public void loginUserGmail(String gmail) {
        String gmailValue = (gmail != null) ? gmail : "";
        editor.putString(USER_GMAIL, gmailValue);
        editor.commit();
    }

    public void loginUserPhone(String phone) {
        String phoneValue = (phone != null) ? phone : "";
        editor.putString(USER_PHONE, phoneValue);
        editor.commit();
    }

    public void setUserGmailRemember(String gmail) {
        editor.putString(USER_GMAIL_REMEMBER, gmail);
        editor.commit();
    }

    public void setUserPasswordRemember(String pasword) {
        editor.putString(USER_PASSWORD_REMEMBER, pasword);
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

    public void loginUserPhone(String phone) {
        String phoneValue = (phone != null) ? phone : "";
        editor.putString(USER_PHONE, phoneValue);
        editor.commit();
    }


    public void loginUserName(String name) {
        String nameValue = (name != null) ? name : "";
        editor.putString(USER_NAME, nameValue);
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

    // Method logout
    public void logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.putString(USER_ROLE, "");
        editor.putString(USER_INFOR, "");
        editor.putInt(USER_PHONE, 0);
        editor.putString(USER_NAME, "");
        editor.putString(USER_GMAIL, "");

        editor.commit();
    }
}
