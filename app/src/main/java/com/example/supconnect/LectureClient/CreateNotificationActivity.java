package com.example.supconnect.LectureClient;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.supconnect.Fragments.CreateAllNotificationFragment;
import com.example.supconnect.Fragments.CreateAssignmentNotificationFragment;
import com.example.supconnect.Fragments.CreateLeaveNotificationFragment;
import com.example.supconnect.R;

public class CreateNotificationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final int FRAGMENT_ALL_NOTIFICATION = 1;
    private static final int FRAGMENT_ASSIGNMENT_NOTIFICATION = 2;
    private static final int FRAGMENT_LEAVE_NOTIFICATION = 3;
    private  int currentFragment = FRAGMENT_ALL_NOTIFICATION;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification_lecture);
        spinner = findViewById(R.id.spinner_function);
        ArrayAdapter<String> adapterFunction = new ArrayAdapter<>(this,R.layout.custom_spinner,getResources().getStringArray(R.array.notification));
        adapterFunction.setDropDownViewResource(R.layout.item_dropdown);
        spinner.setAdapter(adapterFunction);
        spinner.setOnItemSelectedListener(this);
        ReplaceFragment(new CreateAllNotificationFragment());
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = spinner.getItemAtPosition(position).toString();
        if (selected.equals("Thông báo chung")){
            openAllNotificationFragment();
        }
        else if (selected.equals("Báo nghỉ")){
            openLeaveNotificationFragment();
        }
        else {
            openAssignmentNotificationFragment();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void openAssignmentNotificationFragment(){
        if(currentFragment!=FRAGMENT_ASSIGNMENT_NOTIFICATION){
            ReplaceFragment(new CreateAssignmentNotificationFragment());
            currentFragment = FRAGMENT_ASSIGNMENT_NOTIFICATION;
        }
    }
    private void openLeaveNotificationFragment(){
        if(currentFragment!=FRAGMENT_LEAVE_NOTIFICATION){
            ReplaceFragment(new CreateLeaveNotificationFragment());
            currentFragment = FRAGMENT_LEAVE_NOTIFICATION;
        }
    }
    private void openAllNotificationFragment(){
        if(currentFragment!=FRAGMENT_ALL_NOTIFICATION){
            ReplaceFragment(new CreateAllNotificationFragment());
            currentFragment = FRAGMENT_ALL_NOTIFICATION;
        }
    }
    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.notification_fragment,fragment);
        transaction.commit();
    }
}
