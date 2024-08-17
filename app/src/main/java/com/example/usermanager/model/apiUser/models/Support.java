package com.example.usermanager.model.apiUser.models;

import com.google.gson.annotations.SerializedName;

public class Support {
    @SerializedName("url")
    private String url;

    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public Support setText(String text) {
        this.text = text;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Support setUrl(String url) {
        this.url = url;
        return this;
    }
}
