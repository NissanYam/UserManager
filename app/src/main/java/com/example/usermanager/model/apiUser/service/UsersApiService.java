package com.example.usermanager.model.apiUser.service;

import com.example.usermanager.model.apiUser.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersApiService {
    @GET("users")
    Call<UserResponse> getUsers(@Query("page") int page);
}
