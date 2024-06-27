package com.example.fududelivery.Customer.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String email;
    private String avatarURL;
    private String userName;
    private String phoneNumber;

    public User() {
    }

    public User(String userId, String email, String avatarURL, String userName, String birthDate, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.avatarURL = avatarURL;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
