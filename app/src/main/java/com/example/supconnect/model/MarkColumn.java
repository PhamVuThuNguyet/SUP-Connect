package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkColumn {
    @SerializedName("grade_type_name")
    @Expose
    private String gradeTypeName;
    @SerializedName("grade_type")
    @Expose
    private Integer gradeType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("grade")
    @Expose
    private Double grade;


    public String getGradeTypeName() {
        return gradeTypeName;
    }

    public void setGradeTypeName(String gradeTypeName) {
        this.gradeTypeName = gradeTypeName;
    }

    public Integer getGradeType() {
        return gradeType;
    }

    public void setGradeType(Integer gradeType) {
        this.gradeType = gradeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
