package com.example.usermanager.model.apiUser.models;
public class Support {
    private String url;

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
