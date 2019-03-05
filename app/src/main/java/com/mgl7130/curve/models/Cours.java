package com.mgl7130.curve.models;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Cours {

    private String student;
    private String teacher;
    private Subject subject;
    private Level level;
    private Timestamp date;
    private Timestamp startDate;
    private Timestamp endDate;


    public Cours(String teacherId, Subject subject, Level level, Timestamp date, Timestamp startDate, Timestamp endDate) {
        this.teacher = teacherId;
        this.subject = subject;
        this.level = level;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Timestamp getStartDate() { return startDate; }

    public void setStartDate(Timestamp startDate) { this.startDate = startDate; }

    public Timestamp getEndDate() { return endDate; }

    public void setEndDate(Timestamp endDate) { this.endDate = endDate; }

    public String getStudentId() {
        return student;
    }

    public void setStudentId(String student) {
        this.student = student;
    }

    public String getTeacherId() {
        return teacher;
    }

    public void setTeacherId(String teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
