package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.LeaveNoticeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LeaveNoticeService{

    @GET("api/student/leavenotice/list/{student_id}")
    Call<LeaveNoticeResponse> getLeaveNoticeList(@Path("student_id") String student_id);

    @GET("api/student/leavenotice/{id}")
    Call<LeaveNoticeResponse> getLeaveNotice(@Path("id") String id);
}
