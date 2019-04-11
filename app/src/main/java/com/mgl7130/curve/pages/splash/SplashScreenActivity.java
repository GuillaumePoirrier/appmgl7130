package com.mgl7130.curve.pages.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.auth.views.ProfileChoiceActivity;
import com.mgl7130.curve.pages.auth.views.AuthentificationChoiceActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private Intent activityIntent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        //Get shared preferences to save STAY_Logged or not
        final SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.auth_preferences_file), Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(getString(R.string.auth_preferences_remember_me), false)){
            mAuth.signOut();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        isUserLogedIn();
                        startActivity(activityIntent);
                    }
                },
                1000
        );
    }

    private void isUserLogedIn() {
        if (mAuth.getCurrentUser() != null){
            activityIntent = new Intent(this, ProfileChoiceActivity.class);
        } else {
            activityIntent = new Intent(this, AuthentificationChoiceActivity.class);
        }
    }

}
