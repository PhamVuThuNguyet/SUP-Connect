package com.example.supconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supconnect.ConversationActivity;
import com.example.supconnect.R;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.model.ContactDetails;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.myViewHolder> {
    private Context context;
    private List<ContactDetails> contacts;

    public ContactListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactview = inflater.inflate(R.layout.contact_list_item, parent, false);
        return new myViewHolder(contactview);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        ContactDetails contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.msg.setText(contact.getMessage());

        holder.msgTime.setText(Helper.convertDateTime(contact.getMessageTime()));

        Glide.with(context).load(contact.getAvatar()).centerCrop().into(holder.avatar);
        holder.contact.setOnClickListener(v -> {
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra("room_id", contact.getChatHistoryId());
            intent.putExtra("user_2", "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<ContactDetails> list) {
        contacts = list;
        notifyDataSetChanged();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView name, msg, msgTime;
        private RelativeLayout contact;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            msg = itemView.findViewById(R.id.msg);
            msgTime = itemView.findViewById(R.id.msgTime);
            contact = itemView.findViewById(R.id.contact_row);
        }
    }
}
