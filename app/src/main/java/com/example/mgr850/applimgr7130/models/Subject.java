package com.example.mgr850.applimgr7130.models;

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
