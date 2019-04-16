package com.mgl7130.curve.util;

import com.google.firebase.Timestamp;
import com.mgl7130.curve.models.Level;
import com.mgl7130.curve.models.Subject;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class CoursUtilTest {

    @Test
    public void getTimefromTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(date);

        String result = CoursUtil.getTime(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getTimefromNullTimestamp() {
        String result = CoursUtil.getTime(null);
        assertNull(result);
    }

    @Test
    public void getDayFromTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(date);

        String result = CoursUtil.getDay(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getDayFromNullTimestamp() {
        String result = CoursUtil.getDay(null);
        assertNull(result);
    }

    @Test
    public void getMonth() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(date);

        String result = CoursUtil.getMonth(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getMonthFromNullTimestamp() {
        String result = CoursUtil.getMonth(null);
        assertNull(result);
    }

    @Test
    public void getYearFromTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("yyyy", Locale.CANADA_FRENCH).format(date);

        String result = CoursUtil.getYear(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getYearFromNullTimestamp() {
        String result = CoursUtil.getYear(null);
        assertNull(result);
    }

    @Test
    public void getSubject() {
        Subject subject = Subject.Chemistry;
        String result = CoursUtil.getSubject(subject);
        assertNotNull(result);
        assertEquals(result, "Chimie");
    }

    @Test
    public void getLevel() {
        Level level = Level.Primary;
        String result = CoursUtil.getLevel(level);
        assertNotNull(result);
        assertEquals(result, "Primaire");
    }

    @Test
    public void getDateString() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("dd MMMM", Locale.CANADA_FRENCH).format(date);

        String result = CoursUtil.getDateString(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }
}