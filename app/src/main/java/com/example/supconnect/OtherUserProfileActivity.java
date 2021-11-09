package com.example.supconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.StudentService;
import com.example.supconnect.RetrofitResponse.UserResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.model.Student;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtherUserProfileActivity extends AppCompatActivity {

    private TextView userFullName, userStudentID, userBirthDay, userMainClass, userPeriod, userEmail, userPhoneNumber;
    private ImageView chatBtn;
    private CircleImageView userAvatar;
    private Student otherstudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        initViews();
    }

    private void initViews() {
        userFullName = findViewById(R.id.userFullName);
        userStudentID = findViewById(R.id.userStudentID);
        userBirthDay = findViewById(R.id.userBirthDay);
        userMainClass = findViewById(R.id.userMainClass);
        userPeriod = findViewById(R.id.userPeriod);
        userEmail = findViewById(R.id.userEmail);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        userAvatar = findViewById(R.id.userAvatar);
        chatBtn = findViewById(R.id.chatBtn);

        setUpUserInfo();

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
                if (!userSharedPreferences.getString("user_id", "").equals(otherstudent.getStudentId())) {
                    Intent intent = new Intent(OtherUserProfileActivity.this, ConversationActivity.class);
                    intent.putExtra("room_id", 0);
                    intent.putExtra("user_2", otherstudent.getStudentId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

    }
        private void setUpUserInfo() {
            Retrofit retrofit = new RetrofitClient().getRetrofit(this);
            retrofit.create(StudentService.class).getOtherStudentInfo(getIntent().getExtras().getString("card_id"))
                    .enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                otherstudent = response.body().getStudent();
                                Picasso.get().load(otherstudent.getAvatar()).resize(350, 350).centerCrop().into(userAvatar);
                                userFullName.setText(otherstudent.getFirstName() + " " + otherstudent.getLastName());
                                userStudentID.setText(otherstudent.getStudentId());
                                userBirthDay.setText(Helper.convertDate(otherstudent.getDateOfBirth()));
                                userMainClass.setText(otherstudent.getClassName());
                                userPeriod.setText(otherstudent.getStartYear() + "-" + otherstudent.getEndYear());
                                userEmail.setText(otherstudent.getEmail());
                                userPhoneNumber.setText(otherstudent.getPhoneNumber());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.d("fail", String.valueOf(t));
                        }
                    });
        }

}