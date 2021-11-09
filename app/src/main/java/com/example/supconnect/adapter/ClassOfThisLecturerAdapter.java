package com.example.supconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.model.ClassOfThisTeacher;
import com.example.supconnect.model.SubClassofThisTeacher;

import java.util.ArrayList;
import java.util.List;

public class ClassOfThisLecturerAdapter extends RecyclerView.Adapter<ClassOfThisLecturerAdapter.ViewHolder> {
    Context cont;
    List<ClassOfThisTeacher> List;
    List<SubClassofThisTeacher> subClassofThisTeacherArrayList = new ArrayList<>();
    private static int currentPosition = -1;

    public ClassOfThisLecturerAdapter(Context cont, List<ClassOfThisTeacher> list) {
        this.cont = cont;
        List = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(cont).inflate(R.layout.manage_class_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameClass.setText(List.get(position).getSubject_name());
        setClassSectionRecycler(holder.rc_classSection, List.get(position).getSubject_classes());
        holder.expanded.setVisibility(View.GONE);
        if (currentPosition == position) {
            Animation slideDown = AnimationUtils.loadAnimation(cont, R.anim.drop_down);
            holder.expanded.setVisibility(View.VISIBLE);
            holder.expanded.startAnimation(slideDown);
        }
        holder.ic_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelectedAfterClick = !v.isSelected();
                v.setSelected(isSelectedAfterClick);
                holder.card_view.setSelected(isSelectedAfterClick);
                holder.nameClass.setSelected(isSelectedAfterClick);
                if (isSelectedAfterClick){
                    getPosition(position);
                } else {
                    if(currentPosition == position){
                        Animation slideDown = AnimationUtils.loadAnimation(cont,R.anim.drop_up);
                        holder.expanded.setVisibility(View.GONE);
                        holder.expanded.startAnimation(slideDown);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameClass;
        RelativeLayout expanded,card_view;
        RecyclerView rc_classSection;
        ImageView ic_dropdown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameClass = (TextView) itemView.findViewById(R.id.class_name);
            card_view = itemView.findViewById(R.id.card_class);
            rc_classSection = (RecyclerView) itemView.findViewById(R.id.rc_viewClass);
            expanded = (RelativeLayout) itemView.findViewById(R.id.expanded_view);
            ic_dropdown = (ImageView) itemView.findViewById(R.id.icon_dropdown);
        }
    }

    private void setClassSectionRecycler(RecyclerView rc_view, java.util.List<SubClassofThisTeacher> subClassOfThisTeacher) {
        SubClassOfThisLecturerApdapter apdapter = new SubClassOfThisLecturerApdapter(cont, subClassOfThisTeacher);
        rc_view.setLayoutManager(new LinearLayoutManager(cont, LinearLayoutManager.VERTICAL, false));
        rc_view.setAdapter(apdapter);
    }

    public void getPosition(int position) {
        currentPosition = position;
        notifyDataSetChanged();
    }

    public void Animation() {

    }
}
