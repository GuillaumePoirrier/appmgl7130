package com.mgl7130.curve.pages.student.ui.search.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.mgl7130.curve.pages.student.ui.search.model.Filters;

/**
 * ViewModel for {@link com.mgl7130.curve.pages.student.ui.search.list.StudentSearchRecyclerFragment}.
 */
public class StudentSearchFilterViewModel extends ViewModel {

    private Filters mFilters;

    public StudentSearchFilterViewModel(){
        mFilters = Filters.getDefault();
    }

    public Filters getFilters() {
        return mFilters;
    }

    public void setFilters(Filters mFilters) {
        this.mFilters = mFilters;
    }
}
