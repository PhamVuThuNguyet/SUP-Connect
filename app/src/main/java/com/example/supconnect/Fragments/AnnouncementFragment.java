package com.example.supconnect.Fragments;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;

import com.example.supconnect.AnnouncementDetailActivity;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.AnnouncementResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.model.Announcement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnnouncementFragment extends Fragment {
    View view;
    RecyclerView rc_view;
    ArrayList<Announcement> AnnounceList = new ArrayList<>();
    AnnouncementAdapter adapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rc_view_announcement, container, false);
        rc_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        rc_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rc_view.setItemAnimator(new DefaultItemAnimator());
        adapter = new AnnouncementAdapter(AnnounceList);
        rc_view.setAdapter(adapter);
        getAnnouncement();
        return view;
    }

    private void getAnnouncement() {
        AnnounceList.clear();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(StudentService.class).getAnnouncement()
                .enqueue(new Callback<AnnouncementResponse>() {
                    @Override
                    public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
                        Log.d("ANNOUNCEMENT_RES: ", response.message());
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            AnnounceList.addAll(response.body().getAnnouncement());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnouncementResponse> call, Throwable t) {

                    }
                });
    }

    public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
        ArrayList<Announcement> AnnouncementList = new ArrayList<>();

        public AnnouncementAdapter( ArrayList<Announcement> announcementList) {
            AnnouncementList = announcementList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.announcement_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Announcement announcement = AnnouncementList.get(position);
            holder.Title.setText(announcement.getTitle());
            holder.Date.setText(Helper.convertDateTime(announcement.getDate()));
            holder.layout.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), AnnouncementDetailActivity.class);
                intent.putExtra("announcement_id", announcement.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            });
        }


        @Override
        public int getItemCount() {
            return AnnouncementList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView Title,Date;
            LinearLayout layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Title = (TextView) itemView.findViewById(R.id.title);
                Date = (TextView) itemView.findViewById(R.id.date);
                layout = itemView.findViewById(R.id.layout_announcement);
            }
        }
        }
    }
