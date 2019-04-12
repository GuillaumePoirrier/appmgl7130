package com.mgl7130.curve.pages.auth.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.auth.models.SignInData;
import com.mgl7130.curve.pages.auth.views.ProfileChoiceActivity;


public class SignInViewModel extends ViewModel {

    public static final String TAG = "SignInVM";

    public SignInData data = new SignInData();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SignInViewModel(Boolean rememberMe) {
        this.data.rememberMe.setValue(rememberMe);
        this.data.email.setValue("");
        this.data.password.setValue("");
    }

    public String getEmail() {
        return data.email.getValue();
    }

    public void setEmail(String value) {
        if (!data.email.getValue().equals(value)) {
            data.email.setValue(value);
        }
    }

    public String getPassword() {
        return data.password.getValue();
    }

    public void setPassword(String value) {
        if (!data.password.getValue().equals(value)) {
            data.password.setValue(value);
        }
    }

    public Boolean getRememberMe() {
        return data.rememberMe.getValue();
    }

    public void setRememberMe(Boolean value) {
        if (data.rememberMe.getValue() != value) {
            data.rememberMe.setValue(value);
        }
    }

    public void login(View view) {
        if (testFields(view)) signUserIn();
    }

    private boolean testFields(View view) {
        if (TextUtils.isEmpty(data.email.getValue())) {
            Toast.makeText(view.getContext(), R.string.enter_email_address, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(data.password.getValue())) {
            Toast.makeText(view.getContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signUserIn() {
        mAuth.signInWithEmailAndPassword(data.email.getValue(), data.password.getValue()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                data.startActivity.setValue(ProfileChoiceActivity.class);
            }
        });
    }

}
