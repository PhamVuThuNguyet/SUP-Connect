package com.example.supconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.supconnect.R;
import com.example.supconnect.model.SubjectOfThisStudent;

import java.util.ArrayList;

public class DropDownSelectSubjectAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<SubjectOfThisStudent> subjectOfThisStudents;
    private int listItemLayoutResource;
    private int textViewItemName;

    public DropDownSelectSubjectAdapter(Context context, ArrayList<SubjectOfThisStudent> subjectOfThisStudents, int listItemLayoutResource, int textViewItemName) {
        this.context = context;
        this.subjectOfThisStudents = subjectOfThisStudents;
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemName = textViewItemName;
    }

    @Override
    public int getCount() {
        return subjectOfThisStudents.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectOfThisStudents.get(position);
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
        subject.setText(subjectOfThisStudents.get(position).getSubjectClassName());
        return convertView;
    }

}
