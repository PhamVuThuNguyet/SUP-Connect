package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.RollCallViewLecturer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentOfClassResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("students")
    @Expose
    private List<RollCallViewLecturer> students = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<RollCallViewLecturer> getStudents() {
        return students;
    }

    public void setStudents(List<RollCallViewLecturer> students) {
        this.students = students;
    }
}
