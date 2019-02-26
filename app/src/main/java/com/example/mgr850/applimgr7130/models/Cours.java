package com.example.mgr850.applimgr7130.models;

import java.util.Date;

public class Cours {

    private Date date;
    private Student student;
    private Teacher teacher;
    private Subject subject;

    public Cours(Date date, Student student, Teacher teacher, Subject subject) {
        this.date = date;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getStudentFullName(){
        return this.student.getFullName();
    }
}
