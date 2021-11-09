package com.example.supconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.*;

import com.example.supconnect.API.AssignmentService;
import com.example.supconnect.API.LeaveNoticeService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.AnnouncementResponse;
import com.example.supconnect.RetrofitResponse.AssignmentResponse;
import com.example.supconnect.RetrofitResponse.LeaveNoticeResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.AttachmentListAdapter;
import com.example.supconnect.model.Announcement;
import com.example.supconnect.model.Assignment;
import com.example.supconnect.model.AttachmentFile;
import com.example.supconnect.model.LeaveNotice;
import com.example.supconnect.model.Student;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnnouncementDetailActivity extends AppCompatActivity {

    private TextView txtTitle, txtDate, txtDesc, deadline;
    private ImageButton back;
    private List<AttachmentFile> files = new ArrayList<>();
    ListView attachmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        attachmentList = findViewById(R.id.attachment_list);

        attachmentList.setOnItemClickListener((parent, view, position, id) -> {
            Object o = attachmentList.getItemAtPosition(position);
            AttachmentFile f = (AttachmentFile) o;

            /*
             * To download the file in browser
             */
            Intent downloadIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(f.getFileUrl()));
            Intent chooser = Intent.createChooser(downloadIntent, "Tải tệp này");
            startActivity(chooser);
        });
        txtTitle = findViewById(R.id.textView);
        txtDate = findViewById(R.id.textView2);
        txtDesc = findViewById(R.id.textView3);
        deadline = findViewById(R.id.deadline);
        if(String.valueOf(getIntent().getExtras().getInt("announcement_type")).equals("6")) {
            getAssignment();
        }else if(String.valueOf(getIntent().getExtras().getInt("announcement_type")).equals("5")){
            getLeaveNotice();
        }
        else {
            getAnnouncement();
        }
    }

    private void getLeaveNotice() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(AnnouncementDetailActivity.this);
        retrofit.create(LeaveNoticeService.class).getLeaveNotice(String.valueOf(getIntent().getExtras().getInt("announcement_id")))
                .enqueue(new Callback<LeaveNoticeResponse>() {
                    @Override
                    public void onResponse(Call<LeaveNoticeResponse> call, Response<LeaveNoticeResponse> response) {
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            LeaveNotice leaveNotice = response.body().getLeaveNotices().get(0);
                            txtTitle.setText(leaveNotice.getTitle());
                            txtDate.setText(Helper.convertDateTime(leaveNotice.getCreate_date()));
                            txtDesc.setText(leaveNotice.getTitle()+" ngày "+leaveNotice.getLeave_date());
                        }
                    }

                    @Override
                    public void onFailure(Call<LeaveNoticeResponse> call, Throwable t) {
                        Log.d("fail", t.getMessage());
                    }
                });
    }

    private void getAssignment() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(AnnouncementDetailActivity.this);
        retrofit.create(AssignmentService.class).getAssignment(String.valueOf(getIntent().getExtras().getInt("announcement_id")))
                .enqueue(new Callback<AssignmentResponse>() {
                    @Override
                    public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            Assignment assignment = response.body().getAssignments().get(0);
                            txtTitle.setText(getIntent().getExtras().getString("class_name"));
                            txtDate.setText(Helper.convertDateTime(assignment.getDeadline()));
                            txtDesc.setText(assignment.getDescription());
                            if(!(assignment.getAttachment() == null)) {
                                files.add(new AttachmentFile(assignment.getAttachment(), "word", ""));
                            }
                            attachmentList.setAdapter(new AttachmentListAdapter(AnnouncementDetailActivity.this, files));
                            deadline.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AssignmentResponse> call, Throwable t) {

                    }
                });
    }

    private void getAnnouncement() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(AnnouncementDetailActivity.this);
        retrofit.create(StudentService.class).getAnnouncement(String.valueOf(getIntent().getExtras().getInt("announcement_id")))
                .enqueue(new Callback<AnnouncementResponse>() {
                    @Override
                    public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            Announcement announcement = response.body().getAnnouncement().get(0);
                            txtTitle.setText(announcement.getTitle());
                            txtDate.setText(Helper.convertDateTime(announcement.getDate()));
                            txtDesc.setText(announcement.getDes());
                            if(!(announcement.getAttachment() == null)) {
                                files.add(new AttachmentFile(URLUtil.guessFileName(announcement.getAttachment(), null, null), "word", announcement.getAttachment()));
                            }
                            attachmentList.setAdapter(new AttachmentListAdapter(AnnouncementDetailActivity.this, files));
                        }
                    }

                    @Override
                    public void onFailure(Call<AnnouncementResponse> call, Throwable t) {

                    }
                });
    }
}