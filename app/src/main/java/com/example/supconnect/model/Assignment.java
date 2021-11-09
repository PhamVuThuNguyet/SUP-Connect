package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Assignment {
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;
    @SerializedName("class_name")
    @Expose
    private String subjectClass;
    @SerializedName("announcement_type")
    @Expose
    private Integer announcementType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(String subjectClass) {
        this.subjectClass = subjectClass;
    }

    public Integer getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(Integer announcementType) {
        this.announcementType = announcementType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
