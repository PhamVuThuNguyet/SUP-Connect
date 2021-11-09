package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.LectureClient.HistoryRollCallViewLecturerActivity;
import com.example.supconnect.R;
import com.example.supconnect.model.ClassOfThisTeacher;
import com.example.supconnect.model.SubClassofThisTeacher;

import java.util.ArrayList;
import java.util.List;

public class SubClassOfThisLecturerApdapter extends RecyclerView.Adapter<SubClassOfThisLecturerApdapter.ViewHolder> {
    Context cont;
    java.util.List<SubClassofThisTeacher> List;

    public SubClassOfThisLecturerApdapter(Context cont, List<SubClassofThisTeacher> list) {
        this.cont = cont;
        List = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(cont).inflate(R.layout.class_of_this_lecturer_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameClassSection.setText(List.get(position).getSubject_class_name());
        holder.ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cont, HistoryRollCallViewLecturerActivity.class);
                intent.putExtra("className",List.get(position).getSubject_class_name());
                intent.putExtra("classID",List.get(position).getSubject_class_id());
                cont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameClassSection;
        ImageView ic_next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameClassSection = itemView.findViewById(R.id.name_classSection);
            ic_next = itemView.findViewById(R.id.icon_next);
        }
    }
}
