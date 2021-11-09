package com.example.supconnect;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.widget.*;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.RetrofitResponse.SubjectListofStudentResponse;
import com.example.supconnect.adapter.DropDownSelectSubjectAdapter;
import com.example.supconnect.model.SubjectOfThisStudent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddTargetModal extends AppCompatDialogFragment{
    private Spinner spinner;
    private ArrayList<SubjectOfThisStudent> subjectOfThisStudents = new ArrayList<>();
    private DropDownSelectSubjectAdapter dropDownSelectSubjectAdapter;
    private SharedPreferences userSharedPreferences;
    private EditText edtTarget;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_target, null);
        edtTarget = view.findViewById(R.id.edt_grade_target);
        userSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        builder.setView(view).setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Tạo mục tiêu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = spinner.getSelectedItemPosition();
                String student_id = userSharedPreferences.getString("user_id", "");
                String subject_class_id = subjectOfThisStudents.get(position).getSubjectClass();
                String grade_target = edtTarget.getText().toString();
                if(!grade_target.isEmpty()) {
                    createTarget(student_id, subject_class_id, grade_target);
                }

            }
        });
        spinner = (Spinner) view.findViewById(R.id.choose_subject);
        dropDownSelectSubjectAdapter  = new DropDownSelectSubjectAdapter(this.getContext(), subjectOfThisStudents, R.layout.choose_subject_target_list, R.id.subject_chosen);
        spinner.setAdapter(dropDownSelectSubjectAdapter);
        getListSubject();
        return builder.create();
    }

    private void getListSubject() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(StudentService.class).getListSubject(userSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<SubjectListofStudentResponse>() {
                    @Override
                    public void onResponse(Call<SubjectListofStudentResponse> call, Response<SubjectListofStudentResponse> response) {
                        if(response.isSuccessful()) {
                            subjectOfThisStudents.addAll(response.body().getSubjects());
                            dropDownSelectSubjectAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubjectListofStudentResponse> call, Throwable t) {

                    }
                });
    }

    private void createTarget(String student_id, String subject_class_id, String grade_target) {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(StudentService.class).createTarget(student_id, subject_class_id, grade_target)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        Log.d("CREATE_TARGET: ", response.message());
                        if(response.isSuccessful()) {
                            Log.d("CREATE_TARGET_SUCCESS: ", "CREATE_TARGET_SUCCESS");
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Log.d("CREATE_TARGET ERR: ", t.getMessage());
                    }
                });
    }
}
