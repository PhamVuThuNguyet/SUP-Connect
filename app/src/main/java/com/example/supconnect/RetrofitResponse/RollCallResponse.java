package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Record;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RollCallResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("record")
    @Expose
    private Record record;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
