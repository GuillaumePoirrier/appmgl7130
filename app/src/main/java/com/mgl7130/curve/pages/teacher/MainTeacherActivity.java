package com.mgl7130.curve.pages.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.teacher.profile_create.TeacherProfileCreate;
import com.mgl7130.curve.pages.teacher.ui.student_list.TeacherStudentsRecyclerFragment;


public class MainTeacherActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_teacher_classes:
                    return true;
                case R.id.navigation_teacher_profile:
                    return true;
                case R.id.navigation_teacher_students:
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
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected Fragment createFragment() {
        //return new com.mgl7130.curve.pages.teacher.ui.student_list.TeacherStudentsRecyclerFragment().newInstance();
        return new TeacherProfileCreate().newInstance();
    }

}
