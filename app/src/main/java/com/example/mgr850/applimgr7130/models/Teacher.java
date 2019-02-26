package com.example.mgr850.applimgr7130.models;

import java.util.Date;

public class Teacher {

    private String fisrtName;
    private String lastName;
    private Date dateOfBirth;

    public Teacher(String fisrtName, String lastName, Date dateOfBirth) {
        this.fisrtName = fisrtName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
