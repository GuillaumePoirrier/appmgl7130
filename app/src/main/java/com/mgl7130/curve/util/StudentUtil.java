package com.mgl7130.curve.util;

import com.mgl7130.curve.models.Student;

public class StudentUtil {

    public static String getFullname(Student student) {
        return student != null ?  student.getFirstName() + " " + student.getLastName() : "Pas encore d'eleve";
    }

}
