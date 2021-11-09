package com.example.supconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.RollCallRecordViewStudentService;
import com.example.supconnect.RetrofitResponse.RollCallRecordViewStudentResponse;
import com.example.supconnect.adapter.RollCallViewStudentAdapter;
import com.example.supconnect.model.RollCallViewStudent;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryRollCallViewStudentActivity extends AppCompatActivity {
    private RecyclerView rcview_rollup;
    private RollCallViewStudentAdapter rollCallViewStudentAdapter;
    private ArrayList<RollCallViewStudent> rollCallViewStudentList;
    private TextView subject, total, total_checked, total_absence;
    private ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_roll_call_view_student);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rcview_rollup = findViewById(R.id.rc_viewRollup);
        rcview_rollup.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rollCallViewStudentList = new ArrayList<>();
        getListRecord();
        rollCallViewStudentAdapter = new RollCallViewStudentAdapter(this);
        rollCallViewStudentAdapter.setRecords(rollCallViewStudentList);
        rcview_rollup.setAdapter(rollCallViewStudentAdapter);

        subject = findViewById(R.id.subject_check_roll_call);
        total = findViewById(R.id.total_checked);
        total_absence = findViewById(R.id.total_absence);
        total_checked = findViewById(R.id.number_checked);

    }

    private void getListRecord() {
        SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Retrofit retrofit = new RetrofitClient().getRetrofit(this);
        retrofit.create(RollCallRecordViewStudentService.class).getListRecord( useSharedPreferences.getString("user_id", ""), String.valueOf(getIntent().getExtras().getString("subject_class")))
                .enqueue(new Callback<RollCallRecordViewStudentResponse>() {
                    @Override
                    public void onResponse(Call<RollCallRecordViewStudentResponse> call, Response<RollCallRecordViewStudentResponse> response) {
                        if (response.isSuccessful() && response.body().getSuccess()) {
                            Log.d("number:", String.valueOf(response.body().getRecords().size()));
                            rollCallViewStudentList.addAll(response.body().getRecords());
                            rollCallViewStudentAdapter.notifyDataSetChanged();
                            subject.setText(response.body().getSubject_class_name());
                            total.setText("/"+String.valueOf(response.body().getTotal_count()));
                            total_absence.setText(String.valueOf(response.body().getAbsence_count()));
                            total_checked.setText(String.valueOf(response.body().getTotal_count() - response.body().getAbsence_count()));
                        }
                    }
                    public void onFailure(Call<RollCallRecordViewStudentResponse> call, Throwable t) {
                        Log.d("fail", String.valueOf(t));
                    }

                });
    }
}
