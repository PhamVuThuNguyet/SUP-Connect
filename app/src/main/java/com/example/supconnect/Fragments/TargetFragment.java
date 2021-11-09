package com.example.supconnect.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.AddTargetModal;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.TargetListResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.PersonalTargetListAdapter;
import com.example.supconnect.adapter.TransactionHistoryAdapter;
import com.example.supconnect.model.PersonalTargetItem;
import com.example.supconnect.model.Student;
import com.example.supconnect.model.TransactionHistoryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
public class TargetFragment extends Fragment {

    private  View view;
    private RecyclerView personalTargetList;
    private List<PersonalTargetItem> personalTargetItems = new ArrayList<>();
    private PersonalTargetListAdapter personalTargetListAdapter;
    private FloatingActionButton addTarget;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SharedPreferences userSharedPreferences;
    private String user_id;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TargetFragment() {
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
    public static TargetFragment newInstance(String param1, String param2) {
        TargetFragment fragment = new TargetFragment();
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
        view= inflater.inflate(R.layout.target_fragment, container, false);

        userSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = Helper.getStudentID(userSharedPreferences);

        initViews(view);

        personalTargetListAdapter = new PersonalTargetListAdapter(getContext());
        personalTargetListAdapter.setPersonalTargetItems(personalTargetItems);

        personalTargetList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        personalTargetList.setAdapter(personalTargetListAdapter);

        addTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTarget();
            }
        });

        getListTarget();
        return view;
    }
    private void openAddTarget() {
        AddTargetModal addTargetModal = new AddTargetModal();
        addTargetModal.show(getActivity().getSupportFragmentManager(), "Add Target");
    }

    private void getListTarget() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
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
    private void initViews(View view) {
        personalTargetList = view.findViewById(R.id.personalTargetList);
        addTarget = view.findViewById(R.id.addTargetBtn);
    }
}