package com.mgl7130.curve.pages.student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mgl7130.curve.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

public class MainStudentActivity extends AppCompatActivity {

    @BindView(R.id.vpPagerStudent)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private FragmentPagerAdapter adapterViewPager;
    MenuItem prevMenuItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);
        ButterKnife.bind(this);

        //set viewPager and adapter
        adapterViewPager = new StudentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @OnPageChange(R.id.vpPagerStudent)
    public void onPageChanged(int position) {
        if (prevMenuItem != null)
            prevMenuItem.setChecked(false);
        else
            navigation.getMenu().getItem(0).setChecked(false);

        navigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = navigation.getMenu().getItem(position);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_student_classes:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_student_profile:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_student_search:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

}
