package com.mgl7130.curve.pages.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mgl7130.curve.R;

import com.mgl7130.curve.pages.teacher.ui.profile_create.TeacherProfileCreate;


import butterknife.BindView;
import butterknife.ButterKnife;


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
        ButterKnife.bind(this);

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
//        return new TeacherStudentsRecyclerFragment().newInstance();
//        return new TeacherClassFormActivity().newInstance();
     //   return new TeacherClassRecyclerFragment().newInstance();
  //      return new TeacherClassFormFragment().newInstance();

    return new TeacherProfileCreate().newInstance();
    }

}
