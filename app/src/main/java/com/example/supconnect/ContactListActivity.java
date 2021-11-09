package com.example.supconnect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.ChattingService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.RetrofitResponse.ListChatRoomResponse;
import com.example.supconnect.adapter.ContactListAdapter;
import com.example.supconnect.model.ContactDetails;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContactListActivity extends AppCompatActivity {
    private List<ContactDetails> contacts = new ArrayList<>();
    private List<ContactDetails> contacts_recent = new ArrayList<>();
    private RecyclerView recyclerView, allrecyclerView;
    private ContactListAdapter contactListAdapter, recentContactListAdapter;
    private ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recent_msg_view);
        allrecyclerView = findViewById(R.id.all_msg_view);

        recentContactListAdapter = new ContactListAdapter(this);
        recentContactListAdapter.setContacts(contacts_recent);

        contactListAdapter = new ContactListAdapter(this);
        contactListAdapter.setContacts(contacts);

        recyclerView.setAdapter(recentContactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        allrecyclerView.setAdapter(contactListAdapter);
        allrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        getListContact();
    }

    private void getListContact() {
        SharedPreferences useSharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getApplicationContext());
        retrofit.create(ChattingService.class).getListChatRoom(useSharedPreferences.getString("user_id", ""))
                .enqueue(new Callback<ListChatRoomResponse>() {
                    @Override
                    public void onResponse(Call<ListChatRoomResponse> call, Response<ListChatRoomResponse> response) {
                        if (response.isSuccessful() && response.body().getSuccess()) {
                            contacts.addAll(response.body().getChat());
                            contactListAdapter.notifyDataSetChanged();
                            if (contacts.size() >= 1) {
                                contacts_recent.add(contacts.get(0));
                            }

                            if (contacts.size() >= 2) {
                                contacts_recent.add(contacts.get(1));
                            }

                            if (contacts.size() >= 3) {
                                contacts_recent.add(contacts.get(2));
                            }
                            recentContactListAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override

                    public void onFailure
                            (Call<ListChatRoomResponse> call, Throwable t) {
                        Log.d("fail", String.valueOf(t));

                    }
                });
    }

}
