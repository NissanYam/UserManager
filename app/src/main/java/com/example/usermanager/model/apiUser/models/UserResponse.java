package com.example.usermanager.model.apiUser.models;

import java.util.List;

public class UserResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<User> data;
    private Support support;

    public List<User> getData() {
        return data;
    }

    public UserResponse setData(List<User> data) {
        this.data = data;
        return this;
    }

    public int getPage() {
        return page;
    }

    public UserResponse setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPer_page() {
        return per_page;
    }

    public UserResponse setPer_page(int per_page) {
        this.per_page = per_page;
        return this;
    }

    public Support getSupport() {
        return support;
    }

    public UserResponse setSupport(Support support) {
        this.support = support;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public UserResponse setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public UserResponse setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
        return this;
    }
}
