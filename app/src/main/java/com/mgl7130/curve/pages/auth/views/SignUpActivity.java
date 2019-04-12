package com.mgl7130.curve.pages.auth.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.ActivitySignUpBinding;
import com.mgl7130.curve.pages.auth.viewmodels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";

    private SignUpViewModel mViewModel;
    private ActivitySignUpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        if (savedInstanceState == null) mViewModel.init();
        mBinding.setViewmodel(mViewModel);
        setupButtonClicked();
    }

    private void setupButtonClicked() {
        mViewModel.getSignUpFields().observe(this, signUpFields -> {
            Snackbar.make(mBinding.getRoot(), R.string.creating_user, Snackbar.LENGTH_LONG).show();
            mViewModel.createUser(signUpFields);
        });
        setupUserCreated();
    }

    private void setupUserCreated() {
        mViewModel.userCreated.observe(this, e -> {
            if (e == null) {
                Snackbar.make(mBinding.getRoot(), R.string.user_created, Snackbar.LENGTH_LONG).show();
                startActivity(new Intent(SignUpActivity.this, ProfileChoiceActivity.class));
                finish();
            } else {
                Snackbar.make(mBinding.getRoot(), R.string.error_creating_user, Snackbar.LENGTH_LONG).show();
                Log.e(TAG, e.toString());
            }
        });
    }

}
