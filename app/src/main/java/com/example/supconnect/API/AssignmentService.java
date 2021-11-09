package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.AssignmentResponse;
import com.example.supconnect.RetrofitResponse.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AssignmentService {

    @GET("api/student/assignment/list/{student_id}")
    Call<AssignmentResponse> getAssignmentList(@Path("student_id") String student_id);

    @GET("api/student/assignment/{id}")
    Call<AssignmentResponse> getAssignment(@Path("id") String id);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/assignment")
    Call<StatusResponse> createAssignment(@Field("subject_class") String subject_class, @Field("title") String title, @Field("description") String description, @Field("deadline") String deadline);
}
