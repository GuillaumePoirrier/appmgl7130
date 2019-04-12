package com.mgl7130.curve.pages.auth.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthentificationChoiceActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Intent signInActivityIntent;
    Intent signUpActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_choice);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        signInActivityIntent = new Intent(this, SignInActivity.class);
        signUpActivityIntent = new Intent(this, SignUpActivity.class);

    }

    @OnClick(R.id.button_sign_in)
    public void onSignInClicked(View view) {
        startActivity(signInActivityIntent);
    }

    @OnClick(R.id.textView_sign_up)
    public void onSignUpClicked(View view) {
        startActivity(signUpActivityIntent);
    }

    //    prevent user to go back to splash screen
    @Override
    public void onBackPressed() {
        return;
    }
}
