package com.example.supconnect.LectureClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.supconnect.Fragments.ContactLecturerListFragment;
import com.example.supconnect.Fragments.HomeFragmentLecturer;
import com.example.supconnect.Fragments.ManageClassFragment;
import com.example.supconnect.Fragments.NotificationFragment;
import com.example.supconnect.Fragments.ScheduleFragment;
import com.example.supconnect.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivityLecturer extends AppCompatActivity {
    FloatingActionButton fab;

    private static final int FRAGMENT_HOME= 1;
    private static final int FRAGMENT_SCHEDULES= 2;
    private static final int FRAGMENT_MANAGE_CLASS= 3;
    private static final int FRAGMENT_NOTIFICATION= 4;
   private static final int FRAGMENT_CONTACTS_LECTURER= 5;
    private int currentFragment = FRAGMENT_HOME;

    private BottomNavigationView bottomNavigationView;
    private ImageButton userProfileBtn;
    private DrawerLayout drawer;
    private ImageButton close, logout;
    private LinearLayout header;
    private CardView info, contact, lecturer, studyresult;
    private ImageView userava;
    private TextView user_name, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lecturer);
        initViews();

        replaceFragment(new HomeFragmentLecturer());
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        fab.setSelected(true);

        /*bottom nav onClickListener*/

        fab.setOnClickListener(v -> {
            boolean isSelected = !v.isSelected();
            if(isSelected){
            openHomeFragment();
            header.setVisibility(View.VISIBLE);
            for (int i = 0; i<4; i++){
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
                case R.id.menu_class_management:
                    openManageFragment();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.GONE);
                    return true;
                case R.id.menu_notifications:
                    openNotificationFragment();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.VISIBLE);
                    return true;
                case R.id.menu_contacts_lecturer:
                    openLecturerContacts();
                    fab.setSelected(false);
                    item.setCheckable(true);
                    header.setVisibility(View.GONE);

                    return true;
                default:
                    return false;
            }
        });

        userProfileBtn.setOnClickListener(v->{
            startActivity(new Intent(this, UserProfileActivityForLecturer.class));
        });
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
    }


    private void openHomeFragment(){
        if(currentFragment!=FRAGMENT_HOME){
            replaceFragment(new HomeFragmentLecturer());
            currentFragment = FRAGMENT_HOME;
        }
    }
    private void openScheduleFragment(){
        if(currentFragment!=FRAGMENT_SCHEDULES){
            replaceFragment(new ScheduleFragment());
            currentFragment = FRAGMENT_SCHEDULES;
        }
    }

    private void openManageFragment(){
        if(currentFragment!=FRAGMENT_MANAGE_CLASS){
           replaceFragment(new ManageClassFragment());
            currentFragment = FRAGMENT_MANAGE_CLASS;
        }
    }
    private void openNotificationFragment(){
        if(currentFragment!=FRAGMENT_NOTIFICATION){
            replaceFragment(new NotificationFragment());
            currentFragment = FRAGMENT_NOTIFICATION;
        }
    }

    private void openLecturerContacts(){
        if(currentFragment!=FRAGMENT_CONTACTS_LECTURER){
            replaceFragment(new ContactLecturerListFragment());
            currentFragment = FRAGMENT_CONTACTS_LECTURER;
        }
    }
    private void replaceFragment( Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

}