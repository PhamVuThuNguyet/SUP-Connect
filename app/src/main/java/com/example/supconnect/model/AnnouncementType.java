package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnouncementType {
    @SerializedName("announcement_type_id")
    @Expose
    private Integer announcementTypeId;
    @SerializedName("announcement_type_name")
    @Expose
    private String announcementTypeName;

    public Integer getAnnouncementTypeId() {
        return announcementTypeId;
    }

    public void setAnnouncementTypeId(Integer announcementTypeId) {
        this.announcementTypeId = announcementTypeId;
    }

    public String getAnnouncementTypeName() {
        return announcementTypeName;
    }

    public void setAnnouncementTypeName(String announcementTypeName) {
        this.announcementTypeName = announcementTypeName;
    }
}
