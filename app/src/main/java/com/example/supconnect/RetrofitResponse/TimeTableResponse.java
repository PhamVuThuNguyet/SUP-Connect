package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Lesson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("timetables")
    @Expose
    private List<Lesson> timetables = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Lesson> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Lesson> timetables) {
        this.timetables = timetables;
    }
}
