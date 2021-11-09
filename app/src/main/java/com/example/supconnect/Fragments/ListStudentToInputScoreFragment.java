package com.example.supconnect.Fragments;

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
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.StudentOfClassResponse;
import com.example.supconnect.adapter.InputGradeAdater;
import com.example.supconnect.model.RollCallViewLecturer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListStudentToInputScoreFragment extends Fragment  {
    private View view;
    RecyclerView rc_viewStudent;
   InputGradeAdater adapterStudent;
    ArrayList<RollCallViewLecturer> listStudent;
    private static final int FRAGMENT_ROLLUP = 1;
    private static final int FRAGMENT_ENTRYPOINT = 2;
    private  int currentFragment = FRAGMENT_ROLLUP;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String classID;
    public  ListStudentToInputScoreFragment(){
    }
    public static ListStudentToInputScoreFragment newInstance(String param1, String param2) {
        ListStudentToInputScoreFragment fragment = new ListStudentToInputScoreFragment();
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
        classID = getArguments().getString("classID");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_student_to_input_score_fragment, container, false);
        rc_viewStudent = view.findViewById(R.id.rc_view_student_list);
        rc_viewStudent.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listStudent = new ArrayList<>();
        adapterStudent = new InputGradeAdater(listStudent,getContext());
        rc_viewStudent.setAdapter(adapterStudent);
        getStudents();
        return view;
    }

    private void getStudents() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(LecturerService.class).getStudents(getArguments().getString("classID"))
                .enqueue(new Callback<StudentOfClassResponse>() {
                    @Override
                    public void onResponse(Call<StudentOfClassResponse> call, Response<StudentOfClassResponse> response) {
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            listStudent.addAll(response.body().getStudents());
                            adapterStudent.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentOfClassResponse> call, Throwable t) {
                        Log.d("LIST_STUDENT: ", "ERROR: " + t.getMessage());
                    }
                });
    }


}
