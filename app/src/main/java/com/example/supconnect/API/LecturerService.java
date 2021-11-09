package com.example.supconnect.API;

import com.example.supconnect.RetrofitResponse.GradeInputResponse;
import com.example.supconnect.RetrofitResponse.HistoryRollcallLecurerResponse;
import com.example.supconnect.RetrofitResponse.LecturerResponse;
import com.example.supconnect.RetrofitResponse.RollCallResponse;
import com.example.supconnect.RetrofitResponse.StudentOfClassResponse;
import com.example.supconnect.RetrofitResponse.SubjectGradeColResponse;
import com.example.supconnect.RetrofitResponse.SubjectListofLecturerResponse;
import com.example.supconnect.RetrofitResponse.TimeTableResponse;
import com.example.supconnect.RetrofitResponse.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LecturerService {
    @GET("api/lecturer")
    Call<LecturerResponse> getLecturers(@Query("student_id") String user_id);

    @Headers("Accept: application/json")
    @PUT("api/lecturer/{lecturer_id}")
    Call<UserResponse> updateInfo(@Path("lecturer_id") String lecturer_id, @Query("first_name_lecturer") String first_name, @Query("last_name_lecturer") String last_name, @Query("date_of_birth") String date_of_birth, @Query("email") String email, @Query("phone_number_lecturer") String phone_number);

    @GET("api/lecturer/subject-class")
    Call<TimeTableResponse> getTimeTable(@Query("lecturer_id") String lecturer_id);

    @GET("api/lecturer/subject-class/class_list")
    Call<SubjectListofLecturerResponse> getSubjectList(@Query("lecturer_id") String user_id, @Query("school_year") String school_year,@Query("semester") String semester);

    @GET("api/lecturer/subject-class/{subject_class}")
    Call<HistoryRollcallLecurerResponse> getHistoryRollCall(@Path("subject_class") String subjectClass, @Query("date") String date);

    @PUT("api/lecturer/subject-class/{record_id}")
    Call<RollCallResponse> changeAttendStatus(@Path("record_id") String lecturer_id, @Query("cardID") String cardID);

    @GET("api/grade/{subject}/getcol")
    Call<SubjectGradeColResponse> getGradeCols(@Path("subject") String subject);

    @FormUrlEncoded
    @POST("api/grade")
    Call<GradeInputResponse> inputGrade(@Field("student_id") String student_id,
                                        @Field("grade_book_id") int gradeBookId,
                                        @Field("grade") float grade);

    @GET("api/lecturer/subject-class/{subject_class_id}/students")
    Call<StudentOfClassResponse> getStudents(@Path("subject_class_id") String subject_class_id);
}
