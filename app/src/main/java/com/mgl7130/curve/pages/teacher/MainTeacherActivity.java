package com.mgl7130.curve.pages.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mgl7130.curve.R;

import com.mgl7130.curve.pages.teacher.ui.profile_create.TeacherProfileCreate;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;


public class MainTeacherActivity extends AppCompatActivity {

    @BindView(R.id.vpPagerTeacher)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private FragmentPagerAdapter adapterViewPager;
    MenuItem prevMenuItem;

    public MainTeacherActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);
        ButterKnife.bind(this);

        //set viewPager and adapter
        adapterViewPager = new TeacherPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @OnPageChange(R.id.vpPagerTeacher)
    public void onPageChanged(int position) {
        if (prevMenuItem != null)
            prevMenuItem.setChecked(false);
        else
            navigation.getMenu().getItem(0).setChecked(false);

        navigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = navigation.getMenu().getItem(position);
   return new TeacherProfileCreate().newInstance();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_teacher_students:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_teacher_classes:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_teacher_profile:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };



}
