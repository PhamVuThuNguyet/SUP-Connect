package com.example.supconnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.GradeOfStudentResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.GradeTrackingAdapter;
import com.example.supconnect.model.SubjectResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GradeTrackingActivity extends AppCompatActivity {
    RecyclerView rcview_subjectPoint;
    GradeTrackingAdapter adapter;
    ArrayList<SubjectResult> resultList = new ArrayList<>();
    ImageButton back,calendar_back,calendar_next;
    TextView txtYear,txtSemester;
    String school_year;
    private int semester = 2, start_year = 2020;
    private SharedPreferences sharedPreferences;
    private String user_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_tracking);

        sharedPreferences = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = Helper.getStudentID(sharedPreferences);

        calendar_back = findViewById(R.id.calendar_back);
        calendar_next = findViewById(R.id.calendar_next);
        back = findViewById(R.id.backBtn);
        txtYear = findViewById(R.id.txt_yearClass);
        txtSemester = findViewById(R.id.txt_semester);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calendar_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy lịch hiện tại đang view cộng thêm 1 kỳ, kiểm tra xem lịch cộng thêm có tồn tại k
                start_year = (semester == 2) ? (start_year + 1) : start_year;
                school_year = start_year + "-" + (start_year + 1);
                semester = (semester == 2) ? 1:2;
                txtYear.setText("Năm học " + school_year);
                txtSemester.setText("Học kỳ " + semester);
                getGrade(school_year, String.valueOf(semester));

            }
        });
        calendar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy lịch hiến tại đang view trừ đi 1 kỳ, kiểm tra xem lịch trừ đi có tồn tại k
                start_year = (semester == 2) ? start_year : (start_year-1);
                school_year = start_year + "-" + (start_year + 1);
                semester = (semester == 2) ? 1:2;
                txtYear.setText("Năm học " + school_year);
                txtSemester.setText("Học kỳ " + semester);
                getGrade(school_year, String.valueOf(semester));
            }
        });
        rcview_subjectPoint = findViewById(R.id.rc_viewSubject);
        rcview_subjectPoint.setHasFixedSize(true);
        rcview_subjectPoint.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new GradeTrackingAdapter(this, resultList);
        rcview_subjectPoint.setAdapter(adapter);
        getGrade("","");
    }

    private void getGrade(String school_year, String semester) {
        Log.d("school_year", school_year);
        Log.d("semester", semester);
        resultList.clear();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Retrofit retrofit = new RetrofitClient().getRetrofit(GradeTrackingActivity.this);
        retrofit.create(StudentService.class).getGradeOfStudent(user_id, school_year, semester)
                .enqueue(new Callback<GradeOfStudentResponse>() {
                    @Override
                    public void onResponse(Call<GradeOfStudentResponse> call, Response<GradeOfStudentResponse> response) {
                        if(response.isSuccessful() && (response.body().getSubjectResults() != null)) {
                            resultList.addAll(response.body().getSubjectResults());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<GradeOfStudentResponse> call, Throwable t) {

                    }
                });
    }
}
