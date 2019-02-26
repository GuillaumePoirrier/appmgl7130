package com.example.mgr850.applimgr7130.pages.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import com.example.mgr850.applimgr7130.R;

public class SignInActivity extends AppCompatActivity {

    RadioButton rememberMeRadioNutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
}
