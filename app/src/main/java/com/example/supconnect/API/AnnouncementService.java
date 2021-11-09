package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.AnnouncementTypeResponse;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.model.Conversation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AnnouncementService {

    @GET("api/announcement/type")
    Call<AnnouncementTypeResponse> getAnnouncementType();

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/announcement")
    Call<StatusResponse> createAnnouncement(@Field("type") String type, @Field("title") String title, @Field("description") String description, @Field("create_date") String create_date);
}
