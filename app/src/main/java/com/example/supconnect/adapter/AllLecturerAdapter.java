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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supconnect.R;
import com.example.supconnect.LectureClient.UserProfileActivityForLecturer;
import com.example.supconnect.model.Lecturer;
import com.example.supconnect.model.LecturerOfClass;

import java.util.ArrayList;
import java.util.List;

public class AllLecturerAdapter extends RecyclerView.Adapter<AllLecturerAdapter.ViewHolder> {
    Context cont;
    java.util.List<Lecturer> List;

    public AllLecturerAdapter(Context cont, List<Lecturer> list) {
        this.cont = cont;
        List = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(cont).inflate(R.layout.all_lecturer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameLecture.setText(List.get(position).getNameWithDegree());
        Glide.with(cont).load(List.get(position).getAvatar()).
                centerCrop()
                .into(holder.imgAllLecture);
        holder.alllectureritem.setOnClickListener(v-> {
            Intent intent = new Intent(cont, UserProfileActivityForLecturer.class);
            intent.putExtra("name", List.get(position).getNameWithDegree());
            intent.putExtra("lecturer_id", List.get(position).getLecturerId());
            intent.putExtra("avatar", List.get(position).getAvatar());
            intent.putExtra("faculty", List.get(position).getFaculty());
            intent.putExtra("degree", List.get(position).getDegree_name());
            intent.putExtra("birth", List.get(position).getDateOfBirth());
            intent.putExtra("email", List.get(position).getEmail());
            intent.putExtra("phone", List.get(position).getPhoneNumberLecturer());
            cont.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAllLecture;
        TextView nameLecture;
        CardView alllectureritem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAllLecture = (ImageView) itemView.findViewById(R.id.img_lectureall);
            nameLecture = (TextView) itemView.findViewById(R.id.name_lectureall);
            alllectureritem = itemView.findViewById(R.id.alllectureritem);
        }
    }
}
