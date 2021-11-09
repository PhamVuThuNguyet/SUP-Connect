package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.supconnect.LectureClient.UserProfileActivityForLecturer;
import com.example.supconnect.R;
import com.example.supconnect.model.LecturerOfClass;

import java.util.List;

public class LecturerPagerAdapter extends PagerAdapter {
    Context cont;
    List<LecturerOfClass> List;

    public LecturerPagerAdapter(Context cont, java.util.List<LecturerOfClass> lectureList) {
        this.cont = cont;
        List = lectureList;
    }

    @Override
    public int getCount() {
        return List.size()/2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(cont).inflate(R.layout.lecturer_of_class_list,null);


        ImageView imgLecture = view.findViewById(R.id.img_lectureofclass);
        TextView nameLecture = view.findViewById(R.id.name_lectureofclass);
        TextView emailLecture = view.findViewById(R.id.mail_lectureofclass);
        ImageView imgLecture1 = view.findViewById(R.id.img_lectureofclass2);
        TextView nameLecture1 = view.findViewById(R.id.name_lectureofclass2);
        TextView emailLecture1 = view.findViewById(R.id.mail_lectureofclass2);
        CardView lecturer1 = view.findViewById(R.id.lecturer1);
        CardView lecturer2 = view.findViewById(R.id.lecturer2);

        nameLecture.setText(List.get(position*2).getLecturer_name());
        emailLecture.setText(List.get(position*2).getLecturer_email());
        nameLecture1.setText(List.get(position*2+1).getLecturer_name());
        emailLecture1.setText(List.get(position*2+1).getLecturer_email());
        Glide.with(cont).load(List.get(position*2).getLecturer_avatar()).centerCrop().centerInside().fitCenter().into(imgLecture);
        Glide.with(cont).load(List.get(position*2+1).getLecturer_avatar()).centerCrop().centerInside().fitCenter().into(imgLecture1);
        lecturer1.setOnClickListener(v -> {
            Intent intent = new Intent(cont, UserProfileActivityForLecturer.class);
            intent.putExtra("name", List.get(position*2).getLecturer_name());
            intent.putExtra("lecturer_id", List.get(position*2).getLecturer_id());
            intent.putExtra("avatar", List.get(position*2).getLecturer_avatar());
            intent.putExtra("faculty", List.get(position*2).getFaculty());
            intent.putExtra("degree", List.get(position*2).getDegree());
            intent.putExtra("birth", List.get(position*2).getDateOfBirth());
            intent.putExtra("email", List.get(position*2).getLecturer_email());
            intent.putExtra("phone", List.get(position*2).getPhoneNumberLecturer());
            cont.startActivity(intent);
        });
        lecturer2.setOnClickListener(v -> {
            Intent intent = new Intent(cont, UserProfileActivityForLecturer.class);
            intent.putExtra("name", List.get(position*2 + 1).getLecturer_name());
            intent.putExtra("lecturer_id", List.get(position*2 + 1).getLecturer_id());
            intent.putExtra("avatar", List.get(position*2 + 1).getLecturer_avatar());
            intent.putExtra("faculty", List.get(position*2 + 1).getFaculty());
            intent.putExtra("degree", List.get(position*2 + 1).getDegree());
            intent.putExtra("birth", List.get(position*2 + 1).getDateOfBirth());
            intent.putExtra("email", List.get(position*2 + 1).getLecturer_email());
            intent.putExtra("phone", List.get(position*2 + 1).getPhoneNumberLecturer());
            cont.startActivity(intent);
        });
        container.addView(view);
        return view;
    }
}
