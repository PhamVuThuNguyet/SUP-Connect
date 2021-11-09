package com.example.supconnect.LectureClient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.UserService;
import com.example.supconnect.ConversationActivity;
import com.example.supconnect.LoginActivity;
import com.example.supconnect.MainActivity;
import com.example.supconnect.OtherUserProfileActivity;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.example.supconnect.Utils.Helper;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileActivityForLecturer extends AppCompatActivity {

    private TextView userFullName, lecturerID, userBirthDay, faculty, degree, userEmail, userPhoneNumber;
    private ImageView editBtn;
    private CircleImageView userAvatar;
    private ImageButton logout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_for_lecturer);
        initViews();

        logout.setOnClickListener(v -> {
            Log.d("LOGOUT: ", "TRUE");
            Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
            retrofit.create(UserService.class).logout()
                    .enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            Log.d("LOGOUT_RES: ", response.message());
                            SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
                            useSharedPreferences.edit().clear().apply();
                            startActivity(new Intent(UserProfileActivityForLecturer.this, LoginActivity.class));
                        }

                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {
                            Log.d("LOGOUT_ERR: ", t.getMessage());
                        }
                    });
        });
    }

    private void initViews() {
        userFullName = findViewById(R.id.userFullName);
        lecturerID = findViewById(R.id.lecturerID);
        userBirthDay = findViewById(R.id.userBirthDay);
        faculty = findViewById(R.id.faculty);
        degree = findViewById(R.id.degree);
        userEmail = findViewById(R.id.userEmail);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        userAvatar = findViewById(R.id.userAvatar);
        editBtn = findViewById(R.id.editProfileBtn);
        logout = findViewById(R.id.icon_logout);

        setUpUserInfo();
    }

    private void setUpUserInfo(/*UserModel usermodel*/) {

        SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        if (useSharedPreferences.getString("role", "").equals("2")) {
            userFullName.setText(useSharedPreferences.getString("firstName", "") + " " + useSharedPreferences.getString("lastName", ""));
            lecturerID.setText(useSharedPreferences.getString("user_id", ""));
            userBirthDay.setText(Helper.convertDate(useSharedPreferences.getString("dateOfBirth", "")));
            faculty.setText(useSharedPreferences.getString("faculty", ""));
            degree.setText(useSharedPreferences.getString("degree", ""));
            userEmail.setText(useSharedPreferences.getString("email", ""));
            userPhoneNumber.setText(useSharedPreferences.getString("phoneNumber", ""));
            Glide.with(this).load(useSharedPreferences.getString("avatar", "")).centerCrop().into(userAvatar);
            editBtn.setOnClickListener(v -> {
                startActivity(new Intent(UserProfileActivityForLecturer.this, EditProfileActivityForLecturer.class));
            });
        }
        else {
            Intent intent = getIntent();
            userFullName.setText(intent.getStringExtra("name"));
            lecturerID.setText(intent.getStringExtra("lecturer_id"));
            userBirthDay.setText(intent.getStringExtra("birth"));
            faculty.setText(intent.getStringExtra("faculty"));
            degree.setText(intent.getStringExtra("degree"));
            userEmail.setText(intent.getStringExtra("email"));
            userPhoneNumber.setText(intent.getStringExtra("phone"));
            Glide.with(this).load(intent.getStringExtra("avatar")).centerCrop().into(userAvatar);
            editBtn.setImageResource(R.drawable.ic_chat);
            String lecturer_id = intent.getStringExtra("lecturer_id");
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserProfileActivityForLecturer.this, ConversationActivity.class);
                    intent.putExtra("room_id", 0);
                    Log.d("id", lecturer_id);
                    intent.putExtra("user_2", lecturer_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            logout.setVisibility(View.INVISIBLE);
        }
    }
}