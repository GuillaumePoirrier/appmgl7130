package com.mgl7130.curve.pages.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;

public class AuthentificationChoiceActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_choice);

        mAuth = FirebaseAuth.getInstance();

        final Intent signInActivityIntent = new Intent(this, SignInActivity.class);
        final Intent signUpActivityIntent = new Intent(this, SignUpActivity.class);

        Button signInButton = (Button) findViewById(R.id.button_sign_in);
        TextView signUpButton = (TextView) findViewById(R.id.textView_sign_up);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signInActivityIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signUpActivityIntent);
            }
        });

    }


    //    prevent user to go back to splash screen
    @Override
    public void onBackPressed() {
        return;
    }
}
