package com.example.supconnect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.supconnect.API.ChattingService;
import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.RetrofitResponse.LecturerResponse;
import com.example.supconnect.RetrofitResponse.ListChatRoomResponse;
import com.example.supconnect.adapter.AllLecturerAdapter;
import com.example.supconnect.adapter.LecturerPagerAdapter;
import com.example.supconnect.model.Lecturer;
import com.example.supconnect.model.LecturerOfClass;
import com.google.android.material.tabs.TabLayout;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LectureListActivity extends AppCompatActivity {
    LecturerPagerAdapter adapter;
    TabLayout incaditor;
    ViewPager viewPager;
    List<LecturerOfClass> lectureList;
    List<Lecturer> allLectureList;
    RecyclerView rc_lectureAll;
    AllLecturerAdapter allLecturerAdapter;
    ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lecturer);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        incaditor = findViewById(R.id.incaditor);

        lectureList = new ArrayList<>();
        setAdapterLecture(lectureList);

        allLectureList = new ArrayList<>();
        setAdapterLectureAll(allLectureList);

        getLecturerOfClassList();
        getLecturerList();
    }

    public void setAdapterLecture(List<LecturerOfClass> lectureList){
        viewPager = findViewById(R.id.viewPager_lecture);
        adapter = new LecturerPagerAdapter(this,lectureList);
        viewPager.setAdapter(adapter);
        incaditor.setupWithViewPager(viewPager);

    }

    public void setAdapterLectureAll(List<Lecturer> lectureAll) {
        rc_lectureAll = findViewById(R.id.rcview_lecture);
        rc_lectureAll.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rc_lectureAll.setHasFixedSize(true);
        allLecturerAdapter = new AllLecturerAdapter(this, lectureAll);
        rc_lectureAll.setAdapter(allLecturerAdapter);
    }


    private void getLecturerList() {
        SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        retrofit.create(LecturerService.class).getLecturers(useSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<LecturerResponse>() {
                    @Override
                    public void onResponse(Call<LecturerResponse> call, Response<LecturerResponse> response) {
                        if (response.isSuccessful() && response.body().getSuccess()) {
                            allLectureList.addAll(response.body().getAll_lecturers());
                            allLecturerAdapter.notifyDataSetChanged();
                            lectureList.addAll(response.body().getClass_lecturers());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<LecturerResponse> call, Throwable t) {
                        Log.d("fail", String.valueOf(t));
                    }
                });
    }

    private void getLecturerOfClassList() {
    }
}
