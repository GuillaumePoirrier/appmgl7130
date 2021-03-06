package com.mgl7130.curve.pages.auth.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.ActivitySignInBinding;
import com.mgl7130.curve.pages.auth.viewmodels.SignInViewModel;
import com.mgl7130.curve.pages.auth.viewmodels.SignInViewModelFactory;

public class SignInActivity extends AppCompatActivity {

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Get shared preferences to get STAY_Logged or not
        mSharedPreferences = this.getSharedPreferences(getString(R.string.auth_preferences_file), Context.MODE_PRIVATE);
        Boolean rememberMe = mSharedPreferences.getBoolean(getString(R.string.auth_preferences_remember_me), false);

        SignInViewModel viewModel = ViewModelProviders.of(this, new SignInViewModelFactory(rememberMe)).get(SignInViewModel.class);
        ActivitySignInBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);

        viewModel.data.startActivity.observe(this, profileChoiceActivityClass -> {
            Intent intent = new Intent(mContext, profileChoiceActivityClass);
            startActivity(intent);
            finish();
        });

        viewModel.data.rememberMe.observe(this, value -> {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(mContext.getResources().getString(R.string.auth_preferences_remember_me), value);
            editor.apply();
        });
    }
}
