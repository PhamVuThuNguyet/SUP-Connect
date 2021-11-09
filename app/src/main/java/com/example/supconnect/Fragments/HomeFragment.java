package com.example.supconnect.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.AssignmentService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.AnnouncementDetailActivity;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.AnnouncementResponse;
import com.example.supconnect.RetrofitResponse.AssignmentResponse;
import com.example.supconnect.RetrofitResponse.TargetListResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.AnnouncementListAdapter;
import com.example.supconnect.adapter.TargetScrollViewAdapter;
import com.example.supconnect.model.AnnouncementListItem;
import com.example.supconnect.model.Assignment;
import com.example.supconnect.model.PersonalTargetItem;
import com.example.supconnect.model.Student;
import com.google.gson.Gson;

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
public class HomeFragment extends Fragment {
    private RecyclerView targetRecyclerView;
    private TargetScrollViewAdapter targetScrollViewAdapter;
    private List<PersonalTargetItem> targetScrollviewItems = new ArrayList<>();
    private RecyclerView announcementRecyclerView;
    private AnnouncementListAdapter announcementListAdapter;
    private List<AnnouncementListItem> announcementListItems = new ArrayList<>();
    private View view;
    private LinearLayout header;
    private CardView  assignment1, assignment2, assignment3;
    private TextView assignment1Subject, assignment1Deadline, assignment2Subject, assignment2Deadline, assignment3Subject, assignment3Deadline;
    private List<Assignment> listAssignments = new ArrayList<>();
    private String user_id;
    private SharedPreferences useSharedPreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.home_fragment, container, false);
        announcementRecyclerView = view.findViewById(R.id.latestAnnouncementScrollView);
        header = view.findViewById(R.id.headerhome);
        header.setVisibility(View.GONE);
        targetRecyclerView = view.findViewById(R.id.targetScrollView);

        useSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = Helper.getStudentID(useSharedPreferences);

        init();
        setUpDueAssignment();
        setUpTargetRecyclerView();
        setUpAnnouncementRecyclerView();
        return view;
    }

    private void init() {
        assignment1Subject = view.findViewById(R.id.assignment1_subject);
        assignment1Deadline = view.findViewById(R.id.assignment1_deadline);
        assignment2Subject = view.findViewById(R.id.assignment2_subject);
        assignment2Deadline = view.findViewById(R.id.assignment2_deadline);
        assignment3Subject = view.findViewById(R.id.assignment3_subject);
        assignment3Deadline = view.findViewById(R.id.assignment3_deadline);
        getAssignmentsList();
    }

    private void getAssignmentsList() {
        listAssignments = new ArrayList<>();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(AssignmentService.class).getAssignmentList(user_id)
                .enqueue(new Callback<AssignmentResponse>() {
                    @Override
                    public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {

                        if(response.body().getAssignments().size() >= 3) {
                            assignment1.setVisibility(View.VISIBLE);
                            assignment2.setVisibility(View.VISIBLE);
                            assignment3.setVisibility(View.VISIBLE);

                            assignment1Subject.setText(response.body().getAssignments().get(0).getSubjectClass());
                            assignment1Deadline.setText(Helper.timeDifferent(response.body().getAssignments().get(0).getDeadline()));
                            listAssignments.add(response.body().getAssignments().get(0));

                            assignment2Subject.setText(response.body().getAssignments().get(1).getSubjectClass());
                            assignment2Deadline.setText(Helper.timeDifferent(response.body().getAssignments().get(1).getDeadline()));
                            listAssignments.add(response.body().getAssignments().get(1));

                            assignment3Subject.setText(response.body().getAssignments().get(2).getSubjectClass());
                            assignment3Deadline.setText(Helper.timeDifferent(response.body().getAssignments().get(2).getDeadline()));
                            listAssignments.add(response.body().getAssignments().get(2));
                        }
                        else if(response.body().getAssignments().size() >= 2) {
                            assignment2.setVisibility(View.VISIBLE);
                            assignment3.setVisibility(View.VISIBLE);

                            assignment2Subject.setText(response.body().getAssignments().get(0).getSubjectClass());
                            assignment2Deadline.setText(Helper.timeDifferent(response.body().getAssignments().get(0).getDeadline()));
                            listAssignments.add(response.body().getAssignments().get(0));

                            assignment3Subject.setText(response.body().getAssignments().get(1).getSubjectClass());
                            assignment3Deadline.setText(Helper.timeDifferent(response.body().getAssignments().get(1).getDeadline()));
                            listAssignments.add(response.body().getAssignments().get(1));
                        }
                        else if(response.body().getAssignments().size() >= 1){
                            assignment2.setVisibility(View.VISIBLE);
                            assignment2Subject.setText(response.body().getAssignments().get(0).getSubjectClass());
                            assignment2Deadline.setText(Helper.timeDifferent(response.body().getAssignments().get(0).getDeadline()));
                            listAssignments.add(response.body().getAssignments().get(0));
                        }
                        setOnClickForDueAssignment();
                    }

                    @Override
                    public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                        Log.d("HOME ERR: ", t.getMessage());
                    }
                });
    }

    private void setUpDueAssignment() {
        assignment1 = view.findViewById(R.id.assignment1);
        assignment2 = view.findViewById(R.id.assignment2);
        assignment3 = view.findViewById(R.id.assignment3);

        assignment1.setVisibility(View.GONE);
        assignment2.setVisibility(View.GONE);
        assignment3.setVisibility(View.GONE);
    }

    private void setOnClickForDueAssignment(){
        Log.d("ListAssigments", String.valueOf(listAssignments.size()));
        if(listAssignments.size() == 1) {
            assignment2.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", listAssignments.get(0).getAssignmentId());
                intent.putExtra("announcement_type", listAssignments.get(0).getAnnouncementType());
                intent.putExtra("class_name", listAssignments.get(0).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });
        }
        else if(listAssignments.size() == 2) {
            assignment2.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", listAssignments.get(0).getAssignmentId());
                intent.putExtra("announcement_type", listAssignments.get(0).getAnnouncementType());
                intent.putExtra("class_name", listAssignments.get(0).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });

            assignment3.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", listAssignments.get(1).getAssignmentId());
                intent.putExtra("announcement_type", listAssignments.get(1).getAnnouncementType());
                intent.putExtra("class_name", listAssignments.get(1).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });
        }
        else {
            assignment1.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", listAssignments.get(0).getAssignmentId());
                intent.putExtra("announcement_type", listAssignments.get(0).getAnnouncementType());
                intent.putExtra("class_name", listAssignments.get(0).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });

            assignment2.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", listAssignments.get(1).getAssignmentId());
                intent.putExtra("announcement_type", listAssignments.get(1).getAnnouncementType());
                intent.putExtra("class_name", listAssignments.get(1).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });

            assignment3.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", listAssignments.get(2).getAssignmentId());
                intent.putExtra("announcement_type", listAssignments.get(2).getAnnouncementType());
                intent.putExtra("class_name", listAssignments.get(2).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });
        }
    }
    
    private void setUpAnnouncementRecyclerView() {
        announcementListAdapter = new AnnouncementListAdapter(getContext());
        announcementListAdapter.setAnnouncementListItems(announcementListItems);
        announcementRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        announcementRecyclerView.setAdapter(announcementListAdapter);
        getAnnouncementItems();
    }

    private void getAnnouncementItems() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(StudentService.class).getAnnouncement()
                .enqueue(new Callback<AnnouncementResponse>() {
                    @Override
                    public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
                        Log.d("ANNOUNCEMENT_RES: ", response.message());
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            for(int i = 0; i < 1; i++) {
                                if(response.body().getAnnouncement().get(i) != null) {
                                    AnnouncementListItem announcementListItem = new AnnouncementListItem(response.body().getAnnouncement().get(i).getId(), response.body().getAnnouncement().get(i).getTypeid(),response.body().getAnnouncement().get(i).getTitle(), response.body().getAnnouncement().get(i).getDate());
                                    announcementListItems.add(announcementListItem);
                                }
                            }
                            announcementListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnouncementResponse> call, Throwable t) {

                    }
                });

    }

    private void setUpTargetRecyclerView() {
//        targetScrollviewItems = getTargetItems();
        targetScrollViewAdapter = new TargetScrollViewAdapter(getContext());
        targetScrollViewAdapter.setItemList(targetScrollviewItems);
        targetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        targetRecyclerView.setAdapter(targetScrollViewAdapter);
        getListTarget();
    }

    private void getListTarget() {
        SharedPreferences userSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(StudentService.class).getListTarget(userSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<TargetListResponse>() {
                    @Override
                    public void onResponse(Call<TargetListResponse> call, Response<TargetListResponse> response) {
                        Log.d("TARGET_LIST", response.message());
                        if(response.isSuccessful()) {
                            Log.d("TARGET_LIST", response.body().getTargets().toString());
                            targetScrollviewItems.addAll(response.body().getTargets());
                            targetScrollViewAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<TargetListResponse> call, Throwable t) {
                        Log.d("TARGET_LIST_ERR", t.getMessage());
                    }
                });
    }

//    private List<TargetScrollviewItem> getTargetItems() {
//        List<TargetScrollviewItem> list = new ArrayList<>();
//        list.add(new TargetScrollviewItem("Tiếng anh", R.drawable.ic_sbj_eng));
//        list.add(new TargetScrollviewItem("Toán", R.drawable.ic_sbj_math));
//        list.add(new TargetScrollviewItem("Thiết kế web", R.drawable.ic_sbj_web_design));
//        list.add(new TargetScrollviewItem("Tiếng anh", R.drawable.ic_sbj_eng));
//        list.add(new TargetScrollviewItem("Toán", R.drawable.ic_sbj_math));
//        list.add(new TargetScrollviewItem("Thiết kế web", R.drawable.ic_sbj_web_design));
//        return list;
//    }


}