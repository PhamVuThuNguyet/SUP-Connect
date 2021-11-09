package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.UserResponse;
import com.example.supconnect.RetrofitResponse.WalletResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WalletService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/payment")
    Call<StatusResponse> storePayment(@Field("acc_id") String acc_id,
                                      @Field("type") Integer type,
                                      @Field("amount") String amount,
                                      @Field("category") Integer category,
                                      @Field("description") String description);

    @GET("api/payment")
    Call<WalletResponse> getTransaction(@Query("student_id") String user_id);



}
