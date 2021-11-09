package com.example.supconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supconnect.API.ChattingService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.RetrofitResponse.ConversationResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.ConversationAdapter;
import com.example.supconnect.model.Conversation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConversationActivity extends AppCompatActivity {
    private List<Conversation> conversations = new ArrayList<>();
    private RecyclerView conversation;
    private ConversationAdapter conversationAdapter;
    private ImageButton back, sendBtn, addFileBtn;
    private TextView txtConversationTitle, txtFacultyOrClass;
    private EditText edtConservation;
    private CircleImageView imgAvatar;
    SharedPreferences userSharedPreferences;
    private CircleImageView partneravatar;
    private Bitmap bitmap = null;

    private static int GALLERY_SEND_PHOTO_MESSAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        userSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);

        edtConservation = findViewById(R.id.edt_conversation);
        imgAvatar = findViewById(R.id.partnerAvatar);

        txtConversationTitle = findViewById(R.id.conversation_title);
        txtFacultyOrClass = findViewById(R.id.txt_faculty_or_class);
        partneravatar = findViewById(R.id.partnerAvatar);

        sendBtn = findViewById(R.id.sendMsgBtn);
        sendBtn.setOnClickListener(v -> {
            String message = edtConservation.getText().toString();
            if(!message.isEmpty()) {
                sendMessage(String.valueOf(getIntent().getExtras().getInt("room_id")), userSharedPreferences.getString("user_id", ""), message, "text");
            }
            edtConservation.setText("");
        });

        addFileBtn = findViewById(R.id.btnAddFile);
        addFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_SEND_PHOTO_MESSAGE);
            }
        });

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(v -> finish());

        conversation = findViewById(R.id.messageList);
        conversation.setHasFixedSize(true);
        conversationAdapter = new ConversationAdapter(this, conversations);
        conversation.setAdapter(conversationAdapter);
        conversation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        getConversation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== GALLERY_SEND_PHOTO_MESSAGE && resultCode==RESULT_OK){
            Uri imgUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                sendMessage(String.valueOf(getIntent().getExtras().getInt("room_id")), userSharedPreferences.getString("user_id", ""), Helper.bitmapToString(bitmap), "image");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getConversation() {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        retrofit.create(ChattingService.class).getConversation(String.valueOf(getIntent().getExtras().getInt("room_id")), userSharedPreferences.getString("user_id", ""), getIntent().getExtras().getString("user_2"))
                .enqueue(new Callback<ConversationResponse>() {
                    @Override
                    public void onResponse(Call<ConversationResponse> call, Response<ConversationResponse> response) {
                        Log.d("CHATTING_RES: ", response.message());
                        if(response.isSuccessful() && response.body().getSuccess()) {
                            Glide.with(getApplicationContext()).load(response.body().getAvatar()).centerCrop().into(imgAvatar);
                            conversations.addAll(response.body().getMessages());
                            conversationAdapter.notifyDataSetChanged();
                            txtConversationTitle.setText(response.body().getName());
                            txtFacultyOrClass.setText(response.body().getFaculty_name());
                            Glide.with(getApplicationContext()).load(response.body().getAvatar()).centerCrop().into(partneravatar);
                        }
                    }

                    @Override
                    public void onFailure(Call<ConversationResponse> call, Throwable t) {

                    }
                });
    }

    private void sendMessage(String id_chat, String sender_id, String message, String type) {
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        retrofit.create(ChattingService.class).sendMessage(id_chat, sender_id, message, type)
                .enqueue(new Callback<Conversation>() {
                    @Override
                    public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                        Log.d("SEND_MESSAGE: ", response.message());
                        if(response.isSuccessful()) {
                            int size = conversations.size();
                            Conversation conversation = response.body();
                            conversations.add(size, conversation);
                            conversationAdapter.notifyItemInserted(size);
                        }
                    }

                    @Override
                    public void onFailure(Call<Conversation> call, Throwable t) {

                    }
                });
    }
}