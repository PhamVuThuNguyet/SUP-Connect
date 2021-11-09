package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.SubjectResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GradeOfStudentResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("subjectResults")
    @Expose
    private List<SubjectResult> subjectResults = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<SubjectResult> getSubjectResults() {
        return subjectResults;
    }

    public void setSubjectResults(List<SubjectResult> subjectResults) {
        this.subjectResults = subjectResults;
    }
}
