package com.example.supconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.model.RollCallViewStudent;

import java.util.List;

public class RollCallViewStudentAdapter extends RecyclerView.Adapter<RollCallViewStudentAdapter.ViewHolder> {
    Context cont;
    List<RollCallViewStudent> rollCallViewStudentList;

    public RollCallViewStudentAdapter(Context cont) {
        this.cont = cont;
    }

    public void setRecords(List<RollCallViewStudent> list) {
        rollCallViewStudentList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(cont).inflate(R.layout.history_rollcall_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtdate.setText(rollCallViewStudentList.get(position).getDate());
        holder.txtlesson.setText("Tiết " + rollCallViewStudentList.get(position).getLesson());
        if (rollCallViewStudentList.get(position).getIs_attend() == 1) {
            holder.ic_check.setImageResource(R.drawable.ic_tick_square);
        } else {
            holder.ic_check.setImageResource(R.drawable.ic_close_square);
        }

    }

    @Override
    public int getItemCount() {
        return rollCallViewStudentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ic_check;
        TextView txtdate, txtlesson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ic_check = (ImageView) itemView.findViewById(R.id.icon_check);
            txtdate = (TextView) itemView.findViewById(R.id.date_subject);
            txtlesson = (TextView) itemView.findViewById(R.id.lesson_subject);

        }
    }
}
