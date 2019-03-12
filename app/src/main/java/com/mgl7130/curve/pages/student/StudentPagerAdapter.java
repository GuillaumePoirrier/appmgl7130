package com.mgl7130.curve.pages.student;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//import com.mgl7130.curve.pages.student.list.StudentClassRecyclerFragment;
import com.mgl7130.curve.pages.student.profile.StudentProfile;
import com.mgl7130.curve.pages.teacher.ui.classes.list.TeacherClassRecyclerFragment;
import com.mgl7130.curve.pages.teacher.ui.student.student_list.TeacherStudentsRecyclerFragment;

public class StudentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    StudentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    //@TODO change fragments

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StudentProfile.newInstance();
            case 1:
                return StudentProfile.newInstance();
            case 2:
                return StudentProfile.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
