package com.mgl7130.curve.pages.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.editText_email)
    EditText inputEmail;

    @BindView(R.id.editText_password)
    EditText inputPassword;

    @BindView(R.id.keep_me_logged)
    CheckBox rememberMe;


    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();


        //Get shared preferences to save STAY_Logged or not
        sharedPreferences = this.getSharedPreferences(getString(R.string.auth_preferences_file), Context.MODE_PRIVATE);
        rememberMe.setChecked(sharedPreferences.getBoolean(getString(R.string.auth_preferences_remember_me), false));

    }

    @OnClick(R.id.button_sign_in)
    public void onSignInClicked() {
        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if(rememberMe.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.auth_preferences_remember_me), rememberMe.isChecked());
            editor.apply();
        }

        if(testFields(email, password)){
            signUserIn(email, password);
        }

    }

    private boolean testFields(String email, String password){
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), R.string.enter_email_address, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signUserIn(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.password_too_short));
                            } else {
                                Toast.makeText(SignInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(SignInActivity.this, ProfileChoiceActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
