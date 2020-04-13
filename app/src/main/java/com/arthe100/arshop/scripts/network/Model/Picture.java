package com.arthe100.arshop.scripts.network.Model;

import com.google.gson.annotations.SerializedName;

public class Picture
{
    @SerializedName("Url")
    private String url;

    public Picture(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}