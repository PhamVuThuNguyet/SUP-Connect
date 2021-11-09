package com.example.supconnect.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RollCallViewLecturer {
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("subject_class_id")
    @Expose
    private String subject_class_id;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("checkRollUp")
    @Expose
    private Boolean checkRollUp;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubject_class_id() {
        return subject_class_id;
    }

    public void setSubject_class_id(String subject_class_id) {
        this.subject_class_id = subject_class_id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getCheckRollUp() {
        return checkRollUp;
    }

    public void setCheckRollUp(Boolean checkRollUp) {
        this.checkRollUp = checkRollUp;
    }

}
