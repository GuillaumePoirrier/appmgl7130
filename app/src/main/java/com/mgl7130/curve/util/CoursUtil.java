package com.mgl7130.curve.util;

import com.google.firebase.Timestamp;
import com.mgl7130.curve.models.Level;
import com.mgl7130.curve.models.Subject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CoursUtil {

    public static String getTime(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(timestamp.toDate());
    }

    public static String getDay(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(timestamp.toDate());
    }

    public static String getMonth(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(timestamp.toDate());
    }

    public static String getYear(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new SimpleDateFormat("yyyy", Locale.CANADA_FRENCH).format(timestamp.toDate());
    }

    public static String getSubject(Subject subject) {
        if (subject == null) return null;
        return subject.toString();
    }

    public static String getLevel(Level level) {
        if (level == null) return null;
        return level.toString();
    }

    public static String getDateString(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new SimpleDateFormat("dd MMMM", Locale.CANADA_FRENCH).format(timestamp.toDate());
    }

}
