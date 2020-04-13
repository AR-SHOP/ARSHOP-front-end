package com.arthe100.arshop.scripts.network.Model;

import com.google.gson.annotations.SerializedName;

public class UserInfo
{
    @SerializedName("Username")
    private String username;

    @SerializedName("Password")
    private String password;

    @SerializedName("E-mail")
    private String email;

    public UserInfo(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}