package com.mgl7130.curve.pages.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.mgl7130.curve.R;

public class choice_course extends AppCompatActivity {

    private EditText city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_choice_course);

        city = (EditText) findViewById(R.id.city_suggestion);

    }
}
