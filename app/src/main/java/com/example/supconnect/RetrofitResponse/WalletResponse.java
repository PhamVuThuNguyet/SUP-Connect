package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.TransactionHistoryItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("student_name")
    @Expose
    private String student_name;
    @SerializedName("student_id")
    @Expose
    private String student_id;
    @SerializedName("payments")
    @Expose
    private List<TransactionHistoryItem> payments;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public List<TransactionHistoryItem> getPayments() {
        return payments;
    }

    public void setPayments(List<TransactionHistoryItem> payments) {
        this.payments = payments;
    }
}
