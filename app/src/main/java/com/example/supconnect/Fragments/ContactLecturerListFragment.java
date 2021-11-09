package com.example.supconnect.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.ChattingService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.ListChatRoomResponse;
import com.example.supconnect.adapter.ContactListAdapter;
import com.example.supconnect.model.ContactDetails;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactLecturerListFragment extends Fragment {
    private List<ContactDetails> contacts = new ArrayList<>();
    private List<ContactDetails> contacts_recent = new ArrayList<>();
    private RecyclerView recyclerView, allrecyclerView;
    private ContactListAdapter contactListAdapter, recentContactListAdapter;
    private ImageButton back;
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactLecturerListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactLecturerListFragment newInstance(String param1, String param2) {
        ContactLecturerListFragment fragment = new ContactLecturerListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.contact_lecturer_list_fragment, container, false);
        back = view.findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        recyclerView = view.findViewById(R.id.recent_msg_view);
        allrecyclerView = view.findViewById(R.id.all_msg_view);

        recentContactListAdapter = new ContactListAdapter(getContext());
        recentContactListAdapter.setContacts(contacts_recent);

        contactListAdapter = new ContactListAdapter(getContext());
        contactListAdapter.setContacts(contacts);

        recyclerView.setAdapter(recentContactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        allrecyclerView.setAdapter(contactListAdapter);
        allrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        getListContact();
        return view;
    }
    private void getListContact() {
        SharedPreferences useSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getActivity().getApplicationContext());
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