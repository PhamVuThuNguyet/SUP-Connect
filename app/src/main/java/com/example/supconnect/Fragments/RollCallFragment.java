package com.example.supconnect.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.HistoryRollcallLecurerResponse;
import com.example.supconnect.RetrofitResponse.RollCallResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.RollCallViewLecturerAdapter;
import com.example.supconnect.model.HistoryRollcallStudent;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.HEAD;

public class RollCallFragment extends Fragment {
    private View view;
    Retrofit retrofit;
    private TextView txt_yearClass, txt_Semester, txtDay;
    private EditText edtSearch;
    RecyclerView rc_viewStudent;
    RollCallViewLecturerAdapter adapterStudent;
    ArrayList<HistoryRollcallStudent> listStudent = new ArrayList<>();
    private static final int FRAGMENT_ROLLUP = 1;
    private static final int FRAGMENT_ENTRYPOINT = 2;
    private  int currentFragment = FRAGMENT_ROLLUP;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String subject_class;
    private String date;
    private Date dateNow;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RollCallFragment(){
    }

    public static RollCallFragment newInstance(String param1, String param2) {
        RollCallFragment fragment = new RollCallFragment();
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

        view = inflater.inflate(R.layout.roll_call_fragment, container, false);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dateNow = new Date();
        date = formatter.format(dateNow);
        Bundle bundle = this.getArguments();
//        subject_class = bundle.getString("classID");
        subject_class = getArguments().getString("classID");
        initViews(view);
        getStudentList();
        adapterStudent = new RollCallViewLecturerAdapter(listStudent , subject_class, getContext());
        rc_viewStudent.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rc_viewStudent.setAdapter(adapterStudent);

        return view;
    }

    private void getStudentList() {
        /*
        The next line get the current lecturer's id, currently no use since the api use fixed id.
         */
        //SharedPreferences useSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        listStudent = new ArrayList<>();
        retrofit = new RetrofitClient().getRetrofit(this.requireContext());
        retrofit.create(LecturerService.class).getHistoryRollCall(this.subject_class, date)
                .enqueue(new Callback<HistoryRollcallLecurerResponse>() {
                    @Override
                    public void onResponse(Call<HistoryRollcallLecurerResponse> call, Response<HistoryRollcallLecurerResponse> response) {
                        if (response.isSuccessful() && response.body() != null ) {
                            listStudent.addAll(response.body().getStudents());
                            adapterStudent.notifyDataSetChanged();
                            Log.d("listStudent", String.valueOf(listStudent.size()));
                            String yearClass = "Ngày " + response.body().getDate();
//                            txt_yearClass.setText(yearClass);
                            String semester = "Học kỳ " + response.body().getSemeter() + " - " + response.body().getYear();
//                            txt_Semester.setText(semester);
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryRollcallLecurerResponse> call, Throwable t) {
                        Log.d("history roll call", t.getMessage());
                    }
                });

    }

    private void changeAttendStatus(int record_id) {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(LecturerService.class).changeAttendStatus(subject_class, edtSearch.getText().toString())
                .enqueue(new Callback<RollCallResponse>() {
                    @Override
                    public void onResponse(Call<RollCallResponse> call, Response<RollCallResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getSuccess()) {
                                Log.d("Change attend status", response.message());
                                for (int i = 0; i < listStudent.size(); i++) {
                                    if (listStudent.get(i).getRecordDetailId().equals(response.body().getRecord().getRecordDetailId())) {
                                        Log.d("Change attend status", "Change");
                                        adapterStudent.notifyItemChanged(i);
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<RollCallResponse> call, Throwable t) {
                        Log.d("Change attend status", t.getMessage());
                    }
                });
    }

    private void initViews(View view) {
        rc_viewStudent = view.findViewById(R.id.rc_viewRollup);
        txt_yearClass = view.findViewById(R.id.txt_yearClass);
        txt_Semester = view.findViewById(R.id.txt_semester);
        txtDay = view.findViewById(R.id.txt_date);

        txtDay.setText("Ngày " + Helper.convertDate(date));

        rc_viewStudent.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        edtSearch = view.findViewById(R.id.search_bar);
        edtSearch.setInputType(InputType.TYPE_NULL);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String card_id = edtSearch.getText().toString();
                if(card_id.length() == 10) {
                    for(int i = 0; i < listStudent.size(); i++) {
                        if (listStudent.get(i).getCardUID().equals(card_id)) {
                            changeAttendStatus(listStudent.get(i).getRecordDetailId());
                            edtSearch.setText("");
                        }
                    }
                }
            }
        });
    }
}
