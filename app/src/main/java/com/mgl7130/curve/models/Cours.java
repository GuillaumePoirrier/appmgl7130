package com.mgl7130.curve.models;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;

public class Cours {

    private String student;
    private String teacher;
    private Subject subject;
    private Timestamp startDate;
    private Timestamp endDate;


    public Cours(Student student, Teacher teacher, Subject subject) {
        this.student = student.getId();
        this.teacher = teacher.getId();
        this.subject = subject;
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
