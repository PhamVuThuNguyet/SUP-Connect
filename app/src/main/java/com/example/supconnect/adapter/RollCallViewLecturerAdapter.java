package com.example.supconnect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supconnect.API.LecturerService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.RollCallResponse;
import com.example.supconnect.model.HistoryRollcallStudent;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RollCallViewLecturerAdapter extends RecyclerView.Adapter<RollCallViewLecturerAdapter.ViewHolder> {
    ArrayList<HistoryRollcallStudent> rollCallViewLecturers;
    Context cont;
    String subject_class;

    public RollCallViewLecturerAdapter(ArrayList<HistoryRollcallStudent> rollCallViewLecturers, String subject_class, Context cont) {
        this.rollCallViewLecturers = rollCallViewLecturers;
        this.subject_class = subject_class;
        this.cont = cont;
    }

    @NonNull
    @Override
    public RollCallViewLecturerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RollCallViewLecturerAdapter.ViewHolder(LayoutInflater.from(cont).inflate(R.layout.student_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RollCallViewLecturerAdapter.ViewHolder holder, int position) {
        HistoryRollcallStudent student = rollCallViewLecturers.get(position);
        String student_name = student.getFirstName() + " " + student.getLastName();
        holder.txtStudent.setText(student_name);
        holder.txtClass.setText(student.getClassName());
        Glide.with(cont).load(student.getAvatar()).centerCrop().centerInside().fitCenter().into(holder.ava);
        if (student.getIsAttend() == 1) {
            holder.ic_check.setImageResource(R.drawable.ic_check);
        } else holder.ic_check.setImageResource(R.drawable.ic_not);

        holder.ic_check.setOnClickListener(v -> {
            Retrofit retrofit = new RetrofitClient().getRetrofit(cont);
            retrofit.create(LecturerService.class).changeAttendStatus(subject_class, "")
                    .enqueue(new Callback<RollCallResponse>() {
                        @Override
                        public void onResponse(Call<RollCallResponse> call, Response<RollCallResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Log.d("Change attend status:" + student.getRecordDetailId(),
                                        response.body().getRecord().getIsAttend() == 1 ?
                                                "not attended => is attended" :
                                                "is attended => not attended");
                                if (response.body().getRecord().getIsAttend() == 0) {
                                    holder.ic_check.setImageResource(R.drawable.ic_not);
                                } else holder.ic_check.setImageResource(R.drawable.ic_check);
                            }
                        }
                        @Override
                        public void onFailure(Call<RollCallResponse> call, Throwable t) {
                            Log.d("Change attend status", t.getMessage());
                        }
                    });
        });
        holder.ic_check.setOnClickListener(v -> {
            if (student.getIsAttend() == 1) {
                holder.ic_check.setImageResource(R.drawable.ic_not);

            } else holder.ic_check.setImageResource(R.drawable.ic_check);
        });
    }

    @Override
    public int getItemCount() {
        return rollCallViewLecturers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ic_check;
        TextView txtStudent, txtClass;
        CircleImageView ava;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ic_check = (ImageView) itemView.findViewById(R.id.icon_checkRollup);
            txtStudent = (TextView) itemView.findViewById(R.id.name_student);
            txtClass = (TextView) itemView.findViewById(R.id.name_class);
            ava = itemView.findViewById(R.id.avatar);
        }
    }
}
