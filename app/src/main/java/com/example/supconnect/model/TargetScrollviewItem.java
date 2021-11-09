package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
    Can replace with Target class
 */
public class TargetScrollviewItem {
    @SerializedName("target_id")
    @Expose
    private Integer targetId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("subject_class_id")
    @Expose
    private String subjectClassId;
    @SerializedName("grade_target")
    @Expose
    private Double gradeTarget;
    @SerializedName("currentGrade")
    @Expose
    private Double currentGrade;

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectClassId() {
        return subjectClassId;
    }

    public void setSubjectClassId(String subjectClassId) {
        this.subjectClassId = subjectClassId;
    }

    public Double getGradeTarget() {
        return gradeTarget;
    }

    public void setGradeTarget(Double gradeTarget) {
        this.gradeTarget = gradeTarget;
    }

    public Double getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(Double currentGrade) {
        this.currentGrade = currentGrade;
    }
}
