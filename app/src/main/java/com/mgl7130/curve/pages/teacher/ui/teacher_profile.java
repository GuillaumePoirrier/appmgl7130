package com.mgl7130.curve.pages.teacher.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.mgl7130.curve.R;
import com.wdullaer.materialdatetimepicker.time.CircleView;


public class teacher_profile extends AppCompatActivity {

    private CircleView avatar;
    private ListView listCourse;
    private TextView description;
    private TextView name;
    private TextView surname;
    private TextView age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile);

      /*  avatar = (CircleView) findViewById(R.id.avatar_circle);
        listCourse = (ListView) findViewById(R.id.list_course);
        description = (TextView) findViewById(R.id.description_teacher);
        name = (TextView) findViewById(R.id.name_teacher);
        surname = (TextView) findViewById(R.id.surname_teacher);
        age = (TextView) findViewById(R.id.age_teacher);*/


    }



}
