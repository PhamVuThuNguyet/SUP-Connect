package com.example.supconnect.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.supconnect.LectureClient.CreateNotificationActivity;
import com.example.supconnect.R;
import com.example.supconnect.adapter.FragmentAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private  View view;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout header;
    private ArrayList<Fragment> fragments;
    private FloatingActionButton addNoti;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.notification_fragment, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        appBar = view.findViewById(R.id.appbar);
        header = view.findViewById(R.id.header_notification);
        viewPager = view.findViewById(R.id.viewPager);
        header.setVisibility(View.GONE);

        fragments = new ArrayList<>();
        fragments.add(new AnnouncementFragment());
        fragments.add(new LeaveNoticeFragment());
        fragments.add(new AssignmentFragment());
        FragmentAdapter pagerAdapter = new FragmentAdapter(this.getChildFragmentManager(), getContext().getApplicationContext(), fragments);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Thông báo chung");
        tabLayout.getTabAt(1).setText("     Báo nghỉ/Học bù     ");
        tabLayout.getTabAt(2).setText("     Bài tập      ");
        reduceMarginsInTabs(tabLayout, 20);

        addNoti = view.findViewById(R.id.addAnnouncementBtn);
        SharedPreferences useSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        if(useSharedPreferences.getString("role","").equals("2")){
            addNoti.setVisibility(View.VISIBLE);
        }
        addNoti.setOnClickListener(v->{
            startActivity(new Intent(getContext(), CreateNotificationActivity.class));
        });
        return view;
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
