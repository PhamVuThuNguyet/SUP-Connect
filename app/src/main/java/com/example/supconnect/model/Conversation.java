package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Conversation {
    @SerializedName("chat_history_detail_id")
    @Expose
    private Integer chatHistoryDetailId;
    @SerializedName("chat_history_id")
    @Expose
    private Integer chatHistoryId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("type")
    @Expose
    private String type;

    public Integer getChatHistoryDetailId() {
        return chatHistoryDetailId;
    }

    public void setChatHistoryDetailId(Integer chatHistoryDetailId) {
        this.chatHistoryDetailId = chatHistoryDetailId;
    }

    public Integer getChatHistoryId() {
        return chatHistoryId;
    }

    public void setChatHistoryId(Integer chatHistoryId) {
        this.chatHistoryId = chatHistoryId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
