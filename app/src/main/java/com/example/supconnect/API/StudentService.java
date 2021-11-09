package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.AnnouncementResponse;

import com.example.supconnect.RetrofitResponse.GradeOfStudentResponse;

import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.UserResponse;
import com.example.supconnect.RetrofitResponse.SubjectListofStudentResponse;
import com.example.supconnect.RetrofitResponse.TargetDetailsResponse;
import com.example.supconnect.RetrofitResponse.TargetListResponse;
import com.example.supconnect.RetrofitResponse.TimeTableResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentService {

    @GET("api/student/timetable/user/{user_id}")
    Call<TimeTableResponse> getTimeTable(@Path("user_id") String user_id);

    @GET("api/student/{user_id}")
    Call<UserResponse> getStudentInfo(@Path("user_id") String user_id);

    @Headers("Accept: application/json")
    @PUT("api/student/{account_id}")
    Call<UserResponse> updateInfo(@Path("account_id") String account_id, @Query("first_name") String first_name, @Query("last_name") String last_name, @Query("date_of_birth") String date_of_birth, @Query("email") String email, @Query("phone_number") String phone_number);

    @GET("api/student/announcement")
    Call<AnnouncementResponse> getAnnouncement();

    @GET("api/student/announcement/{id}")
    Call<AnnouncementResponse> getAnnouncement(@Path("id") String id);

    @GET("api/student/grade/{student_id}")
    Call<GradeOfStudentResponse> getGradeOfStudent(@Path("student_id") String student_id, @Query("school_year") String school_year,@Query("semester") String semester);

    @GET("api/student/subjects/{student_id}")
    Call<SubjectListofStudentResponse> getListSubject(@Path("student_id") String student_id);

    @GET("api/target/0")
    Call<TargetListResponse> getListTarget(@Query("student_id") String student_id);

    @GET("api/target")
    Call<TargetDetailsResponse> getTargetDetails(@Query("student_id") String student_id, @Query("subject_class_id") String subject_class_id);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/target")
    Call<StatusResponse> createTarget(@Field("student_id") String student_id, @Field("subject_class_id") String subject_class_id, @Field("grade_target") String grade_target);

    @GET("api/student/card/{id}")
    Call<UserResponse> getOtherStudentInfo(@Path("id") String id);
}
