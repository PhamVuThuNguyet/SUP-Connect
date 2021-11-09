package com.example.supconnect.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.API.WalletService;
import com.example.supconnect.PaymentInputActivity;
import com.example.supconnect.QrScanActivity;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.WalletResponse;
import com.example.supconnect.Utils.Helper;
import com.example.supconnect.adapter.TransactionHistoryAdapter;
import com.example.supconnect.model.TransactionHistoryItem;
import com.sendbird.android.shadow.okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class WalletFragment extends Fragment {

    private  View view;
    private RecyclerView TransactionHistory;
    private List<TransactionHistoryItem> transactionHistoryItems = new ArrayList<>();
    private TransactionHistoryAdapter transactionHistoryAdapter;
    private LinearLayout header;
    private TextView walletOwner, student_id, balance;
    private ImageButton btnPayScan;
    private Context mainContext;

    private SharedPreferences useSharedPreferences;
    private String user_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalletFragment(Context context) {
        mainContext = context;
    }

    public WalletFragment() {
        // Require empty constructor
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
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        view= inflater.inflate(R.layout.wallet_fragment, container, false);

        useSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        user_id = Helper.getStudentID(useSharedPreferences);

        initViews();

        header.setVisibility(View.GONE);

        transactionHistoryAdapter = new TransactionHistoryAdapter(getContext());
        transactionHistoryAdapter.setTransactionHistoryItems(transactionHistoryItems);

        TransactionHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        TransactionHistory.setAdapter(transactionHistoryAdapter);
        getListTransaction();
        return view;
    }

    private void initViews() {
        TransactionHistory = view.findViewById(R.id.transaction_history_list);
        header = view.findViewById(R.id.headerwallet);
        walletOwner = view.findViewById(R.id.walletOwner);
        student_id = view.findViewById(R.id.walletOwner_id);
        balance = view.findViewById(R.id.balance);
        btnPayScan = view.findViewById(R.id.btnPayScan);
        btnPayScan.setOnClickListener(v -> {
            scanCode();
        });
    }

    private void scanCode() {
        Intent intent = new Intent(mainContext, QrScanActivity.class);
        startActivity(intent);
    }

    private void getListTransaction() {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(WalletService.class).getTransaction(user_id)
                .enqueue(new Callback<WalletResponse>() {
                    @Override
                    public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getSuccess()) {
                                transactionHistoryItems.addAll(response.body().getPayments());
                                transactionHistoryAdapter.notifyDataSetChanged();
                                walletOwner.setText(response.body().getStudent_name());
                                student_id.setText(response.body().getStudent_id());
                                balance.setText(String.valueOf(response.body().getBalance()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WalletResponse> call, Throwable t) {
                        Log.d("fail", String.valueOf(t));
                    }

                });
    }
}