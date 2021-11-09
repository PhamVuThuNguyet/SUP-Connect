package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.LectureClient.InputScoreActivity;
import com.example.supconnect.R;
import com.example.supconnect.Utils.Constant;
import com.example.supconnect.model.RollCallViewLecturer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InputGradeAdater extends RecyclerView.Adapter<InputGradeAdater.ViewHolder> {
    ArrayList<RollCallViewLecturer> rollCallViewLecturers;
    Context cont;
    public InputGradeAdater(ArrayList<RollCallViewLecturer> rollCallViewLecturers, Context cont) {
        this.rollCallViewLecturers = rollCallViewLecturers;
        this.cont = cont;
    }

    @NonNull
    @Override
    public InputGradeAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InputGradeAdater.ViewHolder(LayoutInflater.from(cont).inflate(R.layout.student_to_input_score_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InputGradeAdater.ViewHolder holder, int position) {
        holder.txtStudent.setText(rollCallViewLecturers.get(position).getStudentName());
        holder.txtClass.setText(rollCallViewLecturers.get(position).getClassName());
        Picasso.get().load(rollCallViewLecturers.get(position).getAvatar()).resize(400, 400).centerCrop().into(holder.avatar);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cont, InputScoreActivity.class);
                intent.putExtra("studentID", rollCallViewLecturers.get(position).getStudentId());
                intent.putExtra("avatar", rollCallViewLecturers.get(position).getAvatar());
                intent.putExtra("studentName", rollCallViewLecturers.get(position).getStudentName());
                intent.putExtra("classID", rollCallViewLecturers.get(position).getSubject_class_id());
                intent.putExtra("className", rollCallViewLecturers.get(position).getClassName());
                cont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rollCallViewLecturers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtStudent, txtClass;
        CardView cardView;
        CircleImageView avatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStudent = (TextView) itemView.findViewById(R.id.txt_student_name);
            txtClass = (TextView) itemView.findViewById(R.id.txt_student_class);
            cardView = (CardView) itemView.findViewById(R.id.student_to_input_score_item);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
