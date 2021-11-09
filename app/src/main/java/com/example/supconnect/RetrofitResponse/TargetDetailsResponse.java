package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.MarkColumn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TargetDetailsResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("grade_target")
    @Expose
    private Double grade_target;

    @SerializedName("currentGrade")
    @Expose
    private Double currentGrade;

    @SerializedName("grades")
    @Expose
    private List<MarkColumn> grades = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Double getGrade_target() {
        return grade_target;
    }

    public void setGrade_target(Double grade_target) {
        this.grade_target = grade_target;
    }

    public Double getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(Double currentGrade) {
        this.currentGrade = currentGrade;
    }

    public List<MarkColumn> getGrades() {
        return grades;
    }

    public void setGrades(List<MarkColumn> grades) {
        this.grades = grades;
    }
}
