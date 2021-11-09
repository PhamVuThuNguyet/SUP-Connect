package com.example.supconnect.model;

import com.google.gson.annotations.SerializedName;

public class Announcement {

    @SerializedName("announcement_id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("create_date")
    String date;

    @SerializedName("description")
    String des;

    @SerializedName("announcement_type_id")
    int typeid;

    @SerializedName("attachment")
    String attachment;

    @SerializedName("announcement_type_name")
    String announcement_type_name;

    public Announcement(int id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAnnouncement_type_name() {
        return announcement_type_name;
    }

    public void setAnnouncement_type_name(String announcement_type_name) {
        this.announcement_type_name = announcement_type_name;
    }
}
