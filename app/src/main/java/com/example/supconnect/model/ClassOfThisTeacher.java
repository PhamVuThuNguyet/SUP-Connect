package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassOfThisTeacher {
    @SerializedName("subject_id")
    @Expose
    private String subject_id;
    @SerializedName("subject_name")
    @Expose
    private String subject_name;
    @SerializedName("subject_classes")
    @Expose
    private List<SubClassofThisTeacher> subject_classes;
    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public List<SubClassofThisTeacher> getSubject_classes() {
        return subject_classes;
    }

    public void setSubject_classes(List<SubClassofThisTeacher> subject_classes) {
        this.subject_classes = subject_classes;
    }
}
