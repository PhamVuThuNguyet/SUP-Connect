package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectResult {

    @SerializedName("junction_id")
    int id;

    @SerializedName("class_name")
    String nameSubject;

    @SerializedName("number_credit")
    int number_credit;

    @SerializedName("point_Rollup")
    float point_Rollup;

    @SerializedName("point_Assign")
    float point_Assign;

    @SerializedName("point_midTerm")
    float point_midTerm;

    @SerializedName("point_EndTerm")
    float point_EndTerm;

    @SerializedName("point_10")
    float point_10;

    @SerializedName("point_word")
    String point_word;

    @SerializedName("lecturer")
    @Expose
    private String lecturer_id;

    public SubjectResult(int id, String nameSubject, int number_credit, float point_Rollup, float point_Assign,
                         float point_midTerm, float point_EndTerm, float point_10, String point_word)
    {
        this.id = id;
        this.nameSubject = nameSubject;
        this.number_credit = number_credit;
        this.point_Rollup = point_Rollup;
        this.point_Assign = point_Assign;
        this.point_midTerm = point_midTerm;
        this.point_EndTerm = point_EndTerm;
        this.point_10 = point_10;
        this.point_word = point_word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public int getNumber_credit() {
        return number_credit;
    }

    public void setNumber_credit(int number_credit) {
        this.number_credit = number_credit;
    }

    public float getPoint_Rollup() {
        return point_Rollup;
    }

    public void setPoint_Rollup(float point_Rollup) {
        this.point_Rollup = point_Rollup;
    }

    public float getPoint_Assign() {
        return point_Assign;
    }

    public void setPoint_Assign(float point_Assign) {
        this.point_Assign = point_Assign;
    }

    public float getPoint_midTerm() {
        return point_midTerm;
    }

    public void setPoint_midTerm(float point_midTerm) {
        this.point_midTerm = point_midTerm;
    }

    public float getPoint_EndTerm() {
        return point_EndTerm;
    }

    public void setPoint_EndTerm(float point_EndTerm) {
        this.point_EndTerm = point_EndTerm;
    }

    public float getPoint_10() {
        return point_10;
    }

    public void setPoint_10(float point_10) {
        this.point_10 = point_10;
    }

    public String getPoint_word() {
        return point_word;
    }

    public void setPoint_word(String point_word) {
        this.point_word = point_word;
    }

    public String getLecturer_id() {
        return lecturer_id;
    }

    public void setLecturer_id(String lecturer_id) {
        this.lecturer_id = lecturer_id;
    }
}
