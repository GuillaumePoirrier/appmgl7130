package com.mgl7130.curve.util;

import com.mgl7130.curve.models.Student;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentUtilTest {

    @Test
    public void getFullname() {
        Student student = new Student("FirstName", "LastName");
        String result = StudentUtil.getFullname(student);

        assertEquals(result, "FirstName LastName");
    }
}