package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Announcement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("announcement")
    @Expose
    private List<Announcement> announcement = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }
}
