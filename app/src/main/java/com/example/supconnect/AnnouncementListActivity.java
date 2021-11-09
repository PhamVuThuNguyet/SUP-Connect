package com.example.supconnect;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.supconnect.Fragments.AnnouncementFragment;
import com.example.supconnect.Fragments.LeaveNoticeFragment;
import com.example.supconnect.adapter.FragmentAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AnnouncementListActivity extends AppCompatActivity {
    AppBarLayout appBar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        appBar = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        fragments.add(new AnnouncementFragment());
        fragments.add(new LeaveNoticeFragment());
        fragments.add(new AnnouncementFragment());
        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Thông báo chung");
        tabLayout.getTabAt(1).setText("     Báo nghỉ     ");
        tabLayout.getTabAt(2).setText("     Bài tập      ");
        reduceMarginsInTabs(tabLayout, 20);
    }

    public void setTabItemSelected(TabLayout tab) {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        new AnnouncementFragment();
                        return;
                    case 2:
                        new LeaveNoticeFragment();
                        return;
                    case 3:
                        new AnnouncementFragment();
                        return;
                    default:
                        new AnnouncementFragment();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {

        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).topMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).bottomMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                }
            }

            tabLayout.requestLayout();
        }
    }
}
