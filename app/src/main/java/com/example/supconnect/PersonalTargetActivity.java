package com.example.supconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.TargetListResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.PersonalTargetListAdapter;
import com.example.supconnect.model.PersonalTargetItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonalTargetActivity extends AppCompatActivity {

    private RecyclerView personalTargetList;
    private List<PersonalTargetItem> personalTargetItems = new ArrayList<>();
    private PersonalTargetListAdapter personalTargetListAdapter;
    private FloatingActionButton addTarget;
    private SharedPreferences userSharedPreferences;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_target);
        userSharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = Helper.getStudentID(userSharedPreferences);
        initViews();

        personalTargetListAdapter = new PersonalTargetListAdapter(this);
        personalTargetListAdapter.setPersonalTargetItems(personalTargetItems);

        personalTargetList.setLayoutManager(new GridLayoutManager(this, 2));
        personalTargetList.setAdapter(personalTargetListAdapter);

        addTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTarget();
            }
        });
        getListTarget();
    }

    private void openAddTarget() {
        AddTargetModal addTargetModal = new AddTargetModal();
        addTargetModal.show(getSupportFragmentManager(), "Add Target");
    }

    public void getListTarget() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        retrofit.create(StudentService.class).getListTarget(user_id)
                .enqueue(new Callback<TargetListResponse>() {
                    @Override
                    public void onResponse(Call<TargetListResponse> call, Response<TargetListResponse> response) {
                        Log.d("TARGET_LIST", response.message());
                        if(response.isSuccessful()) {
                            Log.d("TARGET_LIST", response.body().getTargets().toString());
                            personalTargetItems.addAll(response.body().getTargets());
                            personalTargetListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<TargetListResponse> call, Throwable t) {
                        Log.d("TARGET_LIST_ERR", t.getMessage());

                    }
                });
    }

    private void initViews() {
        personalTargetList = findViewById(R.id.personalTargetList);
        addTarget = findViewById(R.id.addTargetBtn);
    }
}