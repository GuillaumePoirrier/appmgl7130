package com.mgl7130.curve.models;

import com.google.firebase.Timestamp;


public class Cours extends GenericEntity{

    public static final String FIELD_SUBJECT = "subject";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_LEVEL = "level";

    private String studentId;
    private String teacherId;
    private Subject subject;
    private Level level;
    private Timestamp date;
    private Timestamp startDate;
    private Timestamp endDate;

    public Cours(){}

    public Cours(String teacherId,String studentId, Subject subject, Level level, Timestamp date, Timestamp startDate, Timestamp endDate) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.subject = subject;
        this.level = level;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Cours(String teacherId, Subject subject, Level level, Timestamp date, Timestamp startDate, Timestamp endDate) {
        this.teacherId = teacherId;
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

    public Subject getSubject() { return subject; }

    public void setSubject(Subject subject) { this.subject = subject; }

    public String getStudent_id() { return studentId; }

    public void setStudent_id(String student_id) { this.studentId = student_id; }

    public String getTeacher_id() { return teacherId; }

    public void setTeacher_id(String teacher_id) { this.teacherId = teacher_id; }

    public Level getLevel() { return level; }

    public void setLevel(Level level) { this.level = level; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }
}
