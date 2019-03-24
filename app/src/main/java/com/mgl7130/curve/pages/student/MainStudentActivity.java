package com.mgl7130.curve.pages.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.auth.AuthentificationChoiceActivity;
import com.mgl7130.curve.pages.teacher.MainTeacherActivity;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set viewPager and adapter
        adapterViewPager = new StudentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_teacher_classes:
                Intent goToTeacher = new Intent(this, MainTeacherActivity.class);
                startActivity(goToTeacher);
                return true;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent goToHomePage= new Intent(this, AuthentificationChoiceActivity.class);
                startActivity(goToHomePage);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                case R.id.navigation_student_search:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_student_profile:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

}
