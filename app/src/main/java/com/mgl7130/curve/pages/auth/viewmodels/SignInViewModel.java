package com.mgl7130.curve.pages.auth.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.auth.ProfileChoiceActivity;
import com.mgl7130.curve.pages.auth.models.SignInData;


public class SignInViewModel extends BaseObservable {

    public static final String TAG = "SignInVM";

    private SignInData data = new SignInData();
    public MutableLiveData<Class<ProfileChoiceActivity>> startActivity = new MutableLiveData<>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public SignInViewModel(SharedPreferences preferences, Context context) {
        this.mSharedPreferences = preferences;
        this.mContext = context;
        this.data.rememberMe = preferences.getBoolean(mContext.getResources().getString(R.string.auth_preferences_remember_me), false);
    }

    @Bindable
    public String getEmail() {
        return data.email;
    }

    @Bindable
    public String getPassword() {
        return data.password;
    }

    @Bindable
    public Boolean getRememberMe() {
        return data.rememberMe;
    }

    public void setEmail(String value) {
        if(!data.email.equals(value)) {
            data.email = value;
        }
    }

    public void setPassword(String value) {
        if(!data.password.equals(value)) {
            data.password = value;
        }
    }

    public void setRememberMe(Boolean value) {
        if(data.rememberMe != value) {
            data.rememberMe = value;
            saveRememberMe();
        }
    }

    private void saveRememberMe() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(mContext.getResources().getString(R.string.auth_preferences_remember_me), data.rememberMe);
        editor.apply();
    }

    public void login(View view) {
        if (testFields(view)) signUserIn();
    }

    private boolean testFields(View view){
        if (TextUtils.isEmpty(data.email)) {
            Toast.makeText(view.getContext(), R.string.enter_email_address, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(data.password)) {
            Toast.makeText(view.getContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signUserIn() {
        mAuth.signInWithEmailAndPassword(data.email, data.password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity.setValue(ProfileChoiceActivity.class);
            }
        });
    }

}
