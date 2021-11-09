package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RollCallViewStudent {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("lesson")
    @Expose
    private String lesson;

    @SerializedName("is_attend")
    @Expose
    private int is_attend;

    public String getDate()  {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public int getIs_attend() {
        return is_attend;
    }

    public void setIs_attend(int is_attend) {
        this.is_attend = is_attend;
    }
}
