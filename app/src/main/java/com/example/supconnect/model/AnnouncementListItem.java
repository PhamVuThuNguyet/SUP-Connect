package com.example.supconnect.model;

public class AnnouncementListItem {
    private int id;
    private int type;
    private String title;
    private String timeStamp;

    public AnnouncementListItem(int id, int type, String title, String timeStamp) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
