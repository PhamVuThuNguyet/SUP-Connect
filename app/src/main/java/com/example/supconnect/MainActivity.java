package com.example.supconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.UserService;
import com.example.supconnect.Fragments.HomeFragment;
import com.example.supconnect.Fragments.NotificationFragment;
import com.example.supconnect.Fragments.ScheduleFragment;
import com.example.supconnect.Fragments.TargetFragment;
import com.example.supconnect.Fragments.WalletFragment;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;

    private static final int FRAGMENT_HOME= 1;
    private static final int FRAGMENT_SCHEDULES= 2;
    private static final int FRAGMENT_TARGET= 3;
    private static final int FRAGMENT_NOTIFICATION= 4;
    private static final int FRAGMENT_WALLET= 5;
    private int currentFragment = FRAGMENT_HOME;

    private BottomNavigationView bottomNavigationView;
    private ImageButton userProfileBtn;
    private DrawerLayout drawer;
    private ImageButton close, logout;
    private LinearLayout header;
    private CardView info, contact, lecturer, studyresult, findotherusers;
    private ImageView userava;
    private TextView user_name, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navigation);
        initViews();

        replaceFragment(new HomeFragment());
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        fab.setSelected(true);

        /*bottom nav onClickListener*/

        fab.setOnClickListener(v -> {
            boolean isSelected = !v.isSelected();
            if (isSelected) {
                openHomeFragment();
                header.setVisibility(View.VISIBLE);
                for (int i = 0; i < 4; i++) {
                    bottomNavigationView.getMenu().getItem(i).setCheckable(false);
                }
            }

        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_schedules:
                    openScheduleFragment();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.GONE);
                    return true;
                case R.id.menu_wallet:
                   openWalletFragment();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.VISIBLE);
                    return true;
                case R.id.menu_notifications:
                    openNotificationFragment();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.VISIBLE);
                    return true;
                case R.id.menu_targets:
                    openTargetFragment();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.GONE);
                    return true;
                default:
                    return false;
            }
        });

        setOnClickForNavigationDrawer();
        setOnClickForMenu();

        /*Set User Info*/
        setUserInfo();

    }

    private void initViews() {
        header = findViewById(R.id.header);
        fab = findViewById(R.id.menu_home);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        userProfileBtn = findViewById(R.id.user_profile);
        drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        close = findViewById(R.id.close);
        logout = findViewById(R.id.icon_logout);
        info = findViewById(R.id.info);
        contact = findViewById(R.id.contact);
        lecturer = findViewById(R.id.lecturer);
        studyresult = findViewById(R.id.study_result);
        userava = findViewById(R.id.profile_image1);
        user_name = findViewById(R.id.user_name);
        user_id = findViewById(R.id.user_id);
        findotherusers = findViewById(R.id.find_user);
    }


    private void openHomeFragment(){
        if(currentFragment!=FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            currentFragment = FRAGMENT_HOME;
        }
    }
    private void openScheduleFragment(){
        if(currentFragment!=FRAGMENT_SCHEDULES){
            replaceFragment(new ScheduleFragment());
            currentFragment = FRAGMENT_SCHEDULES;
        }
    }
    private void openTargetFragment(){
        if(currentFragment!=FRAGMENT_TARGET){
            replaceFragment(new TargetFragment());
            currentFragment = FRAGMENT_TARGET;
        }
    }
    private void openNotificationFragment(){
        if(currentFragment!=FRAGMENT_NOTIFICATION){
            replaceFragment(new NotificationFragment());
            currentFragment = FRAGMENT_NOTIFICATION;
        }
    }
    private void openWalletFragment(){
        if(currentFragment!=FRAGMENT_WALLET){
            replaceFragment(new WalletFragment(this));
            currentFragment = FRAGMENT_WALLET;
        }
    }
    private void replaceFragment( Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }


    private void setOnClickForMenu(){

        info.setOnClickListener(v->{
            startActivity(new Intent(this, UserProfileActivity.class));
        });

        contact.setOnClickListener(v->{
            startActivity(new Intent(this, ContactListActivity.class));
        });

        lecturer.setOnClickListener(v-> {
            startActivity(new Intent(this, LectureListActivity.class));
        });

        studyresult.setOnClickListener(v-> {
            startActivity(new Intent(this, GradeTrackingActivity.class));
        });

        findotherusers.setOnClickListener(v-> {
            startActivity(new Intent(this, FindOtherUsers.class));
        });
        logout.setOnClickListener(v-> {
            Log.d("LOGOUT: ", "TRUE");
            Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
            retrofit.create(UserService.class).logout()
                    .enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            Log.d("LOGOUT_RES: ", response.message());
                            SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
                            useSharedPreferences.edit().clear().apply();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }

                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {
                            Log.d("LOGOUT_ERR: ", t.getMessage());
                        }
                    });
        });
    }
   private void setOnClickForNavigationDrawer(){

       userProfileBtn.setOnClickListener(v -> {
           drawer.openDrawer(GravityCompat.START);
        });

        close.setOnClickListener(v-> {
            drawer.closeDrawer(GravityCompat.START);
        });

    }

    private void setUserInfo(){
        SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Picasso.get()
                .load(useSharedPreferences.getString("avatar", ""))
                .resize(140, 140)
                .centerCrop()
                .into(userava);

        user_name.setText(useSharedPreferences.getString("firstName", "") + " " + useSharedPreferences.getString("lastName", ""));
        user_id.setText(useSharedPreferences.getString("user_id", ""));
    }

}