package com.mgl7130.curve.models;

public enum Subject {

    Mathematics("Mathematics"),
    Physics("Physics"),
    Chemistry("Chemistry");


    private String name = "";

    Subject(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
