package com.mgl7130.curve.models;

import java.util.ArrayList;
import java.util.List;

public enum Subject {

    Mathematics("Mathematics"),
    Physics("Physics"),
    Chemistry("Chemistry");


    private String name = "";

    Subject(String name){
        this.name = name;
    }

    public static List<String> stringValues(){
        List<String> list = new ArrayList<>();
        for (Subject s: Subject.values()) {
            list.add(s.name);
        }
        return list;
    }

    public String toString(){
        return name;
    }
}
