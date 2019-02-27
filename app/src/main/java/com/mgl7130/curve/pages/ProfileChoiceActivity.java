package com.mgl7130.curve.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.auth.SignInActivity;
import com.mgl7130.curve.pages.student.MainStudentActivity;
import com.mgl7130.curve.pages.teacher.MainTeacherActivity;

public class ProfileChoiceActivity extends AppCompatActivity {

    ImageView imageTeacher, imageStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_choice);

        imageStudent = (ImageView) findViewById(R.id.imageView_student);
        imageTeacher = (ImageView) findViewById(R.id.imageView_teacher);

        imageTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileChoiceActivity.this, MainTeacherActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileChoiceActivity.this, MainStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
