package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.AnnouncementType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementTypeResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("type")
    @Expose
    private List<AnnouncementType> type = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<AnnouncementType> getType() {
        return type;
    }

    public void setType(List<AnnouncementType> type) {
        this.type = type;
    }
}
