package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.ContactDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListChatRoomResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("chat")
    @Expose
    private List<ContactDetails> chat = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ContactDetails> getChat() {
        return chat;
    }

    public void setChat(List<ContactDetails> chat) {
        this.chat = chat;
    }
}
