package com.example.supconnect.LectureClient;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.UserResponse;
import com.example.supconnect.Utils.Helper;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileActivityForLecturer extends AppCompatActivity implements View.OnClickListener {

    private EditText editFirstName, editLastName, editStudentID, editMainClass, editBirthDay, editEmail, editPhoneNumber, edtDegree;
    private Button btnEditSubmit, btnEditCancel;
    private SharedPreferences userSharedPreferences;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_for_lecturer);
        initViews();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker picker = builder.build();
        picker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            calendar.setTimeInMillis((long) selection);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            editBirthDay.setText((sdf.format(calendar.getTime())));
        });

        editBirthDay.setOnClickListener(v -> {
            picker.show(getSupportFragmentManager(), picker.toString());
        });
    }

    private void initViews() {
        dialog = new ProgressDialog(EditProfileActivityForLecturer.this);
        dialog.setCancelable(false);
        userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editStudentID = findViewById(R.id.editStudentID);
        editMainClass = findViewById(R.id.editMainClass);
        editBirthDay = findViewById(R.id.editBirthDay);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        edtDegree = findViewById(R.id.editDegree);

        btnEditCancel = findViewById(R.id.btnEditCancel);
        btnEditSubmit = findViewById(R.id.btnEditSubmit);

        btnEditCancel.setOnClickListener(this);
        btnEditSubmit.setOnClickListener(this);

        setUpInfo();
    }
    /*
    To fill edit texts with old information
     */
    private void setUpInfo() {
        editFirstName.setText(userSharedPreferences.getString("firstName", ""));
        editLastName.setText(userSharedPreferences.getString("lastName", ""));
        editStudentID.setText(userSharedPreferences.getString("user_id", ""));
        editMainClass.setText(userSharedPreferences.getString("faculty", ""));
        editBirthDay.setText(Helper.convertDate(userSharedPreferences.getString("dateOfBirth", "")));
        editEmail.setText(userSharedPreferences.getString("email", ""));
        edtDegree.setText(userSharedPreferences.getString("degree", ""));
        editPhoneNumber.setText(userSharedPreferences.getString("phoneNumber", ""));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditCancel: {
                super.onBackPressed();
                break;
            }

            case R.id.btnEditSubmit: {
                updateInformation();
                break;
            }
        }
    }

    private void updateInformation() {
        dialog.setMessage("Đang cập nhật");
        dialog.show();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String date_of_birth = Helper.convertToSaveDate(editBirthDay.getText().toString());
        String email = editEmail.getText().toString();
        String phoneNumber = editPhoneNumber.getText().toString();

        retrofit.create(LecturerService.class)
                .updateInfo(userSharedPreferences.getString("user_id", "") ,firstName, lastName, date_of_birth, email, phoneNumber)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.d("EDITPROFILERES: ", response.message());
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            SharedPreferences.Editor editor = userSharedPreferences.edit();
                            editor.putString("firstName", firstName);
                            editor.putString("lastName", lastName);
                            editor.putString("date_of_birth", editBirthDay.getText().toString());
                            editor.putString("phoneNumber", phoneNumber);
                            editor.putString("email", email);
                            editor.apply();
                        }
                        if (!EditProfileActivityForLecturer.this.isFinishing() && dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("EDITPROFILEERR: ", t.getMessage());
                        if (!EditProfileActivityForLecturer.this.isFinishing() && dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
    }
}