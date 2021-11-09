package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubClassofThisTeacher {
    @SerializedName("subject_class_id")
    @Expose
    private String subject_class_id;
    @SerializedName("subject_class_name")
    @Expose
    private String subject_class_name;

    public String getSubject_class_id() {
        return subject_class_id;
    }

    public SubClassofThisTeacher(String subject_class_id, String subject_class_name) {
        this.subject_class_id = subject_class_id;
        this.subject_class_name = subject_class_name;
    }

    public void setSubject_class_id(String subject_class_id) {
        this.subject_class_id = subject_class_id;
    }

    public String getSubject_class_name() {
        return subject_class_name;
    }

    public void setSubject_class_name(String subject_class_name) {
        this.subject_class_name = subject_class_name;
    }
}
