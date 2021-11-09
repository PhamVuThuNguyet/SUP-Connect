package com.example.supconnect.LectureClient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.GradeInputResponse;
import com.example.supconnect.RetrofitResponse.GradeOfStudentResponse;
import com.example.supconnect.RetrofitResponse.SubjectGradeColResponse;
import com.example.supconnect.model.GradeColumn;
import com.example.supconnect.model.SubjectResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InputScoreActivity extends AppCompatActivity {
    TextView txt_diligence,txt_assignment,txt_midterm,txt_endterm, txtStudentName, txtStudentID, txtStudentClass;
    CircleImageView imgStudentAvatar;
    EditText edt_diligence,edt_assignment,edt_midterm,edt_endterm;
    Button btnyes,btnno;
    ImageButton backBtn;
    String studentId;
    String subject;
    Map<Integer, Float> gradeOfCols;
    List<GradeColumn> gradeColumnList;
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_score);
        initViews();
        //TODO: get subjectClass and Student id form previous activity
        subject = "LTDD(1)";
        studentId = "19IT003";
        retrofit = new RetrofitClient().getRetrofit(this);
        getOldGrade();
        getGradeColumn();
        btnyes.setOnClickListener(v -> {
            inputGrade();
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inputGrade() {
        Log.d("inputGrade", "Called");
        gradeOfCols = new HashMap<>();
        inputGradeToMap();

        Log.d("gradeOfCols", gradeOfCols.toString());

        Iterator iterator = gradeOfCols.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            storeDB(studentId, (int) pair.getKey(), (float) pair.getValue());
        }
        Toast.makeText(this,
                "Nhập điểm thành công! Sinh viên: " + studentId + ". Môn: " + subject,
                Toast.LENGTH_SHORT).show();
    }

    private void storeDB(String studentId, Integer gradeBookId, Float grade) {
        retrofit.create(LecturerService.class).inputGrade(studentId, gradeBookId, grade)
                .enqueue(new Callback<GradeInputResponse>() {
                    @Override
                    public void onResponse(Call<GradeInputResponse> call, Response<GradeInputResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("Input Grade", response.body().getNew().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GradeInputResponse> call, Throwable t) {
                        Log.d("Input grade", t.getMessage());
                    }
                });
    }

    private void inputGradeToMap() {
        float gradeType1, gradeType2, gradeType3, gradeType4;
        try {
            //chuyen can
            gradeType1 = Float.parseFloat(edt_diligence.getText().toString());
            gradeOfCols.put(getGradeByType(1), gradeType1);
        } catch (NumberFormatException exception) {
            gradeOfCols.remove(getGradeByType(1));
        }
        try {
            //bai tap
            gradeType2 = Float.parseFloat(edt_assignment.getText().toString());
            gradeOfCols.put(getGradeByType(2), gradeType2);
        } catch (NumberFormatException exception) {
            gradeOfCols.remove(getGradeByType(2));
        }
        try {
            //giua ky
            gradeType3 = Float.parseFloat(edt_midterm.getText().toString());
            gradeOfCols.put(getGradeByType(3), gradeType3);
        } catch (NumberFormatException exception) {
            gradeOfCols.remove(getGradeByType(3));
        }
        try {
            //cuoi ky
            gradeType4 = Float.parseFloat(edt_endterm.getText().toString());
            gradeOfCols.put(getGradeByType(4), gradeType4);
        } catch (NumberFormatException exception) {
            gradeOfCols.remove(getGradeByType(4));
        }
    }

    private Integer getGradeByType(int i) {
        for (GradeColumn col: gradeColumnList) {
            if(col.getTypeId() == i) {
                return col.getGradeBookId();
            }
        }
        return null;
    }

    private void getGradeColumn() {
        gradeColumnList = new ArrayList<>();
        retrofit.create(LecturerService.class).getGradeCols(subject)
                .enqueue(new Callback<SubjectGradeColResponse>() {
                    @Override
                    public void onResponse(Call<SubjectGradeColResponse> call, Response<SubjectGradeColResponse> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            gradeColumnList.addAll(response.body().getGradeColumns());
                            Log.d("grade columns", ""+gradeColumnList.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<SubjectGradeColResponse> call, Throwable t) {
                        Log.d("get grade columns", t.getMessage());
                    }
                });
    }

    private void initViews() {
        txt_diligence = findViewById(R.id.txt_diligence);
        txt_assignment = findViewById(R.id.txt_assignment);
        txt_midterm = findViewById(R.id.txt_midterm);
        txt_endterm = findViewById(R.id.txt_endterm);
        edt_diligence = findViewById(R.id.edt_diligence);
        edt_assignment = findViewById(R.id.edt_assignment);
        edt_midterm = findViewById(R.id.edt_midterm);
        edt_endterm = findViewById(R.id.edt_endterm);
        btnno = findViewById(R.id.btn_no);
        btnyes = findViewById(R.id.btn_yes);
        backBtn = findViewById(R.id.backBtn);
        txtStudentName = findViewById(R.id.txt_student_name);
        txtStudentID = findViewById(R.id.txt_student_id);
        txtStudentClass = findViewById(R.id.txt_student_class);
        imgStudentAvatar = findViewById(R.id.img_student_avatar);

        txtStudentName.setText(getIntent().getStringExtra("studentName"));
        txtStudentID.setText(getIntent().getStringExtra("studentID"));
        txtStudentClass.setText(getIntent().getStringExtra("className"));
        Picasso.get().load(getIntent().getStringExtra("avatar")).resize(400, 400).centerCrop().into(imgStudentAvatar);
    }

    private void getOldGrade() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(this);
        retrofit.create(StudentService.class).getGradeOfStudent(getIntent().getStringExtra("studentID"), "", "")
                .enqueue(new Callback<GradeOfStudentResponse>() {
                    @Override
                    public void onResponse(Call<GradeOfStudentResponse> call, Response<GradeOfStudentResponse> response) {
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            List<SubjectResult> subjectResultList = new ArrayList<>();
                            subjectResultList.addAll(response.body().getSubjectResults());
                            for(int i = 0; i < subjectResultList.size(); i++) {
                                if(subjectResultList.get(i).getNameSubject().equals(getIntent().getStringExtra("classID"))) {
                                    edt_diligence.setText(String.valueOf(subjectResultList.get(i).getPoint_Rollup()));
                                    edt_assignment.setText(String.valueOf(subjectResultList.get(i).getPoint_Assign()));
                                    edt_midterm.setText(String.valueOf(subjectResultList.get(i).getPoint_midTerm()));
                                    edt_endterm.setText(String.valueOf(subjectResultList.get(i).getPoint_EndTerm()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GradeOfStudentResponse> call, Throwable t) {

                    }
                });
    }
}
