package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.HistoryRollcallStudent;
import com.example.supconnect.model.Student;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryRollcallLecurerResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("subject_class")
    @Expose
    private String subjectClass;
    @SerializedName("semeter")
    @Expose
    private Integer semeter;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("students")
    @Expose
    private List<HistoryRollcallStudent> students = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(String subjectClass) {
        this.subjectClass = subjectClass;
    }

    public Integer getSemeter() {
        return semeter;
    }

    public void setSemeter(Integer semeter) {
        this.semeter = semeter;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HistoryRollcallStudent> getStudents() {
        return students;
    }

    public void setStudents(List<HistoryRollcallStudent> students) {
        this.students = students;
    }
}
