package com.mgl7130.curve.pages.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mgl7130.curve.R;


public class MainTeacherActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_teacher_classes:
                    fragment = getClasses();
                    fm.beginTransaction()
                            .add(R.id.container, fragment)
                            .commit();
                    return true;
                case R.id.navigation_teacher_profile:
                    fragment = getStudents();
                    fm.beginTransaction()
                            .add(R.id.container, fragment)
                            .commit();
                    return true;
                case R.id.navigation_teacher_students:
                    fragment = getProfile();
                    fm.beginTransaction()
                            .add(R.id.container, fragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    public MainTeacherActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);

        //Insert Fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if(fragment == null){
            fragment = getProfile();
            fm.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected Fragment getStudents() {
        return new com.mgl7130.curve.pages.teacher.ui.teacher.TeacherStudentsRecyclerFragment().newInstance();
    }
    protected Fragment getProfile() {
        return new com.mgl7130.curve.pages.teacher.ui.teacher.TeacherProfileFragment().newInstance();
    }
    protected Fragment getClasses() {
        return new com.mgl7130.curve.pages.teacher.ui.teacher.TeacherClassesFragment().newInstance();
    }




}
