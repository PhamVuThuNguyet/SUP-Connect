package com.example.supconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.model.MarkColumn;

import java.util.List;

public class RecommendMarkColumnListAdapter extends RecyclerView.Adapter<RecommendMarkColumnListAdapter.Holder> {
    private Context context;
    private List<MarkColumn> markColumns;

    public RecommendMarkColumnListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecommendMarkColumnListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.target_detail_mark_column_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendMarkColumnListAdapter.Holder holder, int position) {
        holder.markColumnName.setText(markColumns.get(position).getGradeTypeName());
        String str = "Cần "+String.valueOf(markColumns.get(position).getGrade());
        holder.markColumnValue.setText(str);
    }

    @Override
    public int getItemCount() {
        return markColumns.size();
    }

    public void setMarkColumns(List<MarkColumn> list) {
        markColumns = list;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView markColumnName, markColumnValue;
        public Holder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            markColumnName = itemView.findViewById(R.id.markColumnName);
            markColumnValue = itemView.findViewById(R.id.markColumnValue);
        }
    }
}
