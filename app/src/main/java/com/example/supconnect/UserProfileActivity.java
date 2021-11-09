package com.example.supconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.supconnect.Utils.Helper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userFullName, userStudentID, userBirthDay, userMainClass, userPeriod, userEmail, userPhoneNumber;
    private ImageView editBtn;
    private CircleImageView userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initViews();

        editBtn.setOnClickListener(v -> {
            startActivity(new Intent(UserProfileActivity.this, EditProfileActivity.class));
        });
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
        editBtn = findViewById(R.id.editProfileBtn);

        setUpUserInfo();
    }

    private void setUpUserInfo(/*UserModel usermodel*/) {

        SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        userFullName.setText(useSharedPreferences.getString("firstName", "") + " " + useSharedPreferences.getString("lastName", ""));
        userStudentID.setText(useSharedPreferences.getString("user_id", ""));
        userBirthDay.setText(Helper.convertDate(useSharedPreferences.getString("dateOfBirth", "")));
        userMainClass.setText(useSharedPreferences.getString("class", ""));
        userPeriod.setText(useSharedPreferences.getString("startYear", "") + "-" + useSharedPreferences.getString("endYear", ""));
        userEmail.setText(useSharedPreferences.getString("email", ""));
        userPhoneNumber.setText(useSharedPreferences.getString("phoneNumber", ""));
        Picasso.get().load(useSharedPreferences.getString("avatar", "")).resize(100, 100).centerCrop().into(userAvatar);

    }
}