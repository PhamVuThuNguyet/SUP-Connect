package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GradeColumn {
    @SerializedName("grade_book_id")
    @Expose
    private Integer gradeBookId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("type_name")
    @Expose
    private String typeName;

    public Integer getGradeBookId() {
        return gradeBookId;
    }

    public void setGradeBookId(Integer gradeBookId) {
        this.gradeBookId = gradeBookId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
