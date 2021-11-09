package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lecturer {
    @SerializedName("lecturer_id")
    @Expose
    private String lecturerId;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("faculty_id")
    @Expose
    private Integer facultyId;
    @SerializedName("first_name_lecturer")
    @Expose
    private String firstNameLecturer;
    @SerializedName("last_name_lecturer")
    @Expose
    private String lastNameLecturer;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("phone_number_lecturer")
    @Expose
    private String phoneNumberLecturer;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("degree_name")
    @Expose
    private String degree_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("name")
    @Expose
    private String nameWithDegree;

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getFirstNameLecturer() {
        return firstNameLecturer;
    }

    public void setFirstNameLecturer(String firstNameLecturer) {
        this.firstNameLecturer = firstNameLecturer;
    }

    public String getLastNameLecturer() {
        return lastNameLecturer;
    }

    public void setLastNameLecturer(String lastNameLecturer) {
        this.lastNameLecturer = lastNameLecturer;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumberLecturer() {
        return phoneNumberLecturer;
    }

    public void setPhoneNumberLecturer(String phoneNumberLecturer) {
        this.phoneNumberLecturer = phoneNumberLecturer;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameWithDegree() {
        return nameWithDegree;
    }

    public void setNameWithDegree(String nameWithDegree) {
        this.nameWithDegree = nameWithDegree;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
    }
}
