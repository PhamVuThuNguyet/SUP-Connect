package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Conversation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConversationResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("faculty")
    @Expose
    private String faculty_name;
    @SerializedName("messages")
    @Expose
    private List<Conversation> messages = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
    
    public List<Conversation> getMessages() {
        return messages;
    }

    public void setMessages(List<Conversation> messages) {
        this.messages = messages;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

}
