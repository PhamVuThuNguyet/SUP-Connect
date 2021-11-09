package com.example.supconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.supconnect.R;
import com.example.supconnect.model.SubClassofThisTeacher;

import java.util.List;

public class DropDownSelectClassAdapter extends BaseAdapter {

    private Context context;
    private List<SubClassofThisTeacher> subClassofThisTeacher;
    private int listItemLayoutResource;
    private int textViewItemName;

    public DropDownSelectClassAdapter(Context context, List<SubClassofThisTeacher> subClassofThisTeacher, int listItemLayoutResource, int textViewItemName) {
        this.context = context;
        this.subClassofThisTeacher = subClassofThisTeacher;
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemName = textViewItemName;
    }


    @Override
    public int getCount() {
        return subClassofThisTeacher.size();
    }

    @Override
    public Object getItem(int position) {
        return subClassofThisTeacher.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.choose_subject_target_list, parent, false);
        TextView subject = convertView.findViewById(R.id.subject_chosen);
        subject.setText(subClassofThisTeacher.get(position).getSubject_class_name());
        return convertView;
    }
}
