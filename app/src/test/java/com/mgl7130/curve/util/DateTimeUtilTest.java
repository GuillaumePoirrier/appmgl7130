package com.mgl7130.curve.util;

import com.google.firebase.Timestamp;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class DateTimeUtilTest {


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