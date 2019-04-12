package com.mgl7130.curve.pages.teacher.ui.student.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mgl7130.curve.R;

public class TeacherStudentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        String classID = getIntent().getExtras().getString(TeacherStudentDetailFragment.KEY_STUDENT_ID);
        Bundle args = new Bundle();
        args.putString(TeacherStudentDetailFragment.KEY_STUDENT_ID, classID);

        Fragment fragment = TeacherStudentDetailFragment.newInstance();
        fragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_fragment, fragment);
        ft.commit();

    }

}