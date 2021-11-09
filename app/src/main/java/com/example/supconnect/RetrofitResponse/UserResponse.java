package com.example.supconnect.RetrofitResponse;

import android.content.Intent;

import com.example.supconnect.model.Lecturer;
import com.example.supconnect.model.Parent;
import com.example.supconnect.model.Student;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("student")
    @Expose
    private Student student;

    @SerializedName("lecturer")
    @Expose
    private Lecturer lecturer;

    @SerializedName("parent")
    @Expose
    private Parent parent;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
