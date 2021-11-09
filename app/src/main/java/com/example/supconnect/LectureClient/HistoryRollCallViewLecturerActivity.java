package com.example.supconnect.LectureClient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.Fragments.ListStudentToInputScoreFragment;
import com.example.supconnect.Fragments.RollCallFragment;
import com.example.supconnect.R;
import com.example.supconnect.adapter.RollCallViewLecturerAdapter;
import com.example.supconnect.model.RollCallViewLecturer;

import java.util.ArrayList;

public class HistoryRollCallViewLecturerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rc_viewStudent;
    RollCallViewLecturerAdapter adapterStudent;
    ArrayList<RollCallViewLecturer> listStudent;
    TextView nameClass;
    RelativeLayout layout;
    private Spinner spinner;
    private ImageButton backBtn;
    private static final int FRAGMENT_ROLLUP = 1;
    private static final int FRAGMENT_ENTRYPOINT = 2;
    private  int currentFragment = FRAGMENT_ROLLUP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_roll_call_view_lecturer);
        nameClass = findViewById(R.id.txt_manageClass);
        spinner = findViewById(R.id.spinner_function);
        layout = findViewById(R.id.function_linear);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //spinner
        ArrayAdapter<String> adapterFunction = new ArrayAdapter<>(this,R.layout.custom_spinner,getResources().getStringArray(R.array.function));
        adapterFunction.setDropDownViewResource(R.layout.item_dropdown);
        spinner.setAdapter(adapterFunction);
        spinner.setOnItemSelectedListener(this);
        nameClass.setText(getIntent().getStringExtra("className"));
        Bundle bundle = new Bundle();
        bundle.putString("classID", getIntent().getStringExtra("classID"));
        RollCallFragment fragment = new RollCallFragment();
        fragment.setArguments(bundle);
        ReplaceFragment(fragment);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = spinner.getItemAtPosition(position).toString();
            if (selected.equals("Điểm danh")){
               openRollUpFragment();
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();

                layoutParams.setMargins(0, 20, 0, 0);

                layout.setLayoutParams(layoutParams);
            }
            else {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();

                layoutParams.setMargins(0, 20, 0, 0);

                layout.setLayoutParams(layoutParams);

                openEntryPointFragment();
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void openEntryPointFragment(){
        if(currentFragment!=FRAGMENT_ENTRYPOINT){
            ListStudentToInputScoreFragment fragment = new ListStudentToInputScoreFragment();
            Bundle bundle = new Bundle();
            bundle.putString("classID", getIntent().getStringExtra("classID"));
            fragment.setArguments(bundle);
            ReplaceFragment(fragment);
            currentFragment = FRAGMENT_ENTRYPOINT;
        }
    }

    private void openRollUpFragment(){
        if(currentFragment!=FRAGMENT_ROLLUP){
            Bundle bundle = new Bundle();
            bundle.putString("classID", getIntent().getStringExtra("classID"));
            RollCallFragment fragment = new RollCallFragment();
            fragment.setArguments(bundle);
            ReplaceFragment(fragment);
            currentFragment = FRAGMENT_ROLLUP;
        }
    }
    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.function_fragment,fragment);
        transaction.commit();
    }
}
