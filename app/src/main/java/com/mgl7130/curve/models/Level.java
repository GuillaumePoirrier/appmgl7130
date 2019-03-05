package com.mgl7130.curve.models;

import java.util.ArrayList;
import java.util.List;

public enum Level {

    Primary("Primary"),
    Secondary("Secondary"),
    College("College"),
    University("University");


    private String name = "";

    Level(String name){
        this.name = name;
    }

    public static List<String> stringValues(){
        List<String> list = new ArrayList<>();
        for (Level l: Level.values()) {
            list.add(l.name);
        }
        return list;
    }

    public String toString(){
        return name;
    }
}
