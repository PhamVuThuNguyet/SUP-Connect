package com.example.supconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.TimeTableResponse;
import com.example.supconnect.adapter.DayScheduleAdapter;
import com.example.supconnect.model.Lesson;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleActivity extends AppCompatActivity {
    private RecyclerView scheduleList;
    private List<Lesson> lessons = new ArrayList<>();
    private TabLayout tabLayoutDay;
    private DayScheduleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initViews();
    }

    private void getLessons(int day_of_week) {
        lessons.clear();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Retrofit retrofit = new RetrofitClient().getRetrofit(ScheduleActivity.this);
        retrofit.create(StudentService.class)
                .getTimeTable(sharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<TimeTableResponse>() {
                    @Override
                    public void onResponse(Call<TimeTableResponse> call, Response<TimeTableResponse> response) {
                        Log.d("TimetableRes:", response.body().getTimetables().toString());
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            for(int i = 0;i < response.body().getTimetables().size() ; i++) {
                                if(response.body().getTimetables().get(i).getDayOfWeek() == day_of_week) {
                                    lessons.add(response.body().getTimetables().get(i));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponse> call, Throwable t) {
                        Log.d("TimeTableERR: ", t.getMessage());
                    }
                });
    }

    private void initViews() {
        scheduleList = findViewById(R.id.schedule_list);
        tabLayoutDay = findViewById(R.id.daysOfWeek_tabLayout);
        tabLayoutDay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getLessons(tab.getPosition() + 2);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        adapter = new DayScheduleAdapter(this);
        adapter.setLessons(lessons);
        scheduleList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        scheduleList.setAdapter(adapter);
        getLessons(2);
    }
}