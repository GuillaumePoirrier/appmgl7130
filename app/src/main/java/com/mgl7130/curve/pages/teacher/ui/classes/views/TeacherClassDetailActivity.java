package com.mgl7130.curve.pages.teacher.ui.classes.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mgl7130.curve.R;

public class TeacherClassDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        String classID = getIntent().getExtras().getString(TeacherClassDetailFragment.KEY_CLASS_ID);
        Bundle args = new Bundle();
        args.putString(TeacherClassDetailFragment.KEY_CLASS_ID, classID);

        Fragment fragment = TeacherClassDetailFragment.newInstance();
        fragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_fragment, fragment);
        ft.commit();

    }

}
