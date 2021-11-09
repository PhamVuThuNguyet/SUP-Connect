package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.TargetDetailActivity;
import com.example.supconnect.model.PersonalTargetItem;
import com.example.supconnect.model.TargetScrollviewItem;

import java.util.List;

public class TargetScrollViewAdapter extends RecyclerView.Adapter<TargetScrollViewAdapter.Holder> {
    private List<PersonalTargetItem> itemList;
    private Context context;

    public TargetScrollViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TargetScrollViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.targets_scrollview_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetScrollViewAdapter.Holder holder, int position) {
        PersonalTargetItem item = itemList.get(position);
//        holder.targetIcon.setImageResource();
//        holder.targetSubject.setText(item.get);
        holder.targetSubject.setText(item.getSubjectClassId());

        holder.targetItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, TargetDetailActivity.class);
            intent.putExtra("subject_class_id", item.getSubjectClassId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<PersonalTargetItem> list) {
        itemList = list;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView targetSubject;
        private ImageView targetIcon;
        private CardView targetItem;
        public Holder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            targetItem = itemView.findViewById(R.id.targetItem);
            targetIcon = itemView.findViewById(R.id.targetItem_icon);
            targetSubject = itemView.findViewById(R.id.targetItem_subject);
        }
    }
}
