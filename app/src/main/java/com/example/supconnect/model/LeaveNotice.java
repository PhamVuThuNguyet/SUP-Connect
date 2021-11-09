package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveNotice {
    @SerializedName("leave_notice_id")
    @Expose
    private int leave_notice_id;
    @SerializedName("announcement_type")
    @Expose
    private int announcement_type;
    @SerializedName("leave_notice_title")
    @Expose
    private String title;
    @SerializedName("lecturer")
    @Expose
    private String nameLecture;
    @SerializedName("leave_date")
    @Expose
    private String leave_date;
    @SerializedName("create_date")
    @Expose
    private String create_date;

    public int getLeave_notice_id() {
        return leave_notice_id;
    }

    public void setLeave_notice_id(int leave_notice_id) {
        this.leave_notice_id = leave_notice_id;
    }

    public int getAnnouncement_type() {
        return announcement_type;
    }

    public void setAnnouncement_type(int announcement_type) {
        this.announcement_type = announcement_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameLecture() {
        return nameLecture;
    }

    public void setNameLecture(String nameLecture) {
        this.nameLecture = nameLecture;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
