package com.mgl7130.curve.models;

import java.util.ArrayList;
import java.util.List;

public enum Subject {

    Mathematics("Mathematics"),
    Physics("Physics"),
    Chemistry("Chemistry"),
    English("English"),
    French("French"),
    Spanish("Spanish"),
    German("German"),
    Chinese("Chinese"),
    History("History"),
    Geography("Geography"),
    SVT("S.V.T"),
    Chant("Chant"),
    Computer_Science("Computer Science"),
    Philosophy("Philosophy"),
    Economy("Economy"),
    Literature("Literature");




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

    public static Subject getSubject(String name){
        for (Subject s: Subject.values()) {
            if(s.name.equals(name)){
                return s;
            }
        }
        return null;
    }

    public String toString(){
        return name;
    }
}
