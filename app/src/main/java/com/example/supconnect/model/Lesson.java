package com.example.supconnect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {
    @SerializedName("timetable_id")
    @Expose
    private Integer timetableId;
    @SerializedName("subject_class_id")
    @Expose
    private String subjectClassId;
    @SerializedName("day_of_week")
    @Expose
    private Integer dayOfWeek;
    @SerializedName("lesson")
    @Expose
    private String lesson;
    @SerializedName("classroom")
    @Expose
    private String classroom;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("lecturer_id")
    @Expose
    private String lecturerId;
    @SerializedName("lecturer")
    @Expose
    private String lecturer;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("subject_class_name")
    @Expose
    private String subjectClassName;
    @SerializedName("school_year")
    @Expose
    private String schoolYear;
    @SerializedName("semester")
    @Expose
    private String semester;

    public Integer getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Integer timetableId) {
        this.timetableId = timetableId;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectClassId() {
        return subjectClassId;
    }

    public void setSubjectClassId(String subjectClassId) {
        this.subjectClassId = subjectClassId;
    }

    public String getSubjectClassName() {
        return subjectClassName;
    }

    public void setSubjectClassName(String subjectClassName) {
        this.subjectClassName = subjectClassName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
