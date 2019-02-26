package com.mgl7130.curve.pages.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

import com.mgl7130.curve.R;

public class SignInActivity extends AppCompatActivity {

    RadioButton rememberMeRadioNutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
}
