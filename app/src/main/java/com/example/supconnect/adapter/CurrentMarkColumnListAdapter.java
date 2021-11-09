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

import org.w3c.dom.Text;

import java.util.List;

public class CurrentMarkColumnListAdapter extends RecyclerView.Adapter<CurrentMarkColumnListAdapter.Holder> {
    private Context context;
    private List<MarkColumn> markColumns;

    public CurrentMarkColumnListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CurrentMarkColumnListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View currentmarkview = inflater.inflate(R.layout.target_detail_mark_column_item, parent, false);
        return new Holder(currentmarkview);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentMarkColumnListAdapter.Holder holder, int position) {
        MarkColumn mark = markColumns.get(position);
        holder.markColumnName.setText(mark.getGradeTypeName());
        holder.markColumnValue.setText(String.valueOf(mark.getGrade()));
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
