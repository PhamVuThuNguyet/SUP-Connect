package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.RollCallRecordViewStudentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RollCallRecordViewStudentService {
    @GET("api/record")
    Call<RollCallRecordViewStudentResponse> getListRecord(@Query("user_id") String user_id, @Query("subject_class") String subject_class);
}
