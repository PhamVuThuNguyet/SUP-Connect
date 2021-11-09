package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.AnnouncementDetailActivity;
import com.example.supconnect.R;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.model.AnnouncementListItem;

import java.util.HashMap;
import java.util.List;

public class AnnouncementListAdapter extends RecyclerView.Adapter<AnnouncementListAdapter.Holder> {
    private Context context;
    private List<AnnouncementListItem> announcementListItems;
    private HashMap<Integer, String> typeMap;

    public AnnouncementListAdapter(Context context) {
        this.context = context;
        typeMap = new HashMap<Integer, String>();
        typeMap.put(1, "Thông báo về Đào tạo");
        typeMap.put(2, "Thông báo về Khảo thí");
        typeMap.put(3, "Thông báo về Công tác sinh viên");
        typeMap.put(4, "Thông báo về Kế hoạch tài chính");
        typeMap.put(5, "Báo nghỉ/Báo học bù");
        typeMap.put(6, "Thông báo Bài tập môn học");

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_announcement_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        AnnouncementListItem item = announcementListItems.get(position);
        switch (item.getType()) {
            case 1:
                holder.icon.setImageResource(R.drawable.ic_announcement);
                break;
            case 5:
                holder.icon.setImageResource(R.drawable.ic_calendar_schedule);
        }

        holder.title.setText(item.getTitle());
        holder.category.setText(typeMap.get(item.getType()));
        holder.timestamp.setText(Helper.convertDateTime(item.getTimeStamp()));

        holder.nav.setOnClickListener(v -> {
            Intent intent = new Intent(context, AnnouncementDetailActivity.class);
            intent.putExtra("announcement_id", item.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return announcementListItems.size();
    }

    public void setAnnouncementListItems(List<AnnouncementListItem> list) {
        announcementListItems = list;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView category, title, timestamp;
        private ImageButton nav;
        public Holder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            nav = itemView.findViewById(R.id.announcementNavBtn);
            icon = itemView.findViewById(R.id.announcementIcon);
            category = itemView.findViewById(R.id.announcementCat);
            title = itemView.findViewById(R.id.announcementTitle);
            timestamp = itemView.findViewById(R.id.announcementTimestamp);
        }
    }
}
