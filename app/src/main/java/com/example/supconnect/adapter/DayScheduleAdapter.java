package com.example.supconnect.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.model.Lesson;

import java.util.List;

public class DayScheduleAdapter extends RecyclerView.Adapter<DayScheduleAdapter.ViewHolder> {
    private List<Lesson> lessons;
    private Context context;

    public DayScheduleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayScheduleAdapter.ViewHolder holder, int position) {
        Lesson l = lessons.get(position);
        int start_lesson = Integer.parseInt(String.valueOf(l.getLesson().charAt(0)));
        switch (start_lesson) {
            case 1: {
                holder.time.setText("07:30 AM");
                break;
            }

            case 2: {
                holder.time.setText("08:30 AM");
                break;
            }

            case 3: {
                holder.time.setText("09:30 AM");
                break;
            }

            case 4: {
                holder.time.setText("10:30 AM");
                break;
            }

            case 6: {
                holder.time.setText("01:00 PM");
                break;
            }
            case 7: {
                holder.time.setText("02:00 PM");
                break;
            }

            case 8: {
                holder.time.setText("03:00 PM");
                break;
            }

            case 9: {
                holder.time.setText("04:00 PM");
                break;
            }
            default:
                return;
        }
        SharedPreferences userSharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if(userSharedPreferences.getString("role", "").equals("1")) {
            holder.subject.setText(l.getSubjectName());
            holder.roomLocation.setText(l.getClassroom());
            holder.lecturerName.setText(l.getLecturer());
        } else if(userSharedPreferences.getString("role", "").equals("2")) {
            holder.subject.setText(l.getSubjectClassName());
            holder.roomLocation.setText(l.getClassroom());
            holder.lecturerName.setText("Tiết " + l.getLesson());
        }
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void setLessons(List<Lesson> list) {
        lessons = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time, subject, lecturerName, roomLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            time = itemView.findViewById(R.id.schedule_time);
            subject = itemView.findViewById(R.id.subject_name);
            lecturerName = itemView.findViewById(R.id.room_lecture);
            roomLocation = itemView.findViewById(R.id.room_location);
        }
    }
}
