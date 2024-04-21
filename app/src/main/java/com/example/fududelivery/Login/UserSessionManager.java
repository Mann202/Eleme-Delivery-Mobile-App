package com.example.fududelivery.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    // Tên của SharedPreferences
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ROLE = "UserRole";
    private static final String USER_INFOR = "UserInformation";
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

    // Method check user's logging
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserInformation() {
        return pref.getString(USER_INFOR, ".");
    }

    public String getUserRole() {
        return pref.getString(USER_ROLE, "1");
    }

    // Method logout
    public void logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.commit();
    }
}
