package com.mgl7130.curve.pages.auth.viewmodels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class SignInViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private Boolean rememberMe;

    public SignInViewModelFactory(Boolean rememberMe) {
        this.application = application;
        this.rememberMe = rememberMe;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignInViewModel(rememberMe);
    }
}
