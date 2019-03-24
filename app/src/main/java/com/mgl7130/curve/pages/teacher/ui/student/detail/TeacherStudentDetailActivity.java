package com.mgl7130.curve.pages.teacher.ui.student.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.mgl7130.curve.R;

public class TeacherStudentDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_detail);

        String classID = getIntent().getExtras().getString(TeacherStudentDetailFragment.KEY_CLASS_ID);
        Bundle args = new Bundle();
        args.putString(TeacherStudentDetailFragment.KEY_CLASS_ID, classID);

        Fragment fragment = TeacherStudentDetailFragment.newInstance();
        fragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_fragment, fragment);
        ft.commit();

    }

}