package com.example.supconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.*;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.TargetDetailsResponse;
import com.example.supconnect.adapter.CurrentMarkColumnListAdapter;
import com.example.supconnect.adapter.RecommendMarkColumnListAdapter;
import com.example.supconnect.model.MarkColumn;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TargetDetailActivity extends AppCompatActivity {

    private TextView targetDetailSubject,
            targetDetailProgress,
            targetDetailCurrentGrade,
            targetDetailTargetGrade,
            targetDetailProgressBarLabel;

    private ProgressBar targetDetailProgressBar;

    private RecyclerView targetDetailCurrentMarkColumnList,
            targetDetailRecommendMarkColumnList;

    private List<MarkColumn> currentMarkColumns = new ArrayList<>(), recommendMarkColumns = new ArrayList<>();
    private CurrentMarkColumnListAdapter currentMarkColumnListAdapter;
    private RecommendMarkColumnListAdapter recommendMarkColumnListAdapter;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_detail);
        initViews();
        setUpCurrentMarkColumnList();
        setUpRecommendMarkColumnList();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTargetMark();
    }

    private void setUpRecommendMarkColumnList() {
        recommendMarkColumnListAdapter = new RecommendMarkColumnListAdapter(this);
        recommendMarkColumnListAdapter.setMarkColumns(recommendMarkColumns);
        targetDetailRecommendMarkColumnList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        targetDetailRecommendMarkColumnList.setAdapter(recommendMarkColumnListAdapter);
    }

    private void setUpCurrentMarkColumnList() {
        currentMarkColumnListAdapter = new CurrentMarkColumnListAdapter(this);
        currentMarkColumnListAdapter.setMarkColumns(currentMarkColumns);
        targetDetailCurrentMarkColumnList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        targetDetailCurrentMarkColumnList.setAdapter(currentMarkColumnListAdapter);
    }

    private void getTargetMark() {
        SharedPreferences userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        retrofit.create(StudentService.class)
                .getTargetDetails(userSharedPreferences.getString("user_id", ""),getIntent().getExtras().getString("subject_class_id"))
                .enqueue(new Callback<TargetDetailsResponse>() {
                    @Override
                    public void onResponse(Call<TargetDetailsResponse> call, Response<TargetDetailsResponse> response) {
                        Log.d("TARGET_DETAILS_RES: ", response.message());
                        if(response.isSuccessful() && (response.body().getGrades() != null)) {
                            targetDetailSubject.setText(response.body().getSubject_name());
                            targetDetailCurrentGrade.setText(String.valueOf(response.body().getCurrentGrade()));
                            targetDetailTargetGrade.setText(String.valueOf(response.body().getGrade_target()));
                            int targetProgress = (int) Math.round((float) (response.body().getCurrentGrade() / response.body().getGrade_target()) * 100);
                            targetDetailProgress.setText(targetProgress + "%");
                            targetDetailProgressBarLabel.setText(targetProgress + "%");
                            targetDetailProgressBar.setProgress(targetProgress);
                            for(int i = 0;i < response.body().getGrades().size(); i++) {
                                if(response.body().getGrades().get(i).getStatus() == 0) {
                                    recommendMarkColumns.add(response.body().getGrades().get(i));
                                } else {
                                    currentMarkColumns.add(response.body().getGrades().get(i));
                                }
                                recommendMarkColumnListAdapter.notifyDataSetChanged();
                                currentMarkColumnListAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TargetDetailsResponse> call, Throwable t) {
                        Log.d("fails", t.getMessage());
                    }
                });
    }

    private void initViews() {
        targetDetailSubject = findViewById(R.id.targetDetailSubject);
        targetDetailCurrentGrade = findViewById(R.id.targetDetailCurrentGrade);
        targetDetailTargetGrade = findViewById(R.id.targetDetailTargetGrade);
        targetDetailProgress = findViewById(R.id.targetDetailProgress);
        targetDetailProgressBarLabel = findViewById(R.id.targetDetailProgressBarLabel);
        targetDetailProgressBar = findViewById(R.id.targetDetailProgressBar);
        targetDetailCurrentMarkColumnList = findViewById(R.id.targetDetailCurrentMarkColumnList);
        targetDetailRecommendMarkColumnList = findViewById(R.id.targetDetailRecommendMarkColumnList);
        back = findViewById(R.id.backBtn);
    }
}