package com.example.usermanager.model.apiUser.service;

import com.example.usermanager.model.apiUser.models.UserListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApiService {
    @GET("users")
    Call<UserListResponse> getUsers(@Query("page") int page);
}
