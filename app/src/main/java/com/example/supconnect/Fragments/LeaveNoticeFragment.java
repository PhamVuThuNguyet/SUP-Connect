package com.example.supconnect.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.LeaveNoticeService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;

import com.example.supconnect.AnnouncementDetailActivity;

import com.example.supconnect.R;

import com.example.supconnect.RetrofitResponse.AnnouncementResponse;
import com.example.supconnect.RetrofitResponse.LeaveNoticeResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.model.Announcement;
import com.example.supconnect.model.LeaveNotice;
import com.example.supconnect.model.Student;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LeaveNoticeFragment extends Fragment {
    View view;
    RecyclerView rc_view;
    ArrayList<LeaveNotice> noticeList;
    LeaveNoticeAdapter adapter;
    private String user_id;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rc_view_announcement, container, false);
        rc_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        rc_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rc_view.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences useSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = Helper.getStudentID(useSharedPreferences);

        noticeList = new ArrayList<>();
        getLeaveNotice();
        adapter = new LeaveNoticeAdapter(noticeList);
        rc_view.setAdapter(adapter);

        return view;
    }

    private void getLeaveNotice() {
        noticeList.clear();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(LeaveNoticeService.class).getLeaveNoticeList(user_id)
                .enqueue(new Callback<LeaveNoticeResponse>() {

                    @Override
                    public void onResponse(Call<LeaveNoticeResponse> call, Response<LeaveNoticeResponse> response) {

                        if(response.isSuccessful() && response.body().getSuccess()) {
                            noticeList.addAll(response.body().getLeaveNotices());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<LeaveNoticeResponse> call, Throwable t) {
                        Log.d("false",t.getMessage());
                    }
                });
    }

    public class LeaveNoticeAdapter extends RecyclerView.Adapter<LeaveNoticeAdapter.ViewHolder> {
        ArrayList<LeaveNotice> List;

        public LeaveNoticeAdapter(ArrayList<LeaveNotice> list) {
            List = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.leavenotice_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.Title.setText(List.get(position).getTitle());
            holder.Date.setText(Helper.convertDateTime(List.get(position).getCreate_date()));
            holder.nameLecture.setText(List.get(position).getNameLecture());
            holder.leavenoticeitem.setOnClickListener(v-> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", List.get(position).getLeave_notice_id());
                intent.putExtra("announcement_type", List.get(position).getAnnouncement_type());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return List.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView Title,Date,nameLecture;
            CardView leavenoticeitem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Title = (TextView) itemView.findViewById(R.id.title);
                Date = (TextView) itemView.findViewById(R.id.date);
                nameLecture = (TextView) itemView.findViewById(R.id.name_lecture);
                leavenoticeitem = itemView.findViewById(R.id.leavenoticeitem);
            }
        }
    }

}
