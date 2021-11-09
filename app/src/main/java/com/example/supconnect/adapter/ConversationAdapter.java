package com.example.supconnect.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supconnect.R;
import com.example.supconnect.Utils.Constant;
import com.example.supconnect.model.Conversation;
import com.sendbird.android.SendBird;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ConversationAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context context;
    private List<Conversation> conversations;

    public ConversationAdapter(Context context, List<Conversation> list) {
        this.context = context;
        this.conversations = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(context).inflate(R.layout.conversation_send_list_item, parent, false);
            return new sendHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.conversation_receive_list_item, parent, false);
            return new receiveHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Conversation message = conversations.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((sendHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((receiveHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Conversation conversation = conversations.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if (conversation.getSenderId().equals(sharedPreferences.getString("user_id", ""))) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class receiveHolder extends RecyclerView.ViewHolder {
        private TextView recmsg;
        private ImageView image;

        public receiveHolder(@NonNull View itemView) {
            super(itemView);
            recmsg = itemView.findViewById(R.id.receive);
            image = itemView.findViewById(R.id.image);
        }

        void bind(Conversation msg) {
            if(msg.getType().equals("text")) {
                recmsg.setText(msg.getMessage());
                Log.d("MESSAGE_TYPE: ", "text");
            } else if (msg.getType().equals("image")) {
                recmsg.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                Log.d("MESSAGE_TYPE: ", "image");
                Picasso.get().load(Constant.DOMAIN + msg.getMessage()).into(image);
            }
        }
    }

    public class sendHolder extends RecyclerView.ViewHolder {
        private TextView sendmsg;
        private ImageView image;

        public sendHolder(@NonNull View itemView) {
            super(itemView);
            sendmsg = itemView.findViewById(R.id.send);
            image = itemView.findViewById(R.id.image);
        }

        void bind(Conversation msg) {
            if(msg.getType().equals("text")) {
                sendmsg.setText(msg.getMessage());
                Log.d("MESSAGE_TYPE: ", "text");
            } else if (msg.getType().equals("image")) {
                Log.d("MESSAGE_TYPE: ", "image");
                image.setVisibility(View.VISIBLE);
                sendmsg.setVisibility(View.GONE);
                Picasso.get().load(Constant.DOMAIN + msg.getMessage()).into(image);
            }
        }
    }
}
