package com.example.supconnect.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;

import com.example.supconnect.API.AnnouncementService;
import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.TimeTableResponse;
import com.example.supconnect.adapter.DropDownSelectClassAdapter;
import com.example.supconnect.model.Lesson;
import com.example.supconnect.model.SubClassofThisTeacher;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateLeaveNotificationFragment extends Fragment {
    private View view;
    private Spinner addClass;
    private ImageView btnAddFile;
    private Button yes,no,chooseDealine;
    private EditText edtTitle,edtContent,edtdate;
    private ChipGroup chipGroup;
    private String subject_id;
    private RelativeLayout relativeLayout;
    private DropDownSelectClassAdapter dropDownSelectClassAdapter;
    private List<SubClassofThisTeacher> subClassofThisTeachers = new ArrayList<>();
    private SharedPreferences userSharedPreferences;
    ProgressDialog dialog;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_leave_notification_fragment, container, false);
        userSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        dropDownSelectClassAdapter = new DropDownSelectClassAdapter(this.getContext(), subClassofThisTeachers, R.layout.choose_subject_target_list, R.id.subject_chosen);
        addClass = view.findViewById(R.id.spinner_add_class);
        btnAddFile = view.findViewById(R.id.btn_addFile);
        yes = view.findViewById(R.id.btn_yes_notification);
        no = view.findViewById(R.id.btn_no_notification);
        chooseDealine = view.findViewById(R.id.btn_chooseDate);
        edtTitle = view.findViewById(R.id.title_notification);
        edtContent = view.findViewById(R.id.content_notification);
        edtdate = view.findViewById(R.id.edt_date);
        chipGroup = view.findViewById(R.id.chipGroup);
        relativeLayout = view.findViewById(R.id.relativelayout3_leave);
        addClass.setAdapter(dropDownSelectClassAdapter);
        addClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject_id = subClassofThisTeachers.get(position).getSubject_class_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chooseDealine.setOnClickListener(v -> ChooseDate());
        yes.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String description = edtContent.getText().toString();
            String create_date = edtdate.getText().toString();
            if(!title.isEmpty()) {
                createAnnouncement(title, description, create_date);
            }
        });
        no.setOnClickListener(v -> getActivity().finish());
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        getLecturerLessons();
        return view;
    }

    private void createAnnouncement(String title, String description, String create_date) {
        dialog.setMessage("Đang tiến hành");
        dialog.show();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(AnnouncementService.class).createAnnouncement("5", title, description, create_date)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        Log.d("CREATE_LEAVE_ANNOUNCE: ", response.message());
                        dialog.dismiss();
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Log.d("CREATE_LEAVE_ANNOUNCE_ERR: ", t.getMessage());
                        dialog.dismiss();
                    }
                });
        edtTitle.setText("");
        edtContent.setText("");
        edtdate.setText("");
    }

    private void ChooseDate(){
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MM-yyyy");
                edtdate.setText(simpleFormatter.format(calendar.getTime()));
            }
        },year,month,date);
        datePicker.show();
    }
    private void getLecturerLessons() {
        subClassofThisTeachers.clear();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(LecturerService.class).getTimeTable(userSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<TimeTableResponse>() {
                    @Override
                    public void onResponse(Call<TimeTableResponse> call, Response<TimeTableResponse> response) {
                        assert response.body() != null;
                        if(response.isSuccessful() && response.body().getSuccess())  {
                            for(int i = 0; i < response.body().getTimetables().size(); i++) {
                                Lesson lesson = response.body().getTimetables().get(i);
                                subClassofThisTeachers.add(new SubClassofThisTeacher(lesson.getSubjectClassId(), lesson.getSubjectClassName()));
                            }
                            subject_id = subClassofThisTeachers.get(0).getSubject_class_id();
                            dropDownSelectClassAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponse> call, Throwable t) {
                        Log.d("TimeTableERR: ", t.getMessage());
                    }
                });
    }
}
