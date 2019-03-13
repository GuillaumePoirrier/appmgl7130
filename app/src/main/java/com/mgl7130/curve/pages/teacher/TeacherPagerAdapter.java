package com.mgl7130.curve.pages.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mgl7130.curve.pages.teacher.ui.classes.list.TeacherClassRecyclerFragment;
import com.mgl7130.curve.pages.teacher.ui.profile_create.TeacherProfileCreate;
import com.mgl7130.curve.pages.teacher.ui.student.list.TeacherStudentRecyclerFragment;

public class TeacherPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    TeacherPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show StudentSearchRecyclerFragment
                return TeacherStudentRecyclerFragment.newInstance();
            case 1: // Fragment # 0 - This will show TeacherStudentsRecyclerFragment
                return TeacherClassRecyclerFragment.newInstance();
            case 2: // Fragment # 1 - This will show TeacherStudentsRecyclerFragment
                return TeacherProfileCreate.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
