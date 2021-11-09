package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.SubjectOfThisStudent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectListofStudentResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("subjects")
    @Expose
    private List<SubjectOfThisStudent> subjects = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<SubjectOfThisStudent> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectOfThisStudent> subjects) {
        this.subjects = subjects;
    }
}
