package com.mgl7130.curve.models;

import android.graphics.drawable.Drawable;

public class Student extends User{

    public Student(){}

    public Student(String fisrtName, String lastName) {
        super(fisrtName, lastName);
    }

    public Student getStudentFromId(String studentId){
        //TODO get Student object corresponding to String id
        return null;
    }

    // IMPORTANT : À définir !!

    public Drawable getImgStudent (){
        return null;
    }


}
