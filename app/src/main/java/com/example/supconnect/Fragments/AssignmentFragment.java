package com.example.supconnect.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.AssignmentService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.AnnouncementDetailActivity;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.AssignmentResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.model.Assignment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AssignmentFragment extends Fragment {
    View view;
    RecyclerView rc_view;
    ArrayList<Assignment> AssignList = new ArrayList<>();
    AssignmentAdapter adapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rc_view_announcement, container, false);
        rc_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        rc_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rc_view.setItemAnimator(new DefaultItemAnimator());


        adapter = new AssignmentAdapter(AssignList);
        rc_view.setAdapter(adapter);
        getAssignmentsList();
        return view;
    }

    private void getAssignmentsList() {
        AssignList.clear();
        SharedPreferences useSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(AssignmentService.class).getAssignmentList(useSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<AssignmentResponse>() {
                    @Override
                    public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            AssignList.addAll(response.body().getAssignments());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<AssignmentResponse> call, Throwable t) {

                    }
                });
    }

    public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
        ArrayList<Assignment> AssignmentList;

        public AssignmentAdapter( ArrayList<Assignment> assignments) {
            AssignmentList = assignments;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.assignment_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.SubjectClass.setText(AssignmentList.get(position).getSubjectClass());
            holder.AssignContent.setText(AssignmentList.get(position).getDescription());
            holder.Date.setText((CharSequence) Helper.convertDateTime(AssignmentList.get(position).getDeadline()));
            holder.subjecticon.setImageResource(R.drawable.ic_sbj_eng);

            holder.assignmentitem.setOnClickListener(v->{
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", AssignmentList.get(position).getAssignmentId());
                intent.putExtra("announcement_type", AssignmentList.get(position).getAnnouncementType());
                intent.putExtra("class_name", AssignmentList.get(position).getSubjectClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return AssignmentList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView SubjectClass, AssignContent, Date;
            ImageView subjecticon;
            CardView assignmentitem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                SubjectClass = (TextView) itemView.findViewById(R.id.subject_class);
                AssignContent = (TextView) itemView.findViewById(R.id.assign_content);
                Date = (TextView) itemView.findViewById(R.id.deadline);
                subjecticon = itemView.findViewById(R.id.icon_assign);
                assignmentitem = itemView.findViewById(R.id.assignmentitem);
            }
        }
    }

}
