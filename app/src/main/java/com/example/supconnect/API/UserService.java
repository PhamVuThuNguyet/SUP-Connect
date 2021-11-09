package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @Headers("Accept: application/json")

//    @FormUrlEncoded
    @GET("api/login")
    Call<UserResponse> login(@Query("username") String username, @Query("password") String password);

    @GET("api/logout")
    Call<StatusResponse> logout();


}
