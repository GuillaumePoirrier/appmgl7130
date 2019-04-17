package com.mgl7130.curve.util;

import com.google.firebase.Timestamp;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class DateTimeUtilTest {

    @Test
    public void getTimefromTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(date);

        String result = DateTimeUtil.getTime(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getTimefromNullTimestamp() {
        String result = DateTimeUtil.getTime(null);
        assertNull(result);
    }

    @Test
    public void getDayFromTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(date);

        String result = DateTimeUtil.getDay(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getDayFromNullTimestamp() {
        String result = DateTimeUtil.getDay(null);
        assertNull(result);
    }

    @Test
    public void getMonth() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(date);

        String result = DateTimeUtil.getMonth(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getMonthFromNullTimestamp() {
        String result = DateTimeUtil.getMonth(null);
        assertNull(result);
    }

    @Test
    public void getYearFromTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("yyyy", Locale.CANADA_FRENCH).format(date);

        String result = DateTimeUtil.getYear(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

    @Test
    public void getYearFromNullTimestamp() {
        String result = DateTimeUtil.getYear(null);
        assertNull(result);
    }

    @Test
    public void getDateString() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date);
        String normalResult = new SimpleDateFormat("dd MMMM", Locale.CANADA_FRENCH).format(date);

        String result = DateTimeUtil.getDateString(timestamp);

        assertNotNull(result);
        assertEquals(result, normalResult);
    }

}