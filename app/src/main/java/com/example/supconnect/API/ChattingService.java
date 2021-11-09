package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.ConversationResponse;
import com.example.supconnect.RetrofitResponse.ListChatRoomResponse;
import com.example.supconnect.model.Conversation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChattingService {

    @GET("api/chat")
    Call<ListChatRoomResponse> getListChatRoom( @Query("user_id") String user_id);

    @GET("api/chat/{id}")
    Call<ConversationResponse> getConversation(@Path("id") String id, @Query("user_1") String user_1, @Query("user_2") String user_2);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/chat-details")
    Call<Conversation> sendMessage(@Field("id_chat") String id_chat, @Field("sender_id") String sender_id, @Field("message") String message, @Field("type") String type);
}
