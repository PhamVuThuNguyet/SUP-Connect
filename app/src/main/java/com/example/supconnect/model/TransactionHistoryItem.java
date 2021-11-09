package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionHistoryItem {
    @SerializedName("type_name")
    @Expose
    private String transaction_detail;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getTransaction_detail() {
        return transaction_detail;
    }

    public void setTransaction_detail(String transaction_detail) {
        this.transaction_detail = transaction_detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
