package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.Lecturer;
import com.example.supconnect.model.LecturerOfClass;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LecturerResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("all_lecturers")
    @Expose
    private List<Lecturer> all_lecturers;
    @SerializedName("class_lecturers")
    @Expose
    private List<LecturerOfClass> class_lecturers;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Lecturer> getAll_lecturers() {
        return all_lecturers;
    }

    public void setAll_lecturers(List<Lecturer> all_lecturers) {
        this.all_lecturers = all_lecturers;
    }

    public List<LecturerOfClass> getClass_lecturers() {
        return class_lecturers;
    }

    public void setClass_lecturers(List<LecturerOfClass> class_lecturers) {
        this.class_lecturers = class_lecturers;
    }
}
