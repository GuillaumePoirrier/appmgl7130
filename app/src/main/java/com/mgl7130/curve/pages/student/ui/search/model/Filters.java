package com.mgl7130.curve.pages.student.ui.search.model;

import android.content.Context;
import android.text.TextUtils;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Object for passing filters around.
 */
public class Filters {

    private String subject = null;
    private String level = null;
    private Timestamp date = null;
    private String sortBy = null;
    private Query.Direction sortDirection = null;

    public Filters() {}

    public static Filters getDefault() {
        Filters filters = new Filters();
        filters.setSortBy(Cours.FIELD_DATE);
        filters.setSortDirection(Query.Direction.DESCENDING);

        return filters;
    }

    public boolean hasSubject() {
        return !(TextUtils.isEmpty(subject));
    }

    public boolean hasLevel() {
        return !(TextUtils.isEmpty(level));
    }

    public boolean hasDate() {
        return (date != null);
    }

    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Query.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Query.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSearchDescription(Context context) {
        StringBuilder desc = new StringBuilder();

        if (subject == null && level == null) {
            desc.append("<b>");
            desc.append(context.getString(R.string.all_classes));
            desc.append("</b>");
        }

        if (subject != null) {
            desc.append("<b>");
            desc.append(subject);
            desc.append("</b>");
        }

        if (subject != null && level != null) {
            desc.append(" for ");
        }

        if (level != null) {
            desc.append("<b>");
            desc.append(level);
            desc.append("</b>");
        }

        if (date != null) {
            desc.append(" the ");
            desc.append("<b>");
            desc.append((new SimpleDateFormat("dd MMM yyyy", Locale.CANADA_FRENCH).format(date.toDate())));
            desc.append("</b>");
        }

        return desc.toString();
    }

    public String getOrderDescription(Context context) {
        if (Cours.FIELD_LEVEL.equals(sortBy)) {
            return context.getString(R.string.sorted_by_level);
        } else if (Cours.FIELD_SUBJECT.equals(sortBy)) {
            return context.getString(R.string.sorted_by_subject);
        } else {
            return context.getString(R.string.sorted_by_date);
        }
    }
}
