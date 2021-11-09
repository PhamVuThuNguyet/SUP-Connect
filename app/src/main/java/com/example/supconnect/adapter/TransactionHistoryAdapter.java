package com.example.supconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supconnect.R;
import com.example.supconnect.model.TransactionHistoryItem;

import java.util.HashMap;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.Holder> {
    private Context context;
    private List<TransactionHistoryItem> transactionHistoryItems;
    public TransactionHistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.transaction_history_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryAdapter.Holder holder, int position) {
        TransactionHistoryItem item = transactionHistoryItems.get(position);
        holder.transactionName.setText(item.getTransaction_detail());
        holder.transactionDate.setText(item.getDate());
        String pay = item.getAmount() + " đ";
        holder.transactionPay.setText(pay);
        switch (item.getTransaction_detail()) {
            case "Nạp tiền":
                holder.transactionIcon.setImageResource(R.drawable.ic_topup);
                break;
                // add more type
        }
    }

    public void setTransactionHistoryItems(List<TransactionHistoryItem> list) {
        transactionHistoryItems = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionHistoryItems.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ImageView transactionIcon;
        private TextView transactionName, transactionDate, transactionPay;
        public Holder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            transactionIcon = itemView.findViewById(R.id.transactionIcon);
            transactionDate = itemView.findViewById(R.id.transactionDate);
            transactionName = itemView.findViewById(R.id.transactionName);
            transactionPay = itemView.findViewById(R.id.transactionPay);
        }
    }
}
