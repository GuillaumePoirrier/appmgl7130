package com.mgl7130.curve.pages.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.mgl7130.curve.R;

public class profile_student extends AppCompatActivity {

    private TextView age;
    private TextView name;
    private TextView surname;
    private TextView description;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        name = (TextView) findViewById(R.id.name_student);
        surname = (TextView) findViewById(R.id.surname_student);
        description = (TextView) findViewById(R.id.description_student);
        age = (TextView) findViewById(R.id.age_student);
        age.setText("19ans");



    }
}
