package com.example.supconnect.Fragments;

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

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.AnnouncementResponse;
import com.example.supconnect.adapter.AnnouncementListAdapter;
import com.example.supconnect.adapter.TargetScrollViewAdapter;
import com.example.supconnect.model.AnnouncementListItem;
import com.example.supconnect.model.TargetScrollviewItem;

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
public class HomeFragmentLecturer extends Fragment {
    private RecyclerView targetRecyclerView;
    private TargetScrollViewAdapter targetScrollViewAdapter;
    private List<TargetScrollviewItem> targetScrollviewItems;
    private RecyclerView announcementRecyclerView;
    private AnnouncementListAdapter announcementListAdapter;
    private List<AnnouncementListItem> announcementListItems = new ArrayList<>();
    private View view;
    private LinearLayout header;
    private CardView  assignment1, assignment2, assignment3;
    private TextView assignment1Subject, assignment1Deadline, assignment2Subject, assignment2Deadline, assignment3Subject, assignment3Deadline;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragmentLecturer() {
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
    public static HomeFragmentLecturer newInstance(String param1, String param2) {
        HomeFragmentLecturer fragment = new HomeFragmentLecturer();
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
        view = inflater.inflate(R.layout.home_fragment_lecturer, container, false);
        announcementRecyclerView = view.findViewById(R.id.latestAnnouncementScrollView);
        header = view.findViewById(R.id.headerhome);
        header.setVisibility(View.GONE);
        targetRecyclerView = view.findViewById(R.id.targetScrollView);
        setUpAnnouncementRecyclerView();
        return view;
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

}