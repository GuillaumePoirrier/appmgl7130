package com.mgl7130.curve.pages.auth.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.student.MainStudentActivity;
import com.mgl7130.curve.pages.teacher.MainTeacherActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_choice);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageView_teacher)
    public void onStudentProfileClicked(View view) {
        Intent intent = new Intent(ProfileChoiceActivity.this, MainTeacherActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imageView_student)
    public void onTeacherProfileClicked(View view) {
        Intent intent = new Intent(ProfileChoiceActivity.this, MainStudentActivity.class);
        startActivity(intent);
    }

}
