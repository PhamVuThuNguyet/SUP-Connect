package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.RollCallViewStudent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RollCallRecordViewStudentResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("subject_class_name")
    @Expose
    private String subject_class_name;
    @SerializedName("record")
    @Expose
    private List<RollCallViewStudent> records;
    @SerializedName("total_count")
    @Expose
    private int total_count;
    @SerializedName("absence_count")
    @Expose
    private int absence_count;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSubject_class_name() {
        return subject_class_name;
    }

    public void setSubject_class_name(String subject_class_name) {
        this.subject_class_name = subject_class_name;
    }

    public List<RollCallViewStudent> getRecords() {
        return records;
    }

    public void setRecords(List<RollCallViewStudent> records) {
        this.records = records;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getAbsence_count() {
        return absence_count;
    }

    public void setAbsence_count(int absence_count) {
        this.absence_count = absence_count;
    }
}
