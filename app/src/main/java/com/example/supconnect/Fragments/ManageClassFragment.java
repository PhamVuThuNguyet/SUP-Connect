package com.example.supconnect.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.SubjectListofLecturerResponse;
import com.example.supconnect.adapter.ClassOfThisLecturerAdapter;
import com.example.supconnect.model.ClassOfThisTeacher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageClassFragment extends Fragment {
    private FloatingActionButton fab;
    private RecyclerView rcview_manageClass;

    private ClassOfThisLecturerAdapter classAdapter;

    private LinearLayout cardView, expanded;
    private CardView cardview1;
    private ArrayList<ClassOfThisTeacher> classList;
    private ImageView icon_dropdown;
    private View view;
    private ImageButton preBtn, nextBtn;
    private TextView txtYear, txtSemester;
    private String school_year;
    private int semester = 2, start_year = 2020;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageClassFragment() {
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
    public static ManageClassFragment newInstance(String param1, String param2) {
        ManageClassFragment fragment = new ManageClassFragment();
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
        view = inflater.inflate(R.layout.manage_class_fragment, container, false);
        rcview_manageClass = view.findViewById(R.id.rc_viewmangeClass);
        expanded = view.findViewById(R.id.expanded_view);
        fab = view.findViewById(R.id.menu_home);
        preBtn = view.findViewById(R.id.preBtn);
        nextBtn = view.findViewById(R.id.nextBtn);
        txtYear = view.findViewById(R.id.txt_schoolyear);
        txtSemester = view.findViewById(R.id.txt_semester);

        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy lịch hiến tại đang view trừ đi 1 kỳ, kiểm tra xem lịch trừ đi có tồn tại k
                start_year = (semester == 2) ? start_year : (start_year - 1);
                school_year = start_year + "-" + (start_year + 1);
                semester = (semester == 2) ? 1 : 2;
                txtYear.setText("Năm học " + school_year);
                txtSemester.setText("Học kỳ " + semester);
                getClassList(school_year, String.valueOf(semester));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_year = (semester == 2) ? (start_year + 1) : start_year;
                school_year = start_year + "-" + (start_year + 1);
                semester = (semester == 2) ? 1 : 2;
                txtYear.setText("Năm học " + school_year);
                txtSemester.setText("Học kỳ " + semester);
                getClassList(school_year, String.valueOf(semester));
            }
        });

        classList = new ArrayList<>();
        classAdapter = new ClassOfThisLecturerAdapter(getContext(), classList);
        rcview_manageClass.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        rcview_manageClass.setAdapter(classAdapter);
        getClassList("", "");
        return view;

    }

    private void getClassList(String school_year, String semester) {
        SharedPreferences useSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getActivity().getApplicationContext());
        retrofit.create(LecturerService.class).getSubjectList(useSharedPreferences.getString("user_id", ""), school_year, semester)
                .enqueue(new Callback<SubjectListofLecturerResponse>() {
                    @Override
                    public void onResponse(Call<SubjectListofLecturerResponse> call, Response<SubjectListofLecturerResponse> response) {
                        Log.d("MANAGE_CLASS", response.message());
                        if (response.isSuccessful() && response.body().getSuccess()) {
                            classList.clear();
                            classList.addAll(response.body().getSubject_lists());
                            classAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<SubjectListofLecturerResponse> call, Throwable t) {
                        Log.d("fail", String.valueOf(t));
                    }


                });
    }

}