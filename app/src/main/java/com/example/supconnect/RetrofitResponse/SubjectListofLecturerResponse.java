package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.ClassOfThisTeacher;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectListofLecturerResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("subject_lists")
    @Expose
    private List<ClassOfThisTeacher> subject_lists;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ClassOfThisTeacher> getSubject_lists() {
        return subject_lists;
    }

    public void setSubject_lists(List<ClassOfThisTeacher> subject_lists) {
        this.subject_lists = subject_lists;
    }
}
