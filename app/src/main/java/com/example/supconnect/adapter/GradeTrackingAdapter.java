package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.ConversationActivity;
import com.example.supconnect.HistoryRollCallViewStudentActivity;
import com.example.supconnect.R;
import com.example.supconnect.TargetDetailActivity;
import com.example.supconnect.model.SubjectResult;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class GradeTrackingAdapter extends RecyclerView.Adapter<GradeTrackingAdapter.ViewHolder> {
    Context context;
    ArrayList<SubjectResult> List;
    TabLayout.Tab tab;

    public GradeTrackingAdapter(Context context, ArrayList<SubjectResult> list) {
        this.context = context;
        List = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.grade_tracking_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectResult subjectResult = List.get(position);
        holder.nameSubject.setText(subjectResult.getNameSubject());
        holder.number_credit.setText(String.valueOf(subjectResult.getNumber_credit()));
        holder.point_rollup.setText(String.valueOf(subjectResult.getPoint_Rollup()));
        holder.point_assign.setText(String.valueOf(subjectResult.getPoint_Assign()));
        holder.point_midterm.setText(String.valueOf(subjectResult.getPoint_midTerm()));
        holder.point_endterm.setText(String.valueOf(subjectResult.getPoint_EndTerm()));
        holder.point_10.setText(String.valueOf(subjectResult.getPoint_10()));
        holder.point_word.setText(subjectResult.getPoint_word());
        holder.lecturer_id = subjectResult.getLecturer_id();
        holder.nameSubject.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ShowMenu(holder);
            }

        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameSubject;
        TextView number_credit;
        TextView point_rollup;
        TextView point_assign;
        TextView point_midterm;
        TextView point_endterm;
        TextView point_10;
        TextView point_word;
        String lecturer_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameSubject = (TextView) itemView.findViewById(R.id.name_subject);
            number_credit = (TextView) itemView.findViewById(R.id.number_credits);
            point_rollup = (TextView) itemView.findViewById(R.id.point_rollUp);
            point_assign = (TextView) itemView.findViewById(R.id.point_assignment);
            point_midterm = (TextView) itemView.findViewById(R.id.point_midterm);
            point_endterm = (TextView) itemView.findViewById(R.id.point_endofterm);
            point_10 = (TextView) itemView.findViewById(R.id.point_10);
            point_word = (TextView) itemView.findViewById(R.id.point_word);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ShowMenu(ViewHolder holder) {
        androidx.appcompat.widget.PopupMenu popupMenu = new androidx.appcompat.widget.PopupMenu(context, holder.nameSubject);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.contact_lecturer:
                    Intent intent = new Intent(context, ConversationActivity.class);
                    intent.putExtra("room_id", 0);
                    intent.putExtra("user_2", holder.lecturer_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                case R.id.target:
                    context.startActivity(new Intent(context, TargetDetailActivity.class));
                    return true;
                case R.id.rollcall:
                    Intent intent_rollcall = new Intent(context, HistoryRollCallViewStudentActivity.class);
                    intent_rollcall.putExtra("subject_class", holder.nameSubject.getText());
                    intent_rollcall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent_rollcall);
                    return true;
            }

            return false;
        });
    }
}

