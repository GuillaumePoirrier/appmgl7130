package com.mgl7130.curve.pages.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.auth.AuthentificationChoiceActivity;

public class SplashScreenActivity extends AppCompatActivity {

    Intent authChoiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        authChoiceIntent = new Intent(this, AuthentificationChoiceActivity.class);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        startActivity(authChoiceIntent);
                    }
                },
                1000
        );
    }
}
