package com.example.supconnect.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.supconnect.API.AnnouncementService;
import com.example.supconnect.API.AssignmentService;
import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.TimeTableResponse;
import com.example.supconnect.adapter.DropDownSelectClassAdapter;
import com.example.supconnect.model.Lesson;
import com.example.supconnect.model.SubClassofThisTeacher;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;


public class CreateAssignmentNotificationFragment extends Fragment {
    private View view;
    private ImageView btnAddFile;
    private Button yes,no,chooseDeadline;
    private EditText edtTitle,edtContent,edtdeadline;
    private Calendar calendar;
    private ChipGroup chipGroup;
    private RelativeLayout relativeLayout;
    private ProgressDialog dialog;
    private Spinner addClass;
    private DropDownSelectClassAdapter dropDownSelectClassAdapter;
    private List<SubClassofThisTeacher> subClassofThisTeachers = new ArrayList<>();
    private SharedPreferences userSharedPreferences;
    private String subject_id;
    private TextView file_path;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String FilePath = data.getData().getPath();
                file_path.setVisibility(View.VISIBLE);
                file_path.setText(FilePath);
            }
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_assignment_notification_fragment, container, false);
        addClass = view.findViewById(R.id.spinner_add_class);
        file_path = view.findViewById(R.id.txt_file_path);
        userSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        dropDownSelectClassAdapter = new DropDownSelectClassAdapter(this.getContext(), subClassofThisTeachers, R.layout.choose_subject_target_list, R.id.subject_chosen);
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
        btnAddFile = view.findViewById(R.id.btn_addFile);
        yes = view.findViewById(R.id.btn_yes_notification);
        no = view.findViewById(R.id.btn_no_notification);
        chooseDeadline = view.findViewById(R.id.btn_chooseDeadline);
        edtTitle = view.findViewById(R.id.title_notification);
        edtContent = view.findViewById(R.id.content_notification);
        edtdeadline = view.findViewById(R.id.edt_date);
        chipGroup = view.findViewById(R.id.chipGroup);
        relativeLayout = view.findViewById(R.id.relativelayout3_assignment);
        edtdeadline.setInputType(InputType.TYPE_NULL);
        chooseDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDeadline();
            }
        });
        yes.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String description = edtContent.getText().toString();
            String deadline = edtdeadline.getText().toString();
            if(!(title.isEmpty() || deadline.isEmpty())) {
                createAssignment(subject_id, title, description, deadline);
            }
        });
        no.setOnClickListener(v -> getActivity().finish());
        btnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.putExtra(Intent.EXTRA_STREAM, uri);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, 1);
            }
        });
        getLecturerLessons();
        dialog = new ProgressDialog(getContext());
        return view;
    }

    private void ChooseDeadline(){
        final Calendar currentDate = Calendar.getInstance();
        calendar = Calendar.getInstance();
        int date = currentDate.get(Calendar.DATE);
        int month = currentDate.get(Calendar.MONTH);
        int year = currentDate.get(Calendar.YEAR);
        int hour = currentDate.get(Calendar.HOUR_OF_DAY);
        int minute = currentDate.get(Calendar.MINUTE);
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {
                calendar.set(year,monthofYear,dayOfMonth);
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH:mm a dd-MM-yyyy");
                        edtdeadline.setText(simpleFormatter.format(calendar.getTime()));
                    }
                },currentDate.get(Calendar.HOUR_OF_DAY),currentDate.get(Calendar.MINUTE),false).show();
            }
        },currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void createAssignment(String subject_id, String title, String description, String deadline) {
        dialog.setMessage("Đang tiến hành");
        dialog.show();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(AssignmentService.class).createAssignment(subject_id, title, description, deadline)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        Log.d("CREATE_ASSIGNMENT: ", response.message());
                        dialog.dismiss();
                        edtTitle.setText("");
                        edtContent.setText("");
                        edtdeadline.setText("");
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Log.d("CREATE_ASSIGNMENT_ERR: ", t.getMessage());
                        dialog.dismiss();
                        edtTitle.setText("");
                        edtContent.setText("");
                        edtdeadline.setText("");
                    }
                });

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

