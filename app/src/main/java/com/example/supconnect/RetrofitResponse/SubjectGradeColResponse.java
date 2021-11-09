package com.example.supconnect.RetrofitResponse;

import com.example.supconnect.model.GradeColumn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectGradeColResponse {
    @SerializedName("sucess")
    @Expose
    private String sucess;
    @SerializedName("subject class")
    @Expose
    private String subjectClass;
    @SerializedName("grade columns")
    @Expose
    private List<GradeColumn> gradeColumns = null;

    public String getSucess() {
        return sucess;
    }

    public void setSucess(String sucess) {
        this.sucess = sucess;
    }

    public String getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(String subjectClass) {
        this.subjectClass = subjectClass;
    }

    public List<GradeColumn> getGradeColumns() {
        return gradeColumns;
    }

    public void setGradeColumns(List<GradeColumn> gradeColumns) {
        this.gradeColumns = gradeColumns;
    }
}
