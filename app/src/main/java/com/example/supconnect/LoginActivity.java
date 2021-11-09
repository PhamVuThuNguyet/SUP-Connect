package com.example.supconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.*;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.UserService;
import com.example.supconnect.LectureClient.MainActivityLecturer;
import com.example.supconnect.RetrofitResponse.UserResponse;
import com.example.supconnect.model.Lecturer;
import com.example.supconnect.model.Parent;
import com.example.supconnect.model.Student;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private TextView txtUsername, txtPassword;
    private Button loginbtn;
    private Student student;
    SharedPreferences userSharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Boolean isLogged = userSharedPreferences.getBoolean("isLogged", false);
        if(isLogged) {
            if(userSharedPreferences.getString("role", "").equals("1")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else if(userSharedPreferences.getString("role", "").equals("2")) {
                Intent intent = new Intent(LoginActivity.this, MainActivityLecturer.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        setContentView(R.layout.activity_login);
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);

        loginbtn = findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(v->{
            login();
        });
    }

    private void login() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if(!(username.isEmpty() || password.isEmpty())) {
            Retrofit retrofit = new RetrofitClient().getRetrofit(LoginActivity.this);
            retrofit.create(UserService.class)
                    .login(username, password)
                    .enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            Log.d("LoginRes:", response.message());
                            if(response.isSuccessful() && response.body().getSuccess()) {
                                SharedPreferences userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = userSharedPreferences.edit();
                                if(response.body().getRole() == 1) {
                                    Student student = response.body().getStudent();
                                    /**
                                     * FIXME: Suggest use this instead:
                                     *
                                     * Gson gson = new Gson();
                                     * String studentJson = gson.toJson(student);
                                     * editor.putString("Student", studentJson);
                                     * editor.apply();
                                     *
                                     * And in other activity:
                                     * String studentJson = sharedPreference.getString("Student");
                                     * Student student = gson.formJson(studentJson);
                                     */
                                    editor.putString("user_id", student.getStudentId());
                                    editor.putString("account_id", String.valueOf(student.getAccountId()));
                                    editor.putString("username", student.getUsername());
                                    editor.putString("password", response.body().getPassword());
                                    editor.putString("firstName", student.getFirstName());
                                    editor.putString("lastName", student.getLastName());
                                    editor.putString("dateOfBirth", student.getDateOfBirth());
                                    editor.putString("phoneNumber", student.getPhoneNumber());
                                    editor.putString("startYear", String.valueOf(student.getStartYear()));
                                    editor.putString("endYear", String.valueOf(student.getEndYear()));
                                    editor.putString("classId", String.valueOf(student.getClassId()));
                                    editor.putString("email", student.getEmail());
                                    editor.putString("avatar", student.getAvatar());
                                    editor.putString("class", student.getClassName());
                                    editor.putString("role", String.valueOf(response.body().getRole()));
                                    editor.putBoolean("isLogged", true);
                                    editor.apply();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                //Lecturer
                                if(response.body().getRole() == 2) {
                                    Lecturer lecturer = response.body().getLecturer();
                                    /**
                                     * FIXME: Suggest use this instead:
                                     *
                                     * Gson gson = new Gson();
                                     * String lecturerJson = gson.toJson(lecturer);
                                     * editor.putString("Lecturer", lecturerJson);
                                     * editor.apply();
                                     *
                                     * And in other activity:
                                     * String lecturerJson = sharedPreference.getString("Lecturer");
                                     * Student student = gson.formJson(lecturerJson);
                                     */
                                    editor.putString("user_id", lecturer.getLecturerId());
                                    editor.putString("account_id", String.valueOf(lecturer.getAccountId()));
                                    editor.putString("firstName", lecturer.getFirstNameLecturer());
                                    editor.putString("lastName", lecturer.getLastNameLecturer());
                                    editor.putString("dateOfBirth", lecturer.getDateOfBirth());
                                    editor.putString("phoneNumber", lecturer.getPhoneNumberLecturer());
                                    editor.putString("faculty_id", String.valueOf(lecturer.getFacultyId()));
                                    editor.putString("degree", lecturer.getDegree());
                                    editor.putString("email", lecturer.getEmail());
                                    editor.putString("avatar", lecturer.getAvatar());
                                    editor.putString("faculty", lecturer.getFaculty());
                                    editor.putString("role", String.valueOf(response.body().getRole()));
                                    editor.putBoolean("isLogged", true);
                                    editor.apply();
                                    Intent intent = new Intent(LoginActivity.this, MainActivityLecturer.class);
                                    startActivity(intent);
                                }
                                //Parent
                                if(response.body().getRole() == 3) {
                                    Parent parent = response.body().getParent();

                                    Student student = response.body().getStudent();
                                    Gson gson = new Gson();

                                    editor.putString("user_id", parent.getParentId()+"");
                                    editor.putString("account_id", parent.getAccountId()+"");
                                    editor.putString("firstName", parent.getFirstNameParent());
                                    editor.putString("lastName", parent.getLastNameParent());
                                    editor.putString("dateOfBirth", parent.getDateOfBirth());
                                    editor.putString("phoneNumber", parent.getDateOfBirth());

                                    editor.putString("role", String.valueOf(response.body().getRole()));

                                    editor.putString("avatar", parent.getAvatar());
                                    editor.putString("studentOfParent", gson.toJson(student));
                                    editor.apply();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    /**
                                     * TODO: get the student object:
                                     *
                                     * 1. Get the json string form SharedPreference.
                                     * .String json = ..sharedPreference.getString("studentOfParent");
                                     *
                                     * 2. Convert it to Student object.
                                     * Student student = gson.fromJson(json, Student.class);
                                     *
                                     * ** Also suggest save other object in one json string rather than put each property as a
                                     * ** string, for readability and maintaining.
                                     */
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.d("LoginErr", t.getMessage());
                        }
                    });
        }
    }
}
