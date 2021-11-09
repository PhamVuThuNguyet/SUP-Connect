package com.example.supconnect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supconnect.R;
import com.example.supconnect.model.AttachmentFile;

import java.util.List;

public class AttachmentListAdapter extends BaseAdapter {
    private List<AttachmentFile> files;
    private LayoutInflater layoutInflater;
    private Context context;

    public AttachmentListAdapter(Context context, List<AttachmentFile> files) {
        this.files = files;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.attachment_list_item,null);
            holder = new ViewHolder();
            holder.fileIcon = convertView.findViewById(R.id.file_icon);
            holder.fileName = convertView.findViewById(R.id.file_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AttachmentFile file = files.get(position);

        holder.fileName.setText(file.getFileName());

        int fileicon = getIconIdByName(file.getFileType());

        holder.fileIcon.setImageResource(fileicon);

        return convertView;
    }

    private int getIconIdByName(String fileType) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier("ic_file_"+fileType , "drawable", pkgName);
        return resID;
    }

    static class ViewHolder {
        ImageView fileIcon;
        TextView fileName;
    }
}
