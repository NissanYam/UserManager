package com.example.usermanager.model.apiUser.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("total")
    private int total;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("data")
    private List<User> users;

    @SerializedName("support")
    private Support support;

    public int getPage() {
        return page;
    }

    public UserListResponse setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPerPage() {
        return perPage;
    }

    public UserListResponse setPerPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public Support getSupport() {
        return support;
    }

    public UserListResponse setSupport(Support support) {
        this.support = support;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public UserListResponse setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public UserListResponse setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public List<User> getUsers() {
        return users;
    }

    public UserListResponse setUsers(List<User> users) {
        this.users = users;
        return this;
    }
}
