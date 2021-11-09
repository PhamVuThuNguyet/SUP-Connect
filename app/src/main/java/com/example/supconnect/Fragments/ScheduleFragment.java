package com.example.supconnect.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.TimeTableResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.DayScheduleAdapter;
import com.example.supconnect.model.Lesson;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    private View view;
    private TabLayout daysOfWeeks;
    private RecyclerView scheduleList;
    private List<Lesson> lessions = new ArrayList<>();
    private TabLayout tabLayoutDay;
    private DayScheduleAdapter adapter;
    private SharedPreferences userSharedPreferences;
    private String user_id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.schedule_fragment, container, false);

        userSharedPreferences = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = Helper.getStudentID(userSharedPreferences);

        initViews(view);

        return view;
    }
    private void getLessons(int day_of_week) {
        lessions.clear();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(StudentService.class)
                .getTimeTable(user_id)
                .enqueue(new Callback<TimeTableResponse>() {
                    @Override
                    public void onResponse(Call<TimeTableResponse> call, Response<TimeTableResponse> response) {
                        assert response.body() != null;
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            for(int i = 0;i < response.body().getTimetables().size() ; i++) {
                                if(response.body().getTimetables().get(i).getDayOfWeek() == day_of_week) {
                                    lessions.add(response.body().getTimetables().get(i));
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

    private void getLecturerLessons(int day_of_week) {
        lessions.clear();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(LecturerService.class).getTimeTable(userSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<TimeTableResponse>() {
                    @Override
                    public void onResponse(Call<TimeTableResponse> call, Response<TimeTableResponse> response) {
                        assert response.body() != null;
                        if(response.isSuccessful() && response.body().getSuccess())  {
                            for(int i = 0; i < response.body().getTimetables().size(); i++) {
                                if(response.body().getTimetables().get(i).getDayOfWeek() == day_of_week) {
                                    lessions.add(response.body().getTimetables().get(i));
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

    private void initViews(View view) {
        userSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        scheduleList = view.findViewById(R.id.schedule_list);
        tabLayoutDay = view.findViewById(R.id.daysOfWeek_tabLayout);
        tabLayoutDay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(userSharedPreferences.getString("role", "").equals("1")) {
                    getLessons(tab.getPosition() + 2);
                } else if(userSharedPreferences.getString("role", "").equals("2")) {
                    getLecturerLessons(tab.getPosition() + 2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter = new DayScheduleAdapter(getContext());
        adapter.setLessons(lessions);
        scheduleList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        scheduleList.setAdapter(adapter);
        if(userSharedPreferences.getString("role", "").equals("1")) {
            getLessons(2);
        } else if(userSharedPreferences.getString("role", "").equals("2")) {
            getLecturerLessons(2);
        }
    }
}