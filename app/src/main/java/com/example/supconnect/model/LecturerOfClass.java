package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LecturerOfClass {
    @SerializedName("lecturer_id")
    @Expose
    private String lecturer_id;
    @SerializedName("name")
    @Expose
    private String lecturer_name;
    @SerializedName("email")
    @Expose
    private String lecturer_email;
    @SerializedName("avatar")
    @Expose
    private String lecturer_avatar;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("degree_name")
    @Expose
    private String degree;
    @SerializedName("phone_number_lecturer")
    @Expose
    private String phoneNumberLecturer;

    public String getLecturer_id() {
        return lecturer_id;
    }

    public void setLecturer_id(String lecturer_id) {
        this.lecturer_id = lecturer_id;
    }

    public String getLecturer_name() {
        return lecturer_name;
    }

    public void setLecturer_name(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public String getLecturer_email() {
        return lecturer_email;
    }

    public void setLecturer_email(String lecturer_email) {
        this.lecturer_email = lecturer_email;
    }

    public String getLecturer_avatar() {
        return lecturer_avatar;
    }

    public void setLecturer_avatar(String lecturer_avatar) {
        this.lecturer_avatar = lecturer_avatar;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPhoneNumberLecturer() {
        return phoneNumberLecturer;
    }

    public void setPhoneNumberLecturer(String phoneNumberLecturer) {
        this.phoneNumberLecturer = phoneNumberLecturer;
    }
}
