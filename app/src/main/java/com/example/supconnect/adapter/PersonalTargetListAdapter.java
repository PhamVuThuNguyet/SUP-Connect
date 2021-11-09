package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.TargetDetailActivity;
import com.example.supconnect.model.PersonalTargetItem;

import java.util.List;

public class PersonalTargetListAdapter extends RecyclerView.Adapter<PersonalTargetListAdapter.Holder> {
    private Context context;
    private List<PersonalTargetItem> personalTargetItems;

    public PersonalTargetListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PersonalTargetListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_target_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalTargetListAdapter.Holder holder, int position) {
        PersonalTargetItem item = personalTargetItems.get(position);
        String targetGrade = item.getGradeTarget() + " điểm";
        int targetProgress = (int) Math.round((float) (item.getCurrentGrade() / item.getGradeTarget()) * 100);

        holder.targetSubject.setText(item.getSubjectClassId());
        holder.targetGrade.setText(targetGrade);
        holder.targetProgressTxt.setText(String.format("%s%%", String.valueOf(targetProgress)));
        holder.targetProgress.setProgress(targetProgress);
        holder.targetIcon.setImageResource(R.drawable.calculus);
//        switch (item.getSubjectID()) {
//            case 1:
//
//                break;
//            case 2:
//                holder.targetIcon.setImageResource(R.drawable.artificial_intelligence);
//                break;
//            case 3:
//                holder.targetIcon.setImageResource(R.drawable.statistics);
//        }
        holder.targetitem.setOnClickListener(v -> {
            Intent intent = new Intent(context, TargetDetailActivity.class);
            intent.putExtra("subject_class_id", item.getSubjectClassId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return personalTargetItems.size();
    }

    public void setPersonalTargetItems(List<PersonalTargetItem> list) {
        personalTargetItems = list;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ImageView targetIcon;
        private TextView targetSubject, targetGrade, targetProgressTxt;
        private ProgressBar targetProgress;
        private CardView targetitem;

        public Holder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            targetIcon = itemView.findViewById(R.id.targetIcon);
            targetSubject = itemView.findViewById(R.id.targetSubject);
            targetGrade = itemView.findViewById(R.id.targetGrade);
            targetProgressTxt = itemView.findViewById(R.id.targetProgressTxt);
            targetProgress = itemView.findViewById(R.id.targetProgressBar);
            targetitem = itemView.findViewById(R.id.targetitem);
        }
    }
}
