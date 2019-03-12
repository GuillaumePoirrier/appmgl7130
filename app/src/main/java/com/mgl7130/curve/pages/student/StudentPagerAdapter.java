package com.mgl7130.curve.pages.student;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mgl7130.curve.pages.student.ui.profile.StudentProfileCreate;
import com.mgl7130.curve.pages.student.ui.classes.list.StudentClassRecyclerFragment;

public class StudentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    StudentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    //@TODO change fragments

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show StudentClassRecyclerFragment
                return StudentClassRecyclerFragment.newInstance();
            case 1: // Fragment # 0 - This will show TeacherStudentsRecyclerFragment
                return StudentClassRecyclerFragment.newInstance();
            case 2: // Fragment # 1 - This will show TeacherStudentsRecyclerFragment
                return StudentProfileCreate.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
