package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {
    @SerializedName("record_detail_id")
    @Expose
    private Integer recordDetailId;
    @SerializedName("record_id")
    @Expose
    private Integer recordId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("is_attend")
    @Expose
    private Integer isAttend;
    @SerializedName("leave_of_absence_letter")
    @Expose
    private Integer leaveOfAbsenceLetter;
    @SerializedName("reason")
    @Expose
    private String reason;

    public Integer getRecordDetailId() {
        return recordDetailId;
    }

    public void setRecordDetailId(Integer recordDetailId) {
        this.recordDetailId = recordDetailId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Integer isAttend) {
        this.isAttend = isAttend;
    }

    public Integer getLeaveOfAbsenceLetter() {
        return leaveOfAbsenceLetter;
    }

    public void setLeaveOfAbsenceLetter(Integer leaveOfAbsenceLetter) {
        this.leaveOfAbsenceLetter = leaveOfAbsenceLetter;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
