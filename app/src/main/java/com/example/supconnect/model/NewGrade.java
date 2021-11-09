package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewGrade {
    @SerializedName("grade_book_id")
    @Expose
    private String gradeBookId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("grade_book_detail_id")
    @Expose
    private Integer gradeBookDetailId;

    public String getGradeBookId() {
        return gradeBookId;
    }

    public void setGradeBookId(String gradeBookId) {
        this.gradeBookId = gradeBookId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getGradeBookDetailId() {
        return gradeBookDetailId;
    }

    public void setGradeBookDetailId(Integer gradeBookDetailId) {
        this.gradeBookDetailId = gradeBookDetailId;
    }
}
