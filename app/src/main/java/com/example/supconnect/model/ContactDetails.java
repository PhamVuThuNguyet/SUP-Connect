package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactDetails {
    @SerializedName("chat_history_id")
    @Expose
    private Integer chatHistoryId;
    @SerializedName("user_1")
    @Expose
    private String user1;
    @SerializedName("user_2")
    @Expose
    private String user2;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("messageTime")
    @Expose
    private String messageTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Integer getChatHistoryId() {
        return chatHistoryId;
    }

    public void setChatHistoryId(Integer chatHistoryId) {
        this.chatHistoryId = chatHistoryId;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
